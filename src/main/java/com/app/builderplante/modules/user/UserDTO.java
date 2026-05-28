package com.app.builderplante.modules.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class UserDTO {

    @Getter
    @Setter
    public static class Request {

        @NotBlank(message = "Nome é obrigatório")
        private String name;

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        private String email;

        @NotBlank(message = "Password é obrigatória")
        @Size(min = 8, message = "Password deve ter no mínimo 8 caracteres")
        private String password;

        @NotNull(message = "Role é obrigatório")
        private UserRole role;
    }

    @Getter
    @Setter
    public static class Response {
        private Long id;
        private String name;
        private String email;
        private UserRole role;
        private boolean active;
    }

    @Getter
    @Setter
    public static class UpdateRequest {
        private String name;

        @Email(message = "Email inválido")
        private String email;
    }
}
