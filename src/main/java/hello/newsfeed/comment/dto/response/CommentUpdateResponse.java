package hello.newsfeed.comment.dto.response;

import hello.newsfeed.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// 댓글 수정용 ResponseDto
// ResponseDto에는 final 키워드, 생성자 넣어야함
@Getter
public class CommentUpdateResponse {
    private final Long postId;                  // 게시글 ID
    private final Long userId;                  // 사용자 ID
    private final Long id;                      // 댓글 ID
    private final String content;               // 댓글 내용
    private final LocalDateTime createdAt;      // 작성일
    private final LocalDateTime modifiedAt;     // 수정일

    // 생성자
    @Builder                                    // Builder 패턴을 적용하여 객체 생성 가능
    public CommentUpdateResponse(Long postId, Long userId, Long id, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.postId = postId;                   // postId 필드 초기화
        this.userId = userId;                   // userId 필드 초기화
        this.id = id;                           // id 필드 초기화
        this.content = content;                 // content 필드 초기화
        this.createdAt = createdAt;             // createdAt 필드 초기화
        this.modifiedAt = modifiedAt;           // modifiedAt 필드 초기화
    }

    // 엔티티로부터 DTO를 생성하는 메서드
    public static CommentUpdateResponse from(Comment comment) {
        return CommentUpdateResponse.builder()          // builder 패턴으로 CommentUpdateResponse 객체 생성 시작
                .postId(comment.getPost().getId())      // 엔티티의 게시물 ID를 DTO의 postId 필드에 설정
                .userId(comment.getUser().getId())      // 엔티티의 사용자 ID를 DTO의 userId 필드에 설정
                .id(comment.getId())                    // 엔티티의 댓글 ID를 DTO의 id 필드에 설정
                .content(comment.getContent())          // 엔티티의 댓글 내용을 DTO의 content 필드에 설정
                .createdAt(comment.getCreatedAt())      // 엔티티의 생성일을 DTO의 createdAt 필드에 설정
                .modifiedAt(comment.getModifiedAt())    // 엔티티의 수정일을 DTO의 modifiedAt 필드에 설정
                .build();                               // CommentUpdateResponse 객체 생성 및 반환
    }
}
