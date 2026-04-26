package com.Vaku.Vaku.apiRest.model.response;

import java.time.LocalDate;

public interface OverdueAppointmentResponse {
    Long getChilId();
    String getChilToken();
    LocalDate getVaapNextAppointmentDate();
    String getChildDocument();

    // Niño
    String getChildNames();
    String getChildLastNames();

    // Padre
    String getParentNames();
    String getParentLastNames();
    String getParentPhone();
    String getParentEmail();
}