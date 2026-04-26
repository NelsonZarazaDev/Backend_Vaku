package com.Vaku.Vaku.apiRest.model.response;


import java.time.LocalDate;

public interface VaccinesResponse {
    String getVaccName();
    String getVaccAgeDose();
    String getVaccDosage();
    String getInveLaboratory();
    String getInveLot();
    String getInveQuantity();

    LocalDate getVaapDateApplication();
    LocalDate getVaapNextAppointmentDate();
}
