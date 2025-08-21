package hello.newsfeed.comment.entity;

import hello.newsfeed.common.entity.BaseEntity;
import hello.newsfeed.post.entity.Post;
import hello.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                    // 댓글 ID

    @Column(nullable = false)                           // NOT NULL 제약조건: 반드시 값이 존재해야 함
    private String content;                             // 댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)                  // N:1 관계
    @JoinColumn(name = "post_id", nullable = false)     // 외래키 컬럼명을 post_id로 하고, null 값을 허용하지 않음
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)                  // N:1 관계
    @JoinColumn(name = "user_id", nullable = false)     // 외래키 컬럼명을 user_id로 하고, null 값을 허용하지 않음
    private User user;

    // 생성자
    // id는 자동 생성되므로 생성자에서 받지 않음
    @Builder
    public Comment(String content, Post post, User user) {
        this.content = content;
        this.post = post;
        this.user = user;
    }

    // 수정 메서드
    public void update(String content) {
        this.content = content;
    }
}