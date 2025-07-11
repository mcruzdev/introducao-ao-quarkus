package dev.matheuscruz;

import io.quarkus.logging.Log;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/notifications")
public class NotificationResource {

    @POST
    public Response post(CreateNotificationRequest req) {
        Log.info("Creating notification: " + req);
        return Response.status(Response.Status.CREATED).entity(req).build();
    }

    public record CreateNotificationRequest(String event, String eventName) {
    }
}
