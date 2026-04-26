package com.Vaku.Vaku.pdfVaccinationCard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VacunaDTO {
    private String edad;
    private String nombreVacuna;
    private String dosis;
    private String fechaAplicacion;
    private String laboratorio;
    private String numeroLote;
    private String fechaProximaCita;
    private Long inveId;
}
