import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.ada.JSON;
import tech.ada.Product;

import java.math.BigDecimal;
import java.time.Instant;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

public class JSONTest {

    @Test
    void productFromJSON_shouldConvertWithSnakeCase() {

        Product product = new Product("Product Name", "Product Description", new BigDecimal("19.99"), "Electronics",
                "https://picsum.photos/200/300", Instant.parse("2023-10-01T12:00:00Z"),
                Instant.parse("2023-10-01T12:00:00Z"));

        String jsonString = JSON.toJSON(product);

        assertThatJson(jsonString).inPath("tags").isAbsent();
    }

    @Test
    void toJSON_shouldConvertWithSnakeCase() {

        Product product = JSON.productFromJSON("""
                {
                    "id": "123",
                    "name": "Product Name",
                    "description": "Product Description",
                    "price": 19.99,
                    "category": "Electronics",
                    "image_url": "https://picsum.photos/200/300",
                    "created_at":     "01/10/2023 12:00:00",
                    "updated_at":     "01/10/2023 12:00:00"
                }
                """);

        Assertions.assertEquals("123", product.getId());
        Assertions.assertEquals("Product Name", product.getName());
        Assertions.assertEquals("Product Description", product.getDescription());
        Assertions.assertEquals("Electronics", product.getCategory());
        Assertions.assertEquals("https://picsum.photos/200/300", product.getImageUrl());
        Assertions.assertEquals("2023-10-01T12:00:00Z", product.getCreatedAt().toString());
        Assertions.assertEquals("2023-10-01T12:00:00Z", product.getUpdatedAt().toString());
        Assertions.assertEquals("19.99", product.getPrice().toString());
    }

    @Test
    void toJSON_shouldConvertWithCamelCase() {

        Product product = JSON.productFromJSON("""
                {
                    "id": "123",
                    "name": "Product Name",
                    "description": "Product Description",
                    "price": 19.99,
                    "category": "Electronics",
                    "image_url": "https://picsum.photos/200/300",
                    "createdAt":     "01/10/2023 12:00:00",
                    "updatedAt":     "01/10/2023 12:00:00"
                }
                """);

        Assertions.assertEquals("123", product.getId());
        Assertions.assertEquals("Product Name", product.getName());
        Assertions.assertEquals("Product Description", product.getDescription());
        Assertions.assertEquals("Electronics", product.getCategory());
        Assertions.assertEquals("https://picsum.photos/200/300", product.getImageUrl());
        Assertions.assertEquals("2023-10-01T12:00:00Z", product.getCreatedAt().toString());
        Assertions.assertEquals("2023-10-01T12:00:00Z", product.getUpdatedAt().toString());
        Assertions.assertEquals("19.99", product.getPrice().toString());
    }

    @Test
    void toJSON_shouldNotIncludeNullFields() {
        Product product = new Product();
        String jsonString = JSON.toJSON(product);
        assertThatJson(jsonString).inPath("name").isAbsent();
        assertThatJson(jsonString).inPath("description").isAbsent();
    }

    @Test
    void productFromJSON_shouldHandleMissingOptionalFields() {
        Product product = JSON.productFromJSON("""
                {
                    "name": "Product Name",
                    "description": "Some description"
                }
                """);

        Assertions.assertEquals("Product Name", product.getName());
        Assertions.assertNull(product.getPrice());
    }

    @Test
    void productFromJSON_and_toJSON_shouldBeReversible() {
        Product original = new Product("Reversível", "Teste", new BigDecimal("9.99"), "Books",
                "https://picsum.photos/200/300", Instant.now(), Instant.now());

        String jsonString = JSON.toJSON(original);
        Product result = JSON.productFromJSON(jsonString);

        Assertions.assertEquals(original.getName(), result.getName());
        Assertions.assertEquals(original.getDescription(), result.getDescription());
        Assertions.assertEquals(original.getPrice(), result.getPrice());
    }

    @Test
    void productFromJSON_shouldThrowExceptionWhenInvalid() {
        String invalidJson = """
                {
                    "name": "Broken JSON",
                    "price": "not-a-number"
                }
                """;

        Assertions.assertThrows(RuntimeException.class, () -> JSON.productFromJSON(invalidJson));
    }

    @Test
    void product_shouldGenerateIdAutomatically() {
        Product product = new Product("Auto ID", "Generated", new BigDecimal("1.00"), "Misc", "url", Instant.now(),
                Instant.now());

        Assertions.assertNotNull(product.getId());
    }

    @Test
    void productFromJSON_shouldAcceptCamelOrSnakeCaseDates() {
        Product camelCase = JSON.productFromJSON("""
                {
                    "createdAt": "01/11/2025 10:00:00"
                }
                """);

        Product snakeCase = JSON.productFromJSON("""
                {
                    "created_at": "01/11/2025 10:00:00"
                }
                """);

        Assertions.assertEquals(camelCase.getCreatedAt(), snakeCase.getCreatedAt());
    }

    @Test
    void toJSON_shouldUseJsonPropertyForImageUrl() {
        Product product = new Product("Camera", "Digital Camera", new BigDecimal("299.99"), "Photography",
                "https://picsum.photos/id/237/200/300", Instant.parse("2023-10-01T12:00:00Z"),
                Instant.parse("2023-10-01T12:00:00Z"));

        String jsonString = JSON.toJSON(product);

        assertThatJson(jsonString).inPath("image_url").isEqualTo("https://picsum.photos/id/237/200/300");
    }

    @Test
    void toJSON_shouldSerializeDateWithCustomFormat() {
        Product product = new Product("Notebook", "Ultrabook", new BigDecimal("3999.99"), "Computers",
                "https://picsum.photos/seed/picsum/200/300", Instant.parse("2023-12-25T15:30:45Z"),
                Instant.parse("2023-12-25T15:30:45Z"));

        String jsonString = JSON.toJSON(product);

        assertThatJson(jsonString).inPath("createdAt").isEqualTo("25/12/2023 15:30:45");
    }
}
