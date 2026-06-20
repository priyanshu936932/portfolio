package com.priyanshu.portfolio.controller;

import com.priyanshu.portfolio.dto.LoginRequest;
import com.priyanshu.portfolio.dto.LoginResponse;
import com.priyanshu.portfolio.exception.InvalidCredentialsException;
import com.priyanshu.portfolio.model.User;
import com.priyanshu.portfolio.repository.UserRepository;
import com.priyanshu.portfolio.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
        throw new InvalidCredentialsException("Invalid email or password");
    }

    String token = jwtService.generateToken(user.getEmail());
    return ResponseEntity.ok(new LoginResponse(token, user.getEmail()));
}
}