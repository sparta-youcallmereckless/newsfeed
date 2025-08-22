package hello.newsfeed.user.service;

import hello.newsfeed.common.config.PasswordEncoder;
import hello.newsfeed.user.dto.request.PasswordUpdateRequest;
import hello.newsfeed.user.dto.request.UserCreateRequest;
import hello.newsfeed.user.dto.request.UserUpdateRequest;
import hello.newsfeed.user.dto.response.UserCreateResponse;
import hello.newsfeed.user.dto.response.UserResponse;
import hello.newsfeed.user.dto.response.UserUpdateResponse;
import hello.newsfeed.user.entity.User;
import hello.newsfeed.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Transactional                                                                      // 생성이기 때문에  @Transactional 해줌 (@Transactional이 없으면 일부만 저장되고 데이터가 꼬일 수 있음)
    public UserCreateResponse save(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("해당 이메일은 이미 사용중입니다.");
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getUsername(),
                request.getEmail(),
                encodedPassword
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

    // 전체 유저 조회
    @Transactional(readOnly = true) // 트랜잭션을 읽기 전용으로 설정
    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        List<UserResponse> dtos = new ArrayList<>();

        for (User user : users) {
            dtos.add(new UserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getCreatedAt(),
                    user.getModifiedAt()
            ));
        }
        return dtos;
    }

    // 특정 유저 조회
    @Transactional(readOnly = true) // 트랜잭션을 읽기 전용으로 설정
    public UserResponse findOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("해당 id의 유저를 찾을 수 없습니다.")
        );
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    // 유저 정보 수정
    @Transactional
    public UserUpdateResponse updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 사용자입니다.")
        );

        /* 더티 체킹(Dirty Checking)을 활용하여 엔티티의 상태를 변경
        영속성 컨텍스트에 있는 엔티티의 필드가 변경되면, JPA가 이를 감지해서 자동으로 DB에 반영하는 기능 */
        user.updateEmail(
                request.getEmail()
        );
        return new UserUpdateResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    // 유저 비밀번호 수정
    @Transactional
    public Void updatePassword(Long userId, PasswordUpdateRequest passwordUpdateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 현재 비밀번호 불일치 → 400
        if (!passwordEncoder.matches(passwordUpdateRequest.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        user.updatePassword(passwordUpdateRequest.getNewPassword());
        return null;
    }

    @Transactional
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }
}

