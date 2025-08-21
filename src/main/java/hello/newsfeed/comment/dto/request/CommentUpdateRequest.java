package hello.newsfeed.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

// 댓글 수정용 RequestDto
// RequestDto에는 final 키워드, 생성자 넣지 않음
@Getter                                                 // getter 메서드를 자동으로 생성해주는 annotation
public class CommentUpdateRequest {
    // 해당 필드가 null, 빈 문자열, 공백이면 검증이 실패함
    // message 속성: 검증 실패 시 반환할 메시지 내용을 적음
    @NotBlank(message = "내용을 입력해주시길 바랍니다.")
    private String content;                             // 댓글 내용
}
