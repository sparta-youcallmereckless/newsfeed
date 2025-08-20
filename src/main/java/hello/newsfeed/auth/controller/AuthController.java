package hello.newsfeed.auth.controller;

import hello.newsfeed.auth.dto.request.AuthRequest;
import hello.newsfeed.auth.dto.response.AuthResponse;
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

    @PostMapping("/auth/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/login")
    public String login(
            @RequestBody AuthRequest authRequest,
            HttpServletRequest request
    ) {
        // Cookie Session을 발급
        AuthResponse result = authService.login(authRequest);

        HttpSession session = request.getSession();
        session.setAttribute("LOGIN_DIRECTOR", result.getId());
        return "로그인에 성공했습니다.";
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
