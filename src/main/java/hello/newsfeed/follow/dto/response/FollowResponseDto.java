package hello.newsfeed.follow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder /// <<< 이거 확인
@Data // Lombok 라이브러리 제공 (자동으로 Getter / Setter / toSting / 기본 생정자 등을 생성. 하지만 final 을 먼저 선언하면 setter 는 선언 안해줌)
public class FollowResponseDto {

    private Long id;          // 팔로우 관계의 ID (DB에서 자동 생성됨)
    private Long followerId;  // 팔로우 한 사람
    private Long followingId; // 팔로우 당한 사람
    private String message; // 응답 메시지

}
