package hello.newsfeed.user.dto.request;

import lombok.Getter;

@Getter
public class UserUpdateRequest {

    private String username;
    private String email;
    private String password;
}


