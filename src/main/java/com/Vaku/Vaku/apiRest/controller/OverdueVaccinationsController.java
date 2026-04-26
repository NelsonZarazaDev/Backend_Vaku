package com.Vaku.Vaku.apiRest.controller;

import com.Vaku.Vaku.apiRest.model.response.OverdueAppointmentResponse;
import com.Vaku.Vaku.apiRest.service.OverdueVaccinationsService;
import com.Vaku.Vaku.emailOverdueVaccinations.EmailOverdueVaccinationsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(path = "overdueVaccinations")
public class OverdueVaccinationsController {

    private final OverdueVaccinationsService overdueVaccinationsService;
    private final EmailOverdueVaccinationsHelper emailOverdueVaccinationsHelper;

    @Autowired
    public OverdueVaccinationsController(
            OverdueVaccinationsService overdueVaccinationsService,
            EmailOverdueVaccinationsHelper emailOverdueVaccinationsHelper
    ) {
        this.overdueVaccinationsService = overdueVaccinationsService;
        this.emailOverdueVaccinationsHelper = emailOverdueVaccinationsHelper;
    }

    @GetMapping()
    public ResponseEntity<Page<OverdueAppointmentResponse>> getOverdueAppointments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        int normalizedPage = Math.max(page, 0);
        int normalizedSize = Math.min(Math.max(size, 1), 200);
        return ResponseEntity.ok(overdueVaccinationsService.findByNextAppointmentDateBefore(normalizedPage, normalizedSize));
    }

    @PostMapping("/send-emails")
    public ResponseEntity<Void> sendOverdueEmails() {
        Set<OverdueAppointmentResponse> overdueAppointments = overdueVaccinationsService.findByNextAppointmentDateBefore();

        for (OverdueAppointmentResponse appointment : overdueAppointments) {
            String emailFather = appointment.getParentEmail();
            if (Objects.nonNull(emailFather)) {
                emailOverdueVaccinationsHelper.sendEmail(List.of(emailFather));
            }
        }

        return ResponseEntity.ok().build();
    }
}
