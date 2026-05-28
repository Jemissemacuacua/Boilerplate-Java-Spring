package com.app.builderplante.core.security;

import com.app.builderplante.core.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthDTO.LoginResponse>> login(@Valid @RequestBody AuthDTO.LoginRequest dto) {
        return ResponseEntity.ok(ApiResponse.success(authService.login(dto), "Login realizado com sucesso"));
    }
}
