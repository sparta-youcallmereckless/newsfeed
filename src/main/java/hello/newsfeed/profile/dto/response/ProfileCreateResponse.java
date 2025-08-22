package hello.newsfeed.profile.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProfileCreateResponse {

    // response는 한 번 정해지면 바꿀 일이 없어서 final 붙여줌
    private final Long id;
    private final String username;
    private final String nickname;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public ProfileCreateResponse(Long id, String username, String nickname, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
