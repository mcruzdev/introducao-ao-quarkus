package tech.ada;

import com.fasterxml.jackson.annotation.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

// Plain Old Java Object = POJO
@Getter
@ToString
@Builder
public class Usuario {

    @Getter
    private String name;

    @Getter
    @JsonProperty("_version")
    private String version;

    @Getter
    @JsonAlias({
            "userId", "user_id"
    })
    private String userId;

    @JsonIgnore
    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> roles;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "UTC")
    private Instant createdAt;

    public Usuario() {
    }

    public Usuario(String name, String version, String userId, String password) {
        this.name = name;
        this.version = version;
        this.userId = userId;
        this.password = password;
        this.createdAt = Instant.now();
    }

    public record UsuarioDTO(String name) {}

//    @JsonCreator
//    public Usuario(@JsonProperty("name") final String name) {
//        this.name = Objects.requireNonNull(name);
//    }

//    @JsonCreator
//    public static Usuario create(@JsonProperty("name") final String name) {
//        return new Usuario(name);
//    }
}
