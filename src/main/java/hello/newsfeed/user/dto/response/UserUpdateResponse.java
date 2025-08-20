package hello.newsfeed.user.dto.response;

import lombok.Getter;

@Getter
public class UserUpdateResponse {

    private final String email;

    public UserUpdateResponse(String email) {
        this.email = email;
    }
}