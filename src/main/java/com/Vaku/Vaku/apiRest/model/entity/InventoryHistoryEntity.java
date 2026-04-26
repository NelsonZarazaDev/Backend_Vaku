package com.Vaku.Vaku.apiRest.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_history")
@Getter
@Setter
public class InventoryHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inhi_id")
    private Long id;

    @Column(name = "inhi_date")
    private LocalDateTime date;

    @Column(name = "inve_laboratory")
    private String laboratory;

    @Column(name = "inve_id")
    private Integer inventoryId;

    @Column(name = "inve_lot")
    private String lot;

    @Column(name = "inve_quantity")
    private Integer quantity;

    @Column(name = "inventory_date")
    private LocalDate inventoryDate;

    @Column(name = "vacc_id")
    private Integer vaccineId;
}