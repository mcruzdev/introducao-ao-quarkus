package tech.ada;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@NoArgsConstructor
public class Product {

    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonAlias({"created_at", "createdAt"})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "UTC")
    private Instant createdAt;
    @JsonAlias({"updated_at", "updatedAt"})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "UTC")
    private Instant updatedAt;

    public Product(String productName, String productDescription, BigDecimal bigDecimal, String electronics, String url, Instant parse, Instant parse1) {

    }
}