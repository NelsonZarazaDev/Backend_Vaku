package com.Vaku.Vaku.pdfVaccinationCard;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParentChildInfoDTO {
    @JsonProperty("chilId")
    private Long childId;
    private String childNames;
    private String childLastNames;
    private String childDocument;
    private String childBirthDate;
    private String childAddress;
    private String parentNames;
    private String parentLastNames;
    private String parentPhone;
    private String parentEmail;
    private String parentAddress;
}

