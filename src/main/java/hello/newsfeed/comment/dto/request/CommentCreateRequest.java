package hello.newsfeed.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentCreateRequest {
    @NotBlank(message = "내용을 입력해주시길 바랍니다.")
    private String content;
}
