package tech.ada;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String json = """
                {"name":"Jhon","userId":"1","createdAt":"09/07/2025 00:13:51","_version":"v1"}
               
                """;

        try {
            Usuario usuario = objectMapper.readValue(json, Usuario.class);


            System.out.println(usuario);


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Usuario usuario = new Usuario("Jhon", "v1", "1", "123456");

        try {
            String jsonQueVouEnviarParaOFrontend = objectMapper.writeValueAsString(usuario);

            System.out.println(jsonQueVouEnviarParaOFrontend);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

//    static class ApiVerticle extends AbstractVerticle {
//        @Override
//        public void start() {
//
//            Router router = Router.router(vertx);
//            router.route(HttpMethod.PUT, "/api/users")
//                    .handler(ctx -> {
//                        ctx.request().body(bodyHandler -> {
//                            String body = bodyHandler.result().toString();
//                            System.out.println("Request received: " + body);
//                            ctx.response().setStatusCode(204).send();
//                        });
//                    });
//
//            router.route(HttpMethod.GET, "/ping")
//                    .handler(ctx -> ctx.response().send("pong"));
//
//            vertx.createHttpServer()
//                    .requestHandler(router)
//                    .listen(8888)
//                    .onSuccess(server -> {
//                        System.out.println("HTTP server started on port " + server.actualPort());
//                    });
//        }
//    }
}
