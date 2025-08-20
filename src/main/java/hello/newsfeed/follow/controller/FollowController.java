package hello.newsfeed.follow.controller;


import hello.newsfeed.follow.dto.request.FollowRequestDto;
import hello.newsfeed.follow.dto.response.FollowResponseDto;
import hello.newsfeed.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 이 클래스가 "REST API의 컨트롤러"라는 것을 스프링에게 알려줌 (웹 브라우저나 클라이언트에서 오는 HTTP 요청을 받는 역할)
@RequestMapping("/api/follows") // 이 컨트롤러에서 제공하는 API의 기본 URL 경로를 지정 (즉, "/api/follows"로 시작하는 요청은 이 컨트롤러가 처리)
@RequiredArgsConstructor // 스프링이 자동으로 생성자 주입(Dependency Injection)

public class FollowController {

    private final FollowService followService;


    // "팔로우 기능" 담당 API.
    // HTTP 메서드 중 POST 요청을 받으며, URL에 {followerId}라는 값을 같이 받아옴.
    // 예: POST /api/follows/1 → "1번 사용자가 팔로우를 한다"라는 의미
    @PostMapping("/{followerId}")
    public ResponseEntity<FollowResponseDto> follow(@RequestBody FollowRequestDto requestDto) {
        // 서비스에서 팔로우 로직 실행
        FollowResponseDto response = followService.followUser(requestDto);

        return ResponseEntity.ok(response);
    }
}

    /**& public ResponseEntity<String> follow(
            // URL 경로에서 followerId 값을 꺼내옴. 즉, "누가 팔로우를 하는지"를 알 수 있음.
            @PathVariable Long followerId,
            //요청 바디(JSON 형식)에 들어 있는 followRequestDto 데이터를 꺼내옴. 이 안에는 "누구를 팔로우할지 (followeeId)" 정보가 들어 있음.
            @RequestBody FollowRequestDto followRequestDto
            **/

        // "Service 계층"에 있는 follow() 메서드를 호출해서 실제 비즈니스 로직(팔로우 저장)을 처리.
        // “followService라는 객체 변수 안의 follow 메서드를 호출해서, followerId와 requestDto를 전달하고, 결과 문자열을 message 변수에 담는다.”
       //String message = followService.follow(followerId, requestDto);

