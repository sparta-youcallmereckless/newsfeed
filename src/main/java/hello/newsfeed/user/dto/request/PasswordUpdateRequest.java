package hello.newsfeed.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PasswordUpdateRequest {

    // 현재 비밀번호 (본인 확인용)
    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    private String currentPassword;

    // 새 비밀번호 (형식 유효성 검사 포함)
    @NotBlank(message = "새 비밀번호를 입력해주세요.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = "비밀번호는 8~20자이며, 영문/숫자/특수문자를 모두 포함해야 합니다."
    )
    private String newPassword;


    /*
     * 예외 처리 기준
     * - 현재 비밀번호가 일치하지 않으면 → 400 Bad Request
     * - 새 비밀번호 형식이 올바르지 않으면 → 422 Unprocessable Entity
     * - 새 비밀번호가 현재 비밀번호와 동일하면 → 409 Conflict
     */
}
