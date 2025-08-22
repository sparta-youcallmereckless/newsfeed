package hello.newsfeed.user.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponse {

    // response는 한 번 정해지면 바꿀 일이 없어서 final 붙여줌
    private final Long id;
    private final String username;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public UserResponse(Long id, String username, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
