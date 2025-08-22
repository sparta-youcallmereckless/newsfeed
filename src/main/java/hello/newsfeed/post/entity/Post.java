package hello.newsfeed.post.entity;

import hello.newsfeed.common.entity.BaseEntity;
import hello.newsfeed.post.dto.request.PostRequest;
import hello.newsfeed.post.dto.response.PostResponse;
import hello.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    //프라이빗 스트링 이미지 컬럼 추가

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    //    public Post(
//            Long id, String title, String content) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//    }
//    public Post(PostRequest postRequest) {
//        this.title = title;
//        this.content = content;
//    }
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
//    public PostResponse toResponse() {
//        return new PostResponse(
//                this.title,
//                this.content
//        );
//    }
}