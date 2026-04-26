package com.Vaku.Vaku.apiRest.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity(name = "vaccines_applied")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VaccinesAppliedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vaapId;
    @Future(message = "La fecha no puede ser anterior a la actual")
    private LocalDate vaapNextAppointmentDate;
    private boolean vaapApplied;
    private LocalDate vaapDateApplication;
    private LocalTime vaapTimeApplication;
    private String vaapToken= (Integer.toString((int) System.nanoTime()) + "" +
            (Math.random() * 100) + UUID.randomUUID() +
            (Math.random() * 100) + UUID.randomUUID() +
            System.nanoTime() + "" +
            (Math.random() * 100));;

    @ManyToOne
    @JoinColumn(name = "vacc_id")
    private VaccinesEntity vaccines;

    @ManyToOne
    @JoinColumn(name = "empl_id")
    private EmployeesEntity employees;

    @ManyToOne
    @JoinColumn(name = "chil_id")
    private ChildrensEntity childrens;
}
