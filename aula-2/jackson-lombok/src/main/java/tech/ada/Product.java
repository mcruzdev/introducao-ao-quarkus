package tech.ada;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {

    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "UTC")
    @JsonAlias({"created_at", "createdAt"})
    private Instant createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "UTC")
    @JsonAlias({"updated_at", "updatedAt"})
    private Instant updatedAt;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> tags = new ArrayList<>();
    @JsonIgnore
    private final String sensible = UUID.randomUUID().toString();

    public Product(String name, String description, BigDecimal price, String category, String imageUrl,
                   Instant createdAt, Instant updatedAt) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public List<String> getTags() {
        return Collections.unmodifiableList(tags);
    }

    public void addTag(String tag) {
        if (tag != null && !tag.trim().isEmpty()) {
            this.tags.add(tag);
        }
    }
}
