package hello.newsfeed.comment.dto.request;

import hello.newsfeed.comment.entity.Comment;
import hello.newsfeed.post.entity.Post;
import hello.newsfeed.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentCreateRequest {
    @NotBlank(message = "내용을 입력해주시길 바랍니다.")
    private String content;

    // DTO를 엔티티로 변환함 (Builder 패턴 사용)
    // 사용하기 위해서 Comment 엔티티의 생성자에 @Builder를 사용해야 함
    public Comment toEntity(Post post, User user) {
        return Comment.builder()
                .post(post)
                .user(user)
                .content(this.content)
                .build();
    }
}
