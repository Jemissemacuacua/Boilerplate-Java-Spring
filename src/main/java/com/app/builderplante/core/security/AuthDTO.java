package com.app.builderplante.core.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class AuthDTO {

    @Getter
    @Setter
    public static class LoginRequest {
        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        private String email;

        @NotBlank(message = "Password é obrigatória")
        private String password;
    }

    @Getter
    @Setter
    public static class LoginResponse {
        private String token;
        private String email;
        private String role;

        public LoginResponse(String token, String email, String role) {
            this.token = token;
            this.email = email;
            this.role = role;
        }
    }
}
