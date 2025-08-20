package hello.newsfeed.auth.service;

import hello.newsfeed.auth.dto.request.AuthRequest;
import hello.newsfeed.auth.dto.response.AuthResponse;
import hello.newsfeed.auth.entity.Auth;
import hello.newsfeed.auth.repository.AuthRepository;
import hello.newsfeed.common.config.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    // 사용자 등록(=회원가입)
    @Transactional
    public AuthResponse signup(AuthRequest request) {
        if (authRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("해당 이메일은 이미 사용중입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Auth auth = new Auth(request.getName(), request.getEmail(), encodedPassword);
        authRepository.save(auth);
        return new AuthResponse(
                auth.getId(),
                auth.getName(),
                auth.getEmail(),
                auth.getCreatedAt()
        );
    }

    // 로그인
    @Transactional(readOnly = true)
    public AuthResponse login(AuthRequest request) {
        Auth auth = authRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
        );
        return new AuthResponse(
                auth.getId(),
                auth.getName(),
                auth.getEmail(),
                auth.getCreatedAt()
        ); // 튜터님께 질문...
    }
}
