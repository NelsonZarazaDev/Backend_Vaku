package com.Vaku.Vaku.apiRest.model.response;

import java.time.LocalDate;


public interface VaccinationCardResponse {

    Long getVaapId();

    LocalDate getVaapNextAppointmentDate();

    LocalDate getVaapDateApplication();

    String getVaapToken();

    Long getVaapEmplId();



    Long getVaccId();

    String getVaccName();



    String getPePersNames();

    String getPePersLastNames();

}
