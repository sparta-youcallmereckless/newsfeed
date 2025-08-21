package hello.newsfeed.auth.dto.request;

import lombok.Getter;

@Getter
public class AuthLoginResponse {
    private final Long id;      // 유저 ID

    // 생성자
    public AuthLoginResponse(Long id) {
        this.id = id;
    }
}
