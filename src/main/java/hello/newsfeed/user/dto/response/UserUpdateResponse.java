package hello.newsfeed.user.dto.response;

import lombok.Getter;

@Getter
public class UserUpdateResponse {

    //
    private final String username;
    private final String email;
    private final String password;

    public UserUpdateResponse(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}