package hello.newsfeed.post.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {
    private  Long id;
    private  String authorId;
    private final String title;
    private final String content;
    private  LocalDateTime createdAt;
    private  LocalDateTime modifiedAt;

    public PostResponse
            (Long id, String authorId, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public PostResponse(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

//파이널 내가 임의로 지움 (당장 오류해결)