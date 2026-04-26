package com.Vaku.Vaku.apiRest.repository;

import com.Vaku.Vaku.apiRest.model.entity.ChildrensEntity;
import com.Vaku.Vaku.apiRest.model.entity.InventoriesEntity;
import com.Vaku.Vaku.apiRest.model.response.ChildrensResponse;
import com.Vaku.Vaku.apiRest.model.response.InventoriesResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface InventoriesRepository extends JpaRepository<InventoriesEntity, Long> {
    @Query(value = """
            SELECT i.*, v.*
            FROM inventories i
                            INNER JOIN vaccines v ON  v.inve_id = i.inve_id
            WHERE i.inve_id=:inveId
            LIMIT 1
            """, nativeQuery = true)
    Set<InventoriesResponse> getInventoriByToken(Long inveId);

    Optional<InventoriesEntity> findByinveToken(String inveToken);

    @Query(value = """
            SELECT v.vacc_name, i.*
            FROM inventories i
                            INNER JOIN vaccines v ON i.inve_id = v.inve_id
            GROUP BY v.vacc_name, i.inve_id;
            """, nativeQuery = true)
    Set<InventoriesResponse> getInventoriAll();
}
