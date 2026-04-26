package com.Vaku.Vaku.apiRest.controller;

import com.Vaku.Vaku.apiRest.model.response.AuditLogResponse;
import com.Vaku.Vaku.apiRest.service.AuditLogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
public class AuditLogsController {
    private final AuditLogsService auditLogsService;

    @GetMapping("/logs")
    public Page<AuditLogResponse> getAuditLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    ) {
        int normalizedPage = Math.max(page, 0);
        int normalizedSize = Math.min(Math.max(size, 1), 200);
        return auditLogsService.getAuditLogs(normalizedPage, normalizedSize);
    }
}
