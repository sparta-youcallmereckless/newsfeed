package hello.newsfeed.user.dto.request;

import lombok.Getter;

@Getter
public class UserCreateRequest {

    private String username;
    private String email;
    private String password;

}

