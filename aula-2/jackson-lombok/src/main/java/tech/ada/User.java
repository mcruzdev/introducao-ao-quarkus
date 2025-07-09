package tech.ada;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    private final String id;
    private String name;
    private String password;
    @EqualsAndHashCode.Include
    private final String email;
    private String phone;
    private String role;
    private Instant createdAt;
    private Instant updatedAt;
    private boolean active;
    private String imageUrl;
}
