package dev.matheuscruz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Path("/api/v1/products")
public class ProductResource {

    private static final Map<String, Product> PRODUCTS = new ConcurrentHashMap<>();
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    public ProductResource(
            @RestClient NotificationService notificationService,
            ObjectMapper objectMapper
    ) {
        this.notificationService = notificationService;
        this.objectMapper = objectMapper;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProduct(CreateProductRequest req) {
        Product product = new Product(req.name(), req.price());
        PRODUCTS.put(req.name(), product);

        try {
            String event = this.objectMapper.writeValueAsString(product);
            this.notificationService.sendNotification(
                    new NotificationRequest(
                            event, "PRODUCT_CREATED"
                    )
            );
        } catch (JsonProcessingException e) {
            return Response.serverError().build();
        }

        return Response.ok(product).build();
    }

    public record CreateProductRequest(String name, BigDecimal price) {}

    static class Product {
        private String id;
        private String name;
        private BigDecimal price;

        public Product(String name, BigDecimal price) {
            this.id = UUID.randomUUID().toString();
            this.name = name;
            this.price = price;
        }

        public String getId() {
            return id;
        }
    }
}
