package hello.newsfeed.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class AuthRequest {
    @NotBlank(message = "이메일은 필수입니다.")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
