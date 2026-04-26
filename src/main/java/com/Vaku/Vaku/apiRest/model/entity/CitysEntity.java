package com.Vaku.Vaku.apiRest.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity(name = "citys")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CitysEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;
    private String cityCode;
    private String cityName;

    @ManyToOne
    @JoinColumn(name = "depa_id")
    private DepartmentsEntity departments;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "citys", fetch = FetchType.LAZY)
    private Set<PersonsEntity> persons;
}
