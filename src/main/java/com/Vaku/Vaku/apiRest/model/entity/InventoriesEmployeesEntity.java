package com.Vaku.Vaku.apiRest.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity(name = "inventories_employees")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoriesEmployeesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inemId;
    private LocalDate inemDate;

    @ManyToOne
    @JoinColumn(name = "empl_id")
    private EmployeesEntity employees;

    @ManyToOne
    @JoinColumn(name = "inve_id")
    private InventoriesEntity inventories;
}
