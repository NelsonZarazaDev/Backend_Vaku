package com.Vaku.Vaku.apiRest.controller;

import com.Vaku.Vaku.apiRest.service.AuthService;
import com.Vaku.Vaku.apiRest.service.AuditLogsService;
import com.Vaku.Vaku.dto.LoginChildRequest;
import com.Vaku.Vaku.dto.LoginEmployeeRequest;
import com.Vaku.Vaku.dto.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginEmployeeRequest request,
            HttpServletRequest servletRequest
    ) throws Exception {
        servletRequest.setAttribute(AuditLogsService.ACTOR_IDENTIFIER_ATTR, request.getPersEmail());
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "loginChild")
    public ResponseEntity<AuthResponse> loginChild(
            @Valid @RequestBody LoginChildRequest request,
            HttpServletRequest servletRequest
    ) throws Exception {
        servletRequest.setAttribute(AuditLogsService.ACTOR_IDENTIFIER_ATTR, request.getPersDocument());
        return ResponseEntity.ok(authService.loginChild(request));
    }
}
