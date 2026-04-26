package com.Vaku.Vaku.apiRest.service;

import com.Vaku.Vaku.apiRest.model.entity.AuditLogsEntity;
import com.Vaku.Vaku.apiRest.model.entity.PersonsEntity;
import com.Vaku.Vaku.apiRest.model.response.AuditLogResponse;
import com.Vaku.Vaku.apiRest.repository.AuditLogsRepository;
import com.Vaku.Vaku.apiRest.repository.PersonsRepository;
import com.Vaku.Vaku.utils.RoleUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditLogsService {
    private final AuditLogsRepository auditLogsRepository;
    private final PersonsRepository personsRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void register(HttpServletRequest request, HttpServletResponse response, long durationMs) {
        try {
            String identifier = getCurrentIdentifier();
            String actorRole = "ANONIMO";

            try {
                Optional<PersonsEntity> actor = findActor(identifier);
                actorRole = actor.map(PersonsEntity::getPersRole).orElse("ANONIMO");
            } catch (Exception ignored) {
                // No interrumpir la auditoria si falla la resolucion del actor.
            }

            AuditLogsEntity logEntry = AuditLogsEntity.builder()
                    .httpMethod(limit(request.getMethod(), 10))
                    .requestPath(limit(request.getRequestURI(), 300))
                    .queryString(limit(request.getQueryString(), 1000))
                    .statusCode(response.getStatus())
                    .success(response.getStatus() < 400)
                    .durationMs(Math.max(durationMs, 0))
                    .clientIp(limit(resolveIp(request), 100))
                    .userAgent(limit(request.getHeader("User-Agent"), 700))
                    .actorIdentifier(limit(identifier, 150))
                    .actorRole(limit(actorRole, 120))
                    .createdAt(LocalDateTime.now())
                    .build();

            auditLogsRepository.save(logEntry);
        } catch (Exception exception) {
            log.warn("No se pudo registrar auditoria para {} {}: {}",
                    request.getMethod(), request.getRequestURI(), exception.getMessage());
        }
    }

    public Page<AuditLogResponse> getAuditLogs(int page, int size) {
        assertHeadNurseRole();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return auditLogsRepository.findAll(pageable).map(AuditLogResponse::fromEntity);
    }

    private void assertHeadNurseRole() {
        String identifier = getCurrentIdentifier();
        PersonsEntity person = findActor(identifier)
                .orElseThrow(() -> new AccessDeniedException("No tiene permisos para consultar auditoria"));

        String normalizedRole = RoleUtils.normalizeRole(person.getPersRole());
        if (!"jefe de enfermeria".equals(normalizedRole)) {
            throw new AccessDeniedException("Solo el Jefe de enfermeria puede consultar auditoria");
        }
    }

    private Optional<PersonsEntity> findActor(String identifier) {
        if (identifier == null || identifier.isBlank() || "ANONIMO".equals(identifier)) {
            return Optional.empty();
        }

        Optional<PersonsEntity> byEmail = personsRepository.findByPersEmailIgnoreCase(identifier);
        if (byEmail.isPresent()) {
            return byEmail;
        }
        return personsRepository.findByPersDocument(identifier);
    }

    private String getCurrentIdentifier() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "ANONIMO";
        }
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            return "ANONIMO";
        }
        return principal.toString();
    }

    private String resolveIp(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    private String limit(String value, int maxLength) {
        if (value == null) {
            return null;
        }
        if (value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength);
    }
}
