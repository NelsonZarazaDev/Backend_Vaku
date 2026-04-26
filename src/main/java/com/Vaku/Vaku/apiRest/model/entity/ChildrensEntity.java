package com.Vaku.Vaku.apiRest.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity(name = "childrens")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChildrensEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chilId;
    private String chilToken= (Integer.toString((int) System.nanoTime()) + "" +
            (Math.random() * 100) + UUID.randomUUID() +
            (Math.random() * 100) + UUID.randomUUID() +
            System.nanoTime() + "" +
            (Math.random() * 100));;

    @ManyToOne
    @JoinColumn(name = "pers_id")
    private PersonsEntity persons;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "childrens", fetch = FetchType.LAZY)
    private Set<VaccinesAppliedEntity> vaccines_applied;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "childrens", fetch = FetchType.LAZY)
    private Set<ChildrensParentsEntity> childrens_parents;
}
