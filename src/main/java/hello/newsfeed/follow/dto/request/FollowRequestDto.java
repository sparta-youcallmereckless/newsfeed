package hello.newsfeed.follow.dto.request;

import lombok.Getter;

@Getter
public class FollowRequestDto {
    private Long followerId;  // 팔로우 하는 사용자 ID
    private Long followingId; // 팔로우 당하는 사용자 ID
}
