package hello.newsfeed.follow.service;

import hello.newsfeed.follow.dto.request.FollowRequestDto;
import hello.newsfeed.follow.dto.response.FollowResponseDto;
import hello.newsfeed.follow.entity.Follow;
import hello.newsfeed.follow.repository.FollowsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class FollowService {

    //NOTE: Service 작성 전, 필요한 데이터를 끌어오는 작업 시작

    //필드(멤버 변수) 작성
    // ㄴ followsRepository 클래스의 DB 를 사용할 거기 때문에, 사용하기 전에 followsRepository 클래스의 주소를 적어주는 것임
    private  FollowsRepository followsRepository;



    // Repository 객체를 의존성 주입(DI, Dependency Injection)
    // Service는 Repository 없이는 동작할 수 없음 → 그래서 Repository를 외부(Spring 컨테이너) 에서 받아와야 함.
    public FollowService(FollowsRepository followsRepository) {
        this.followsRepository = followsRepository;
    }


    /** 생성자로 받은 Repository 객체를, service 클래스 안의 Repository 필드에 저장했기 때문에,
     * 이제부터 followService 안에서 언제든 followRepository를 쓸 수 있음.
     *
     */

    // NOTE 이제부터 Service 클래스 작성 시작!

    // TODO 사용자 팔로우

    // NOTE 0. Service 클래스에 필요한 Annotation을 적자! @Transactional 은 거의 필수!!
    // @Transactional : 이 메서드가 실행될 때, Spring이 트랜잭션을 시작하고, 끝날 때 commit 하여 DB에 반영.
    // 런타임 예외(RuntimeException, unchecked exception) 발생하면 롤백함
    @Transactional


    // NOTE 1. Service 매서드 생성 선언
    // followUser로 메서드를 호출하면, 결과 값을 FollowResponseDto 로 돌려줄 것임.
    // 변수 선언 : 타입: FollowRequestDto, 변수명: requestDto : “FollowRequestDto 타입 데이터를 담을 requestDto라는 변수를 만들겠다”
    public FollowResponseDto followUser(FollowRequestDto requestDto) {

        // NOTE 2. 클라이언트의 요청 데이터 가져오기
        // 클라이언트가 전달한 requestDto(요청 데이터)에서 팔로워 ID와 팔로잉 ID를 꺼냄.
        // 그 정보를 바탕으로 새 팔로우 객체(Follow)를 만들어 follow 변수에 저장한다.
        Follow follow = new Follow(requestDto.getFollowerId(), requestDto.getFollowingId());

        //NOTE 3. 새로 만든 follow 객체를 DB에 저장
        //followsRepository 는 JpaRepository<Follow, Long>를 상속했으므로, save() 메서드가 이미 구현되어 있음.
        // 이 코드를 실행하면 JPA가 내부적으로 SQL을 만들어 DB에 저장.
        // 반환값은 Follow 객체인데, DB에서 생성된 PK(id 값 등) 이 들어가 있는 상태.
        Follow savedFollow = followsRepository.save(follow);


        // NOTE 4. 응답 객체로 변환해서 FollowResponseDto 반환
        // 저장된 결과(savedFollow)에서 id, followerId, followingId를 꺼내와서
        // 응답 객체인 FollowResponseDto를 생성하고 반환
        return new FollowResponseDto(
                savedFollow.getId(),
                savedFollow.getFollowerId(),
                savedFollow.getFollowingId(),
                null

        );
    }
    // TODO 사용자 언팔로우
    public void unfollowUser(FollowRequestDto requestDto) {
        // 1. 삭제할 팔로우 객체 찾기
        Follow follow = followsRepository.findByFollowerIdAndFollowingId(
                requestDto.getFollowerId(),
                requestDto.getFollowingId()
        );

        if (follow != null) {
            // 2. DB에서 삭제 (하드 삭제 -> 해당 레코드 자체를 완전히 제거)
            followsRepository.delete(follow);
        }
    }
        // NOTE 5. 팔로잉 리스트 관리 (관계가 4 개)
        // 특정 사용자의 팔로잉 목록 조회 (내가 팔로우한 사람들)
        public List<Long> getFollowingList(Long userId) {
            return followsRepository.findAll().stream()
                    .filter(f -> f.getFollowerId().equals(userId))
                    .map(Follow::getFollowingId)
                    .collect(Collectors.toList());
        }

    // 나를 팔로우한 사람들
    public List<Long> getFollowerList(Long userId) {
        return followsRepository.findAll().stream()
                .filter(f -> f.getFollowingId().equals(userId))
                .map(Follow::getFollowerId)
                .collect(Collectors.toList());

    }
}