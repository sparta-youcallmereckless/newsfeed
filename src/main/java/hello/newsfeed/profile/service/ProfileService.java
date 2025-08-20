package hello.newsfeed.profile.service;

import hello.newsfeed.profile.dto.request.ProfileCreateRequest;
import hello.newsfeed.profile.dto.response.ProfileCreateResponse;
import hello.newsfeed.user.entity.User;
import hello.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
