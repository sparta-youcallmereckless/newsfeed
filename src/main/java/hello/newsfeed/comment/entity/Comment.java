package hello.newsfeed.comment.entity;

import hello.newsfeed.common.entity.BaseEntity;
import hello.newsfeed.post.entity.Post;
import hello.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter                                                 // getter 메서드를 자동으로 생성해주는 annotation
@Entity                                                 // Bean으로 등록할 때 쓰는 annotation, 데이터베이스 테이블과 매핑하는데 사용함
@NoArgsConstructor(access = AccessLevel.PROTECTED)      // 기본 생성자를 자동으로 생성해주는 annotation
public class Comment extends BaseEntity {
    @Id                                                 // 기본키로 지정함
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 값을 증가시킴
    private Long id;                                    // 댓글 ID

    @Column(nullable = false)                           // NOT NULL 제약조건: 반드시 값이 존재해야 함
    private String content;                             // 댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)                  // N:1 관계
    @JoinColumn(name = "post_id", nullable = false)     // 외래키 컬럼명을 post_id로 하고, null 값을 허용하지 않음
    private Post post;                                  // 댓글이 속한 게시물

    @ManyToOne(fetch = FetchType.LAZY)                  // N:1 관계
    @JoinColumn(name = "user_id", nullable = false)     // 외래키 컬럼명을 user_id로 하고, null 값을 허용하지 않음
    private User user;                                  // 댓글 작성자

    // 생성자
    // id는 자동 생성되므로 생성자에서 받지 않음
    @Builder                                                    // Builder 패턴 적용
    public Comment(String content, Post post, User user) {
        this.content = content;                                 // 필드 content 초기화
        this.post = post;                                       // 필드 post 연관관계 설정
        this.user = user;                                       // 필드 user 연관관계 설정
    }

    // 수정 메서드
    // 댓글 내용만 수정 가능함
    public void update(String content) {
        this.content = content;                                 // 매개변수 content 값으로 댓글 내용 필드를 수정함
    }
}