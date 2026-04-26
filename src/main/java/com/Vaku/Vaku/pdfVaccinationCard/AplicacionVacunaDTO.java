package com.Vaku.Vaku.pdfVaccinationCard;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AplicacionVacunaDTO {

    private String vaccName;  // Nombre de la vacuna
    private String vaapDateApplication;  // Fecha de aplicación
    private String vaapNextAppointmentDate;  // Fecha de próxima cita
    private Long vVaccId;  // ID de la vacuna (puedes agregar este si no está)

    // Getter y Setter para vaccName
    public String getVaccName() {
        return vaccName;
    }

    public void setVaccName(String vaccName) {
        this.vaccName = vaccName;
    }

    // Otros getters y setters
    public String getVaapDateApplication() {
        return vaapDateApplication;
    }

    public void setVaapDateApplication(String vaapDateApplication) {
        this.vaapDateApplication = vaapDateApplication;
    }

    public String getVaapNextAppointmentDate() {
        return vaapNextAppointmentDate;
    }

    public void setVaapNextAppointmentDate(String vaapNextAppointmentDate) {
        this.vaapNextAppointmentDate = vaapNextAppointmentDate;
    }

    public Long getvVaccId() {
        return vVaccId;
    }

    public void setvVaccId(Long vVaccId) {
        this.vVaccId = vVaccId;
    }
}
