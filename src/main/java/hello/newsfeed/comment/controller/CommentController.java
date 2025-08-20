package hello.newsfeed.comment.controller;

import hello.newsfeed.comment.dto.request.CommentCreateRequest;
import hello.newsfeed.comment.dto.response.CommentCreateResponse;
import hello.newsfeed.comment.dto.response.CommentReadAllResponse;
import hello.newsfeed.comment.dto.response.CommentReadSingleResponse;
import hello.newsfeed.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 댓글 전체 조회 기능
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentReadAllResponse>> getAllComments(
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok(commentService.getAllComments(postId));
    }

    // 댓글 단건 조회 기능
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentReadSingleResponse> getComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        return ResponseEntity.ok(commentService.getComment(postId, commentId));
    }
}
