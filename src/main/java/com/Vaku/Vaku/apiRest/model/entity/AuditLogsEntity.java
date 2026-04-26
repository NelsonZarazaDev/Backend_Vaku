package com.Vaku.Vaku.apiRest.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id")
    private Long auditId;

    @Column(name = "http_method", nullable = false, length = 10)
    private String httpMethod;

    @Column(name = "request_path", nullable = false, length = 300)
    private String requestPath;

    @Column(name = "query_string", length = 1000)
    private String queryString;

    @Column(name = "status_code", nullable = false)
    private Integer statusCode;

    @Column(name = "success", nullable = false)
    private Boolean success;

    @Column(name = "duration_ms", nullable = false)
    private Long durationMs;

    @Column(name = "client_ip", length = 100)
    private String clientIp;

    @Column(name = "user_agent", length = 700)
    private String userAgent;

    @Column(name = "actor_identifier", length = 150)
    private String actorIdentifier;

    @Column(name = "actor_role", length = 120)
    private String actorRole;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
