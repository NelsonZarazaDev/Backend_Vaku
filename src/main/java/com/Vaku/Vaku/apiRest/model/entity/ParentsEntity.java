package com.Vaku.Vaku.apiRest.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity(name = "parents")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pareId;
    private String pareToken= (Integer.toString((int) System.nanoTime()) + "" +
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
    @OneToMany(mappedBy = "parents", fetch = FetchType.LAZY)
    private Set<ChildrensParentsEntity> childrens_parents;
}
