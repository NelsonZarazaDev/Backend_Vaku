package com.Vaku.Vaku.apiRest.repository;

import com.Vaku.Vaku.apiRest.model.entity.ChildrensParentsEntity;
import com.Vaku.Vaku.apiRest.model.entity.VaccinesAppliedEntity;
import org.springframework.data.repository.CrudRepository;

public interface VaccinesAppliedRepository extends CrudRepository<VaccinesAppliedEntity,Long> {
}
