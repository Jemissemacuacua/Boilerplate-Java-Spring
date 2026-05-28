package com.app.builderplante.core.security;

import com.app.builderplante.core.exception.AppException;
import com.app.builderplante.modules.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthDTO.LoginResponse login(AuthDTO.LoginRequest dto) {
        var user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> AppException.unauthorized("Credenciais inválidas"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw AppException.unauthorized("Credenciais inválidas");
        }

        if (!user.isActive()) {
            throw AppException.forbidden("Conta desactivada");
        }

        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());
        return new AuthDTO.LoginResponse(token, user.getEmail(), user.getRole().name());
    }
}
