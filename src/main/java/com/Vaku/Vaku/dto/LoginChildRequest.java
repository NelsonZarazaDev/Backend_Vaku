package com.Vaku.Vaku.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginChildRequest {
    @NotBlank(message = "El documento es obligatorio")
    private String persDocument;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate persDateBirth;
}
