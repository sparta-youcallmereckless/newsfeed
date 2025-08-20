package hello.newsfeed.auth.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AuthResponse {
    private final Long id;

    public AuthResponse(Long id, String name, String email, LocalDateTime createdAt) {
        this.id = id;
    }
}
