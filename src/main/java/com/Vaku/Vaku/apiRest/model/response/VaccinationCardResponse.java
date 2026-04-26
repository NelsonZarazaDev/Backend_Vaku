package com.Vaku.Vaku.apiRest.model.response;

import java.time.LocalDate;


public interface VaccinationCardResponse {

    Long getvaap_id();

    LocalDate getvaap_next_appointment_date();

    LocalDate getvaap_date_application();

    String getvaap_token();

    Long getvaap_empl_id();



    Long getvVacc_id();

    String getvVacc_name();



    String getpePers_names();

    String getpePers_last_names();

}