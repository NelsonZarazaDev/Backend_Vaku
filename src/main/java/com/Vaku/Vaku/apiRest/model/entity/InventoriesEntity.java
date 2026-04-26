package com.Vaku.Vaku.apiRest.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity(name = "inventories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoriesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inveId;
    @NotBlank(message = "El laboratorio no puede estar vacios")
    private String inveLaboratory;
    @NotBlank(message = "El serial de lote no puede estar vacios")
    private String inveLot;
    @Min(value = 0, message = "La cantidad de vacunas no puede ser un valor inferior a 0")
    @NotNull(message = "La cantidad de vacunas no puede estar vacio")
    private Integer inveQuantity;
    private LocalDate inveDate;
    private String inveToken = (Integer.toString((int) System.nanoTime()) + "" +
            (Math.random() * 100) + UUID.randomUUID() +
            (Math.random() * 100) + UUID.randomUUID() +
            System.nanoTime() + "" +
            (Math.random() * 100));

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "inventories", fetch = FetchType.LAZY)
    private Set<InventoriesEmployeesEntity> inventories_employees;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "inventories", fetch = FetchType.LAZY)
    private Set<VaccinesEntity> vaccines;
}
