package hello.newsfeed.post.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostRequest {
    @NotBlank(message = "내용을 입력해주시길 바랍니다.")
    private String title;
    private String content;

}