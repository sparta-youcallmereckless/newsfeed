package hello.newsfeed.auth.dto.request;

import lombok.Getter;

@Getter
public class AuthRequest {
    private String email;
    private String password;
    private String name;
}
