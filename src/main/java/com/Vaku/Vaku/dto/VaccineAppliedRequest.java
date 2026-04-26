package com.Vaku.Vaku.dto;

import com.Vaku.Vaku.apiRest.model.entity.VaccinesAppliedEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccineAppliedRequest {
    @Valid
    @NotNull(message = "La informacion de vacuna aplicada es obligatoria")
    private VaccinesAppliedEntity vaccinesApplied;
    private String emailFather;
}
