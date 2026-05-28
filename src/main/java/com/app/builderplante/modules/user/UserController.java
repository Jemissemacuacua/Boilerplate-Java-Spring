package com.app.builderplante.modules.user;

import com.app.builderplante.core.pagination.PageResponse;
import com.app.builderplante.core.response.ApiResponse;
import com.app.builderplante.shared.BaseController;
import com.app.builderplante.shared.BaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController extends BaseController<User, Long> {

    private final UserService userService;

    @Override
    protected BaseService<User, Long> getService() {
        return userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO.Response>> create(@Valid @RequestBody UserDTO.Request dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(userService.create(dto), "Utilizador criado com sucesso"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<UserDTO.Response>>> findAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(ApiResponse.success(userService.findAllActive(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO.Response>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(userService.findByIdOrThrow(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO.Response>> update(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO.UpdateRequest dto) {
        return ResponseEntity.ok(ApiResponse.success(userService.update(id, dto), "Utilizador actualizado com sucesso"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deactivate(@PathVariable Long id) {
        userService.deactivate(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Utilizador desactivado com sucesso"));
    }
}
