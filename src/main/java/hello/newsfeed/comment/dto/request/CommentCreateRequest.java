package hello.newsfeed.comment.dto.request;

import hello.newsfeed.comment.entity.Comment;
import hello.newsfeed.post.entity.Post;
import hello.newsfeed.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

// 댓글 생성용 RequestDto
// RequestDto에는 final 키워드, 생성자 넣지 않음
@Getter                                                 // getter 메서드를 자동으로 생성해주는 annotation
public class CommentCreateRequest {
    // 해당 필드가 null, 빈 문자열, 공백이면 검증이 실패함
    // message 속성: 검증 실패 시 반환할 메시지 내용을 적음
    @NotBlank(message = "내용을 입력해주시길 바랍니다.")
    private String content;                             // 댓글 내용

    // DTO를 엔티티로 변환하는 메서드
    // Builder 패턴을 사용하여 Comment 객체 생성
    // 사용하기 위해서 Comment 클래스의 생성자에 @Builder가 적용되어 있어야 함
    public Comment toEntity(Post post, User user) {
        return Comment.builder()                        // builder 패턴으로 Comment 객체 생성 시작
                .post(post)                             // 댓글 작성할 게시물 설정
                .user(user)                             // 댓글 작성자 설정
                .content(this.content)                  // CommentCreateRequest의 content 값으로 설정
                .build();                               // Comment 객체 생성 및 반환
    }
}
