package hello.newsfeed.comment.dto.response;

import hello.newsfeed.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentReadSingleResponse {
    private final Long postId;                  // 게시글 ID
    private final Long userId;                  // 사용자 ID
    private final Long id;                      // 댓글 ID
    private final String content;               // 댓글 내용
    private final LocalDateTime createdAt;      // 작성일
    private final LocalDateTime modifiedAt;     // 수정일

    // 생성자
    @Builder
    public CommentReadSingleResponse(Long postId, Long userId, Long id, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.postId = postId;
        this.userId = userId;
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    // 엔티티로부터 DTO를 생성함
    public static CommentReadSingleResponse from(Comment comment) {
        return CommentReadSingleResponse.builder()
                .postId(comment.getPost().getId())
                .userId(comment.getUser().getId())
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}
