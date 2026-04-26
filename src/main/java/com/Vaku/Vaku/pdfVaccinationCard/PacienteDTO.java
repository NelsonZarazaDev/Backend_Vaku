package com.Vaku.Vaku.pdfVaccinationCard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteDTO {
    private Long id;
    private String nombre;
    private String documento;
    private String fechaNacimiento;
    private String nombrePadre;
    private String direccion;
    private String telefono;
    private String correo;
}
