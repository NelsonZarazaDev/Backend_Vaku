package com.Vaku.Vaku.pdfVaccinationCard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VacunaInfoDTO {
    private int vaccId;
    private String vaccName;
    private String vaccAgeDose;
    private String vaccDosage;

    // Agregar estos campos si no están
    private String inveLaboratory;
    private String inveLot;

}


