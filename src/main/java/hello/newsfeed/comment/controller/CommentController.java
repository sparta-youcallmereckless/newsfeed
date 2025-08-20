package hello.newsfeed.comment.controller;

import hello.newsfeed.comment.dto.request.CommentCreateRequest;
import hello.newsfeed.comment.dto.response.CommentCreateResponse;
import hello.newsfeed.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 생성 기능
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentCreateResponse> createComment(
            @SessionAttribute(name = "LOGIN_USER") Long userId,
            @PathVariable Long postId,
            @RequestBody CommentCreateRequest commentRequest
    ) {
        return ResponseEntity.ok(commentService.createComment(userId, postId, commentRequest));
    }
}
