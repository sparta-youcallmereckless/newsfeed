package hello.newsfeed.post.controller;

import hello.newsfeed.common.consts.Const;
import hello.newsfeed.post.dto.request.PostRequest;
import hello.newsfeed.post.dto.response.PostResponse;

import hello.newsfeed.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
// 클라이언트의 HTTP 요청을 처리하고 JSON 응답을 반환
@RequiredArgsConstructor
// final 필드(postService)에 대한 생성자를 자동으로 만들어 의존성 주입
public class PostController {


    private final PostService postService;
    /*
    PostService 의존성 주입
    실제 게시물 생성, 조회, 수정, 삭제 로직은 서비스에서 수행
     */

    @PostMapping("/posts")
    /*
    HTTP POST 요청 경로 이하 생략 "/Posts"와 매핑
    새로운 게시물 생성 요청
     */
    public ResponseEntity<PostResponse> savePost(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @Valid @RequestBody PostRequest postRequest
    ) {
        /*
        클라이언트 요청 본문에 담긴 PostRequest를 PostRequest로 받아옴(로그인 사용자Id)
        @RequestBody는 JSON을 PostRequest 객체로 변환
         */

        return ResponseEntity.ok(postService.savePost(userId, postRequest));
        /*
         서비스 savePost 호출
         디비 저장 후 DTO로 변환된 결과를 반환
         */
    }

    @GetMapping("/posts")
    // 전체 게시물 조회 요청
    public ResponseEntity<Page<PostResponse>> findAllPosts(
            @PageableDefault(size = 10, sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable)
    //페이징 및 정렬정보를 담는 Pageable 객체 주입
    {
        return ResponseEntity.ok(postService.findAllPosts(pageable));
        /*
        서비스 findAllPosts 호출
        디비에서 모든 게시물 조회 DTO 패이징 반환
        */
    }

    @GetMapping("/posts/{postId}")
    // 단건 게시물 조회 요청
    public ResponseEntity<PostResponse> findPostById(
            @PathVariable Long postId
            //URL경로에서 postId값을 추출하여 변수에 받기
    ) {
        return ResponseEntity.ok(postService.findPostById(postId));
        /*
        서비스 findPostById호출
        DB 에서 사용자가 지정한 개시물 조회후 반환
        */
    }

    @PutMapping("/posts/{postId}")
    //수정 메서드
    public ResponseEntity<PostResponse> updatePost(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            //세션에서 로그인한 사용자의 Id를 가져옴
            @Valid @RequestBody PostRequest postRequest,
            //HTTP 요청 본문을 PostRequest 객체로 변환 벨리드로 유효성 검사
            @PathVariable Long postId
            /*
            본문에서(Json형태)로 내용을 PostRequest로 받고
            사용자에게 수정할 게시물 ID(포스트 아이디)를 받음
             */
    ) {
        return ResponseEntity.ok(postService.updatePost(userId, postId, postRequest));
    }
    //서비스 업데이트 호출 수정후 DTO 반화

    @DeleteMapping("/posts/{postId}")
    //삭제 기능
    public void deletePost(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            //세션에서 로그인한 사용자의 Id를 받아옴
            @PathVariable Long postId
            /* 리퀘스트 파람으로 userID 삭제할사람 식별
            패스 베리어블로 삭제할 포스트 식별
             */
    ) {
        postService.deletePost(postId, userId);
    }
//서비스에서 삭제 조건 확인후 DB에서 삭제 수행
}