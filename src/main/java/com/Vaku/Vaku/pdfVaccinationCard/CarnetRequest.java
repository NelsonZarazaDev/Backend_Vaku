package com.Vaku.Vaku.pdfVaccinationCard;

import java.util.List;

public class CarnetRequest {
    private PacienteDTO paciente;
    private List<VacunaDTO> vacunas;

    // Getters y Setters

    public PacienteDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDTO paciente) {
        this.paciente = paciente;
    }

    public List<VacunaDTO> getVacunas() {
        return vacunas;
    }

    public void setVacunas(List<VacunaDTO> vacunas) {
        this.vacunas = vacunas;
    }
}