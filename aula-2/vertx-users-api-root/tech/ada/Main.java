package tech.ada;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

public class Main {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ApiVerticle());
    }


    static class ApiVerticle extends AbstractVerticle {
        @Override
        public void start() {

            Router router = Router.router(vertx);
            router.route(HttpMethod.PUT, "/api/users")
                    .handler(ctx -> {
                        ctx.request().body(bodyHandler -> {
                            String body = bodyHandler.result().toString();
                            System.out.println("Request received: " + body);
                            ctx.response().setStatusCode(204).send();
                        });
                    });

            router.route(HttpMethod.GET, "/ping")
                    .handler(ctx -> ctx.response().send("pong"));

            vertx.createHttpServer()
                    .requestHandler(router)
                    .listen(8888)
                    .onSuccess(server -> {
                        System.out.println("HTTP server started on port " + server.actualPort());
                    });
        }
    }
}
