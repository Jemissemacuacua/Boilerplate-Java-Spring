package com.app.builderplante.core.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;
    private final ObjectMapper objectMapper;

    @Async
    public void log(String action, String entity, String entityId,
                    Object previousState, Object newState, String ipAddress) {
        try {
            String performer = getCurrentUser();

            AuditLog auditLog = AuditLog.builder()
                    .action(action)
                    .entity(entity)
                    .entityId(entityId)
                    .performedBy(performer)
                    .previousState(previousState != null ? objectMapper.writeValueAsString(previousState) : null)
                    .newState(newState != null ? objectMapper.writeValueAsString(newState) : null)
                    .ipAddress(ipAddress)
                    .build();

            auditLogRepository.save(auditLog);
        } catch (Exception e) {
            log.error("Erro ao guardar audit log: {}", e.getMessage());
        }
    }

    public void log(String action, String entity, String entityId,
                    Object previousState, Object newState) {
        log(action, entity, entityId, previousState, newState, null);
    }

    private String getCurrentUser() {
        try {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                return auth.getName();
            }
        } catch (Exception ignored) {}
        return "system";
    }
}
