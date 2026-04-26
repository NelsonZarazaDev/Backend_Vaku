package com.Vaku.Vaku.apiRest.repository;

import com.Vaku.Vaku.apiRest.model.entity.ChildrensParentsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ChildrensParentsRepository extends CrudRepository<ChildrensParentsEntity,Long> {
    boolean existsByChildrens_ChilIdAndParents_PareId(Long chilId, Long pareId);
    Optional<ChildrensParentsEntity> findByChildrens_ChilIdAndParents_PareId(Long chilId, Long pareId);
}
