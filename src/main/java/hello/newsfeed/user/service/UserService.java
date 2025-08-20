package hello.newsfeed.user.service;

import hello.newsfeed.user.dto.request.UserCreateRequest;
import hello.newsfeed.user.dto.response.UserCreateResponse;
import hello.newsfeed.user.entity.User;
import hello.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 생성이기 때문에  @Transactional 해줌
    // @Transactional이 없으면 일부만 저장되고 데이터가 꼬일 수 있음
    @Transactional
    public UserCreateResponse save(UserCreateRequest request) {
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );
        User savedUser = userRepository.save(user);
        return new UserCreateResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt()
        );
    }
}
