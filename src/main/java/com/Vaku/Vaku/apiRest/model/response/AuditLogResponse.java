package com.Vaku.Vaku.apiRest.model.response;

import com.Vaku.Vaku.apiRest.model.entity.AuditLogsEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
public class AuditLogResponse {
    private Long auditId;
    private String httpMethod;
    private String requestPath;
    private String queryString;
    private Integer statusCode;
    private Boolean success;
    private Long durationMs;
    private String clientIp;
    private String userAgent;
    private String actorIdentifier;
    private String actorRole;
    private LocalDateTime createdAt;
    private String action;
    private String module;

    public static AuditLogResponse fromEntity(AuditLogsEntity entity) {
        String moduleKey = resolveModuleKey(entity.getRequestPath());
        String moduleLabel = MODULE_LABELS.getOrDefault(moduleKey, "Sistema");
        String action = buildAction(entity.getHttpMethod(), moduleLabel);

        return AuditLogResponse.builder()
                .auditId(entity.getAuditId())
                .httpMethod(entity.getHttpMethod())
                .requestPath(entity.getRequestPath())
                .queryString(entity.getQueryString())
                .statusCode(entity.getStatusCode())
                .success(entity.getSuccess())
                .durationMs(entity.getDurationMs())
                .clientIp(entity.getClientIp())
                .userAgent(entity.getUserAgent())
                .actorIdentifier(entity.getActorIdentifier())
                .actorRole(entity.getActorRole())
                .createdAt(entity.getCreatedAt())
                .action(action)
                .module(moduleLabel)
                .build();
    }

    private static String buildAction(String method, String moduleLabel) {
        String verb = switch (method == null ? "" : method.toUpperCase()) {
            case "POST" -> "Creo";
            case "PUT", "PATCH" -> "Actualizo";
            case "DELETE" -> "Elimino";
            default -> "Ejecuto accion en";
        };
        return verb + " " + moduleLabel;
    }

    private static String resolveModuleKey(String requestPath) {
        if (requestPath == null || requestPath.isBlank()) {
            return "sistema";
        }

        String[] parts = requestPath.split("/");
        for (String part : parts) {
            if (part == null || part.isBlank()) {
                continue;
            }
            if (part.matches("^[0-9]+$")) {
                continue;
            }
            if (part.matches("^[0-9a-fA-F-]{8,}$")) {
                continue;
            }
            return part.toLowerCase();
        }
        return "sistema";
    }

    private static final Map<String, String> MODULE_LABELS = Map.ofEntries(
            Map.entry("employee", "empleado"),
            Map.entry("persons", "persona"),
            Map.entry("vaccines", "vacuna"),
            Map.entry("vaccineapplied", "aplicacion de vacuna"),
            Map.entry("vaccinescard", "carnet de vacunacion"),
            Map.entry("auth", "autenticacion"),
            Map.entry("departments", "departamento"),
            Map.entry("citys", "ciudad"),
            Map.entry("inventories", "inventario"),
            Map.entry("overduevaccinations", "prioridad"),
            Map.entry("report", "reporte"),
            Map.entry("audit", "auditoria"),
            Map.entry("sistema", "sistema")
    );
}
