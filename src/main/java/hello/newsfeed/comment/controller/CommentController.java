package hello.newsfeed.comment.controller;

import hello.newsfeed.comment.dto.request.CommentCreateRequest;
import hello.newsfeed.comment.dto.request.CommentUpdateRequest;
import hello.newsfeed.comment.dto.response.CommentCreateResponse;
import hello.newsfeed.comment.dto.response.CommentReadAllResponse;
import hello.newsfeed.comment.dto.response.CommentReadSingleResponse;
import hello.newsfeed.comment.dto.response.CommentUpdateResponse;
import hello.newsfeed.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController                 // Bean으로 등록할 때 사용하는 annotation, @RequestBody + @Controller (Restful API를 작성하기 위해서 사용함)
@RequiredArgsConstructor           // 필수(final) 필드만을 매개변수로 하는 생성자를 자동으로 생성함
public class CommentController {
    // 스프링 컨테이너로부터 의존성을 주입받는 싱글톤 CommentService 객체 (나 이 객체 사용할래! 스프링 컨테이너: 알겠어 써!)
    // 싱글톤: 어떤 클래스를 하나의 객체로 만드는 것
    private final CommentService commentService;

    /*
    기능: 댓글 생성 (Create Comment)
    HTTP Method: POST
    URL: /posts/{postId}/comments (http://localhost:8080/posts/{postId}/comments)
    인증/인가 : 로그인 필요 (세션 기반)
    Request:
     @SessionAttribute("LOGIN_USER") Long userId: 로그인한 사용자 ID
     @PathVariable Long postId: 댓글을 작성할 게시글 ID
     @Valid @RequestBody CommentCreateRequest commentCreateRequest: 요청 Body에 담긴 댓글 내용
    Response: 성공 시 생성된 댓글 정보를 JSON 형태로 반환함
    */
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentCreateResponse> createComment(
            @SessionAttribute(name = "LOGIN_USER") Long userId,             // 로그인 한 사용자 ID
            @PathVariable Long postId,                                      // @PathVariable: URL로 전달된 값을 파라미터로 받아오는 역할을 함 (필수로 받아와야 하는 값)
            @Valid @RequestBody CommentCreateRequest commentCreateRequest   // @Valid 적용, HTTP 요청 Body에 담긴 JSON 데이터를 CommentCreateRequest 객체로 변환함
    ) {
        // 새로운 댓글을 생성하고, 생성된 댓글 정보를 HTTP 200 OK와 함께 반환함
        return ResponseEntity.ok(commentService.createComment(userId, postId, commentCreateRequest));
    }

    /*
    기능: 댓글 전체 조회 (Read All Comments)
    HTTP Method: GET
    URL: /posts/{postId}/comments (http://localhost:8080/posts/{postId}/comments)
    인증/인가 : X
    Request:
     @PathVariable Long postId: 댓글을 조회할 게시글 ID
    Response: 성공 시 해당 게시글에 작성된 모든 댓글 정보를 JSON 리스트 형태로 반환함
    */
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentReadAllResponse>> getAllComments(
            @PathVariable Long postId                                       // @PathVariable: URL로 전달된 값을 파라미터로 받아오는 역할을 함 (필수로 받아와야 하는 값)
    ) {
        // 해당 게시글에 작성된 모든 댓글 정보를 가져와 HTTP 200 OK와 함께 반환함
        return ResponseEntity.ok(commentService.getAllComments(postId));
    }

    /*
    기능: 댓글 단건 조회 (Read Single Comment)
    HTTP Method: GET
    URL: /posts/{postId}/comments/{commentId} (http://localhost:8080/posts/{postId}/comments/{commentId})
    인증/인가 : X
    Request:
     @PathVariable Long postId: 댓글이 작성된 게시글 ID
     @PathVariable Long commentId: 조회할 댓글 ID
    Response: 성공 시 해당 게시물의 특정 댓글 정보를 JSON 형태로 반환함
    */
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentReadSingleResponse> getComment(
            @PathVariable Long postId,                                      // @PathVariable: URL로 전달된 값을 파라미터로 받아오는 역할을 함 (필수로 받아와야 하는 값)
            @PathVariable Long commentId                                    // @PathVariable: URL로 전달된 값을 파라미터로 받아오는 역할을 함 (필수로 받아와야 하는 값)
    ) {
        // 해당 게시물의 특정 댓글 정보를 가져와 HTTP 200 OK와 함께 반환함
        return ResponseEntity.ok(commentService.getComment(postId, commentId));
    }

    /*
    기능: 댓글 수정 (Update Comment)
    HTTP Method: PUT
    URL: /comments/{commentId} (http://localhost:8080/comments/{commentId})
    인증/인가 : 로그인 필요 (세션 기반)
    Request:
     @SessionAttribute("LOGIN_USER") Long userId: 로그인한 사용자 ID
     @PathVariable Long commentId: 수정할 댓글 ID
     @Valid @RequestBody CommentUpdateRequest commentUpdateRequest: 요청 Body에 담긴 수정할 댓글 내용
    Response: 성공 시 특정 댓글의 수정된 정보를 JSON 형태로 반환함
    */
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentUpdateResponse> updateComment(
            @SessionAttribute(name = "LOGIN_USER") Long userId,             // 로그인 한 사용자 ID
            @PathVariable Long commentId,                                   // @PathVariable: URL로 전달된 값을 파라미터로 받아오는 역할을 함 (필수로 받아와야 하는 값)
            @Valid @RequestBody CommentUpdateRequest commentUpdateRequest   // @Valid 적용, HTTP 요청 Body에 담긴 JSON 데이터를 CommentUpdateRequest 객체로 변환함
    ) {
        // 특정 댓글의 정보를 수정하고, 수정된 댓글 정보를 HTTP 200 OK와 함께 반환함
        return ResponseEntity.ok(commentService.updateComment(userId, commentId, commentUpdateRequest));
    }

    /*
    기능: 댓글 삭제 (Delete Comment)
    HTTP Method: DELETE
    URL: /comments/{commentId} (http://localhost:8080/comments/{commentId})
    인증/인가 : 로그인 필요 (세션 기반)
    Request:
     @SessionAttribute(name = "LOGIN_USER") Long userId: 로그인한 사용자 ID
     @PathVariable Long commentId: 삭제할 댓글 ID
    Response: 삭제 성공 시 HTTP 200 OK를 반환하고, Body는 없음
    */
    @DeleteMapping("comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @SessionAttribute(name = "LOGIN_USER") Long userId,             // 로그인 한 사용자 ID
            @PathVariable Long commentId                                    // @PathVariable: URL로 전달된 값을 파라미터로 받아오는 역할을 함 (필수로 받아와야 하는 값)
    ) {
        commentService.deleteComment(userId, commentId);                    // 해당 사용자의 특정 댓글을 삭제함

        return ResponseEntity.ok().build();                                 // HTTP 200 OK를 반환함 (응답 Body는 없음)
    }
}
