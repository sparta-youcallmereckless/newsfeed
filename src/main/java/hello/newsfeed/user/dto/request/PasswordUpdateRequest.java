package hello.newsfeed.user.dto.request;

import lombok.Getter;

@Getter
public class PasswordUpdateRequest {

    private String currentPassword;
    private String newPassword;
}
