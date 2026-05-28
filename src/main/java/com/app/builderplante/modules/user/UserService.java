package com.app.builderplante.modules.user;

import com.app.builderplante.core.audit.AuditService;
import com.app.builderplante.core.exception.AppException;
import com.app.builderplante.core.pagination.PageRequestBuilder;
import com.app.builderplante.core.pagination.PageResponse;
import com.app.builderplante.shared.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService extends BaseService<User, Long> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuditService auditService;

    @Override
    protected JpaRepository<User, Long> getRepository() {
        return userRepository;
    }

    public UserDTO.Response create(UserDTO.Request dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw AppException.conflict("Email já está em uso");
        }
        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User saved = userRepository.save(user);
        auditService.log("CREATE", "User", String.valueOf(saved.getId()), null, userMapper.toResponse(saved));
        return userMapper.toResponse(saved);
    }

    public PageResponse<UserDTO.Response> findAllActive(Integer page, Integer size) {
        return PageResponse.of(
                userRepository.findAllByActiveTrue(PageRequestBuilder.build(page, size))
                        .map(userMapper::toResponse)
        );
    }

    public UserDTO.Response findByIdOrThrow(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> AppException.notFound("Utilizador não encontrado"));
    }

    public UserDTO.Response update(Long id, UserDTO.UpdateRequest dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> AppException.notFound("Utilizador não encontrado"));

        UserDTO.Response previousState = userMapper.toResponse(user);

        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getEmail() != null) {
            if (userRepository.existsByEmail(dto.getEmail())) {
                throw AppException.conflict("Email já está em uso");
            }
            user.setEmail(dto.getEmail());
        }

        User saved = userRepository.save(user);
        auditService.log("UPDATE", "User", String.valueOf(id), previousState, userMapper.toResponse(saved));
        return userMapper.toResponse(saved);
    }

    public void deactivate(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> AppException.notFound("Utilizador não encontrado"));
        UserDTO.Response previousState = userMapper.toResponse(user);
        user.setActive(false);
        userRepository.save(user);
        auditService.log("DEACTIVATE", "User", String.valueOf(id), previousState, null);
    }
}
