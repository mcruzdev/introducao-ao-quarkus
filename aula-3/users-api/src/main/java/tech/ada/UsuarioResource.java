package tech.ada;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * A cada operação que eu fizer com o meu usuário
 * eu preciso notificar o notifications service.
 */

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    private static final List<Usuario> USUARIOS = new ArrayList<>();
    private NotificationService notificationService;
    private ObjectMapper objectMapper;

    public UsuarioResource(
            @RestClient NotificationService notificationService,
            ObjectMapper objectMapper) { // injeção de dependência via construtor :)
        this.notificationService = notificationService;
        this.objectMapper = objectMapper;
    }

    static {
        USUARIOS.add(new Usuario("Maria", 20));
    }

    @POST
    public Usuario criarUmUsuario(Usuario usuario) {
        USUARIOS.add(usuario);

        try {
            String eventoComoJson = this.objectMapper.writeValueAsString(usuario); // convertendo usuario em String em formato JSON

            this.notificationService.enviaNotificacao(
                    new CriaNotificaoRequest(
                            eventoComoJson, "PRODUTO_CRIADO")); // usando o meu cliente HTTP
                            
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    @GET
    public List<Usuario> buscaUsuarios(@QueryParam("apenasMaioresDeIdade") boolean apenasMaioresDeIdade) {
        if (apenasMaioresDeIdade) {
            return USUARIOS.stream().filter(
                    usuario -> usuario.getIdade() >= 18).toList();
        } else {
            return USUARIOS;
        }
    }

    @GET
    @Path("/{id}")
    public Response buscaUsuarioPorId(@PathParam("id") String id) {

        Optional<Usuario> possivelUsuario = USUARIOS.stream()
                .filter(usuario -> usuario.getId().equals(id))
                .findFirst();

        if (possivelUsuario.isPresent()) {
            Usuario usuario = possivelUsuario.get();
            return Response.ok(usuario).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PATCH
    @Path("/{id}")
    public Response atualizaParcialmente(@PathParam("id") String id, AtualizaIdadeRequest requisicao) {

        Optional<Usuario> possivelUsuario = USUARIOS.stream()
                .filter(usuario -> usuario.getId().equals(id))
                .findFirst();

        if (possivelUsuario.isPresent()) {
            Usuario usuario = possivelUsuario.get();
            usuario.atualizaIdade(requisicao.novaIdade());
            USUARIOS.removeIf(user -> user.getId().equals(id));
            USUARIOS.add(usuario);
            return Response.noContent().build();
        } else {
            return Response.status(404).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleta(@PathParam("id") String id) {
        USUARIOS.removeIf(usuario -> usuario.getId().equals(id));
        return Response.noContent().build();
    }

    public record AtualizaIdadeRequest(Integer novaIdade) {
    }

    @PUT
    public void atualiza(Usuario usuario) {
    }
}
