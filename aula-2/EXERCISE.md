# Exercício Jackson + Lombok

Adicionar as dependências do Jackson e do Lombok no seu projeto.

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.15.2</version>
</dependency>

<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.28</version>
    <scope>provided</scope>
</dependency>
```

## 1. Criar uma classe `Product` no pacote `tech.ada`.

```java
package tech.ada;

public class Product {
}
```

## 2. Criar uma classe `Address` no pacote `tech.ada`.

```java
package tech.ada;

public class Address {
}
```

## 3. Criar a classe `JSON` no pacote `tech.ada` com o seguinte conteúdo:

```java
package tech.ada;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public interface JSON {
    
    static Product productFromJSON(String json) {
        // TODO: falar sobre o ObjectMapper e o JavaTimeModule
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            return objectMapper.readValue(json, Product.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    static String toJSON(Product product) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    static Address addressFromJSON(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.readValue(json, Address.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
```

## 3. Você deve criar na parte de `test` as seguintes classes:

```java
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
```

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.ada.Address;
import tech.ada.JSON;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ViaCEPTest {

    @Test
    void testViaCEP() {

        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://viacep.com.br/ws/01001000/json/"))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            Address address = JSON.addressFromJSON(response.body());
            Assertions.assertEquals("01001-000", address.getCep());
            Assertions.assertEquals("Praça da Sé", address.getLogradouro());
            Assertions.assertEquals("lado ímpar", address.getComplemento());
            Assertions.assertEquals("Sé", address.getBairro());
            Assertions.assertEquals("São Paulo", address.getLocalidade());
            Assertions.assertEquals("SP", address.getUf());
            Assertions.assertEquals("São Paulo", address.getEstado());
            Assertions.assertEquals("Sudeste", address.getRegiao());
            Assertions.assertEquals("3550308", address.getIbge());
            Assertions.assertEquals("1004", address.getGia());
            Assertions.assertEquals("11", address.getDdd());
            Assertions.assertEquals("7107", address.getSiafi());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
```

## 4. Requisitos

1. Todos os testes devem passar.
2. Você vai precisar implementar a classe `JSON` com os métodos `toJSON` e `productFromJSON`, além de usar o Lombok para gerar os getters, setters e construtores necessários nas classes `Product` e `Address`.
3. Você não deve mexer no código dos testes, apenas implementar as classes necessárias para que os testes passem.
