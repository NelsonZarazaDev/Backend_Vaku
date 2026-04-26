package com.Vaku.Vaku.apiRest.repository;

import com.Vaku.Vaku.apiRest.model.entity.ChildrensEntity;
import com.Vaku.Vaku.apiRest.model.entity.ParentsEntity;
import com.Vaku.Vaku.apiRest.model.entity.PersonsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ParentsRepository extends JpaRepository<ParentsEntity,Long> {
    Optional<ParentsEntity> findByPersons_PersId(Long persId);

}
