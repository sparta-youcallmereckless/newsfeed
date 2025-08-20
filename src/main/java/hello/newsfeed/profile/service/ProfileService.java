package hello.newsfeed.profile.service;

import hello.newsfeed.profile.dto.request.ProfileCreateRequest;
import hello.newsfeed.profile.dto.response.ProfileCreateResponse;
import hello.newsfeed.profile.dto.response.ProfileResponse;
import hello.newsfeed.user.entity.User;
import hello.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    public ProfileCreateResponse savedProfile(ProfileCreateRequest profileCreateRequest) {
        User user = new User(
                profileCreateRequest.getUsername(),
                profileCreateRequest.getEmail(),
                profileCreateRequest.getPassword()
        );
        User savedProfile = userRepository.save(user);
        return new ProfileCreateResponse(
                savedProfile.getId(),
                savedProfile.getUsername(),
                savedProfile.getEmail(),
                savedProfile.getCreatedAt(),
                savedProfile.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public ProfileResponse getMyProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user not found")); // TODO: 예외처리 변경 예정

        return new ProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }
}
