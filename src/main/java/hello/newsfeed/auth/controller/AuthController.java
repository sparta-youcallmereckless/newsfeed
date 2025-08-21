package hello.newsfeed.auth.controller;

import hello.newsfeed.auth.dto.request.AuthRequest;
import hello.newsfeed.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/users/login")
    public ResponseEntity<String> login(
            @RequestBody AuthRequest authRequest,
            HttpServletRequest request
    ) {
        // Cookie Session을 발급
        Long userId = authService.login(authRequest);

        HttpSession session = request.getSession(); // 신규 세션을 생성, 쿠키 발급
        session.setAttribute("LOGIN_USER", userId); // 서버 메모리에 세션 저장, 로그인 정보를 유지

        return ResponseEntity.ok("로그인 되었습니다.");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}
//주석