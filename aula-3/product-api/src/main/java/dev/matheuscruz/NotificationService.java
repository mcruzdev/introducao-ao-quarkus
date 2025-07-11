package dev.matheuscruz;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api/v1/notifications")
@RegisterRestClient(configKey = "notifications")
public interface NotificationService {

    @POST
    void sendNotification(NotificationRequest notification);
}
