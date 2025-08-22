package hello.newsfeed.profile.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ProfileUpdateRequest {

    @NotBlank(message = "닉네임을 입력해 주세요.")
    private String nickname;
}

