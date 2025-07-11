package tech.ada;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;


@RegisterRestClient(baseUri = "http://localhost:9999") // base url do microservice notifications
@Path("/api/v1/notifications")
public interface NotificationService {

    @POST
    public void enviaNotificacao(CriaNotificaoRequest request);
}
