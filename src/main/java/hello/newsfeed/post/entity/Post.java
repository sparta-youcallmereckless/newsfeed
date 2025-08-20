package hello.newsfeed.post.entity;

import hello.newsfeed.post.dto.request.PostRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authorId;

    private String title;

    private String content;

    public Post(
            Long id, String authorId, String title, String content) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
    }

    public Post(PostRequest postRequest) {
        this.authorId = authorId;
        this.title = title;
        this.content = content;
    }

    public void update(PostRequest postRequest) {
        this.authorId = authorId;
        this.title = title;
        this.content = content;
    }

}
