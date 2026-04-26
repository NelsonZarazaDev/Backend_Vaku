package com.Vaku.Vaku.apiRest.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "childrens_parents")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChildrensParentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chpa_id;

    @ManyToOne
    @JoinColumn(name = "chil_id")
    private ChildrensEntity childrens;

    @ManyToOne
    @JoinColumn(name = "pare_id")
    private ParentsEntity parents;
}
