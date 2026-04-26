package com.Vaku.Vaku.pdfVaccinationCard;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CarnetDataRequest {
    private ParentChildInfoDTO info;
    private List<AplicacionVacunaDTO> aplicaciones;
    private List<VacunaInfoDTO> vacunas; // Asegúrate de que esto esté correctamente definido

    // Getters y Setters
    public ParentChildInfoDTO getInfo() {
        return info;
    }

    public void setInfo(ParentChildInfoDTO info) {
        this.info = info;
    }

    public List<AplicacionVacunaDTO> getAplicaciones() {
        return aplicaciones;
    }

    public void setAplicaciones(List<AplicacionVacunaDTO> aplicaciones) {
        this.aplicaciones = aplicaciones;
    }

    public List<VacunaInfoDTO> getVacunas() {
        return vacunas;
    }

    public void setVacunas(List<VacunaInfoDTO> vacunas) {
        this.vacunas = vacunas;
    }
}