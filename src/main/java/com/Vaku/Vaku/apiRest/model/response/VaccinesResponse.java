package com.Vaku.Vaku.apiRest.model.response;


import java.time.LocalDate;

public interface VaccinesResponse {
    Long getVaccId();
    String getVaccName();
    String getVaccAgeDose();
    String getVaccDosage();
    String getInveLaboratory();
    String getInveLot();
    String getInveQuantity();

    Long getVaapId();
    Boolean getVaapApplied();
    LocalDate getVaapDateApplication();
    LocalDate getVaapNextAppointmentDate();
}
