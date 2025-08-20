package hello.newsfeed.follow.service;

import hello.newsfeed.follow.dto.request.FollowRequestDto;
import hello.newsfeed.follow.dto.response.FollowResponseDto;
import hello.newsfeed.follow.entity.Follow;
import hello.newsfeed.follow.repository.FollowsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class FollowService {

    private final FollowsRepository followsRepository;

    // 생성자 주입: 스프링이 자동으로 Repository 객체를 넣어줌
    public FollowService(FollowsRepository followsRepository) {
        this.followsRepository = followsRepository;
    }

    @Transactional // DB에 저장/변경 시 사용 (롤백 기능 제공)
    public FollowResponseDto followUser(FollowRequestDto requestDto) {
        // 1. 팔로우 객체 생성
        Follow follow = new Follow(requestDto.getFollowerId(), requestDto.getFollowingId());

        // 2. DB에 저장
        Follow savedFollow = followsRepository.save(follow);

        // 3. 응답 객체로 변환해서 반환
        return new FollowResponseDto(
                savedFollow.getId(),
                savedFollow.getFollowerId(),
                savedFollow.getFollowingId()
        );
    }
}