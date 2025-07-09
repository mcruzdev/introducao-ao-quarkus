package tech.ada;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
