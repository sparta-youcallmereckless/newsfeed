package hello.newsfeed.profile.dto.request;

import lombok.Getter;

@Getter
public class ProfileCreateRequest {

    private String username;
    private String nickname;
    private String email;
    private String password;
}
