package hello.newsfeed.profile.service;

import hello.newsfeed.profile.dto.request.ProfileCreateRequest;
import hello.newsfeed.profile.dto.request.ProfileUpdateRequest;
import hello.newsfeed.profile.dto.response.ProfileCreateResponse;
import hello.newsfeed.profile.dto.response.ProfileResponse;
import hello.newsfeed.profile.dto.response.ProfileUpdateResponse;
import hello.newsfeed.profile.entity.Profile;
import hello.newsfeed.profile.repository.ProfileRepository;
import hello.newsfeed.user.entity.User;
import hello.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @Transactional
    public ProfileCreateResponse save(Long userId, ProfileCreateRequest profileCreateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));

        // 프로필 생성
        Profile newProfile = new Profile(
                profileCreateRequest.getUsername(),
                profileCreateRequest.getNickname(),
                profileCreateRequest.getEmail(),
                profileCreateRequest.getPassword(),
                user
        );

        // 프로필 저장
        Profile savedProfile = profileRepository.save(newProfile);
        return new ProfileCreateResponse(
                savedProfile.getId(),
                savedProfile.getUsername(),
                savedProfile.getNickname(),
                savedProfile.getEmail(),
                savedProfile.getCreatedAt(),
                savedProfile.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public ProfileResponse getMyProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
                );

        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로필이 존재하지 않습니다."));

        return new ProfileResponse(
                profile.getId(),
                profile.getUsername(),
                profile.getNickname(),
                profile.getEmail(),
                profile.getCreatedAt(),
                profile.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public ProfileResponse getOtherProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));


        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로필이 존재하지 않습니다."));

        return new ProfileResponse(
                profile.getId(),
                profile.getUsername(),
                profile.getNickname(),
                profile.getEmail(),
                profile.getCreatedAt(),
                profile.getModifiedAt()
        );
    }

    @Transactional
    public ProfileUpdateResponse update(Long userId, ProfileUpdateRequest profileUpdateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로필이 존재하지 않습니다."));

        // 프로필 변경
        profile.updateProfile(profileUpdateRequest.getNickname());

        // 더티체킹
        return new ProfileUpdateResponse(
                profile.getId(),
                profile.getUsername(),
                profile.getNickname(),
                profile.getEmail(),
                profile.getCreatedAt(),
                profile.getModifiedAt()
        );
    }

    @Transactional
    public void deleteProfile(Long userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로필이 존재하지 않습니다."));

        if (password.equals(user.getPassword())) {
            profileRepository.delete(profile);
        }
    }
}
