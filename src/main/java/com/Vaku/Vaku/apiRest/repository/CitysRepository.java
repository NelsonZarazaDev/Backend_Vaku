package com.Vaku.Vaku.apiRest.repository;

import com.Vaku.Vaku.apiRest.model.entity.CitysEntity;
import com.Vaku.Vaku.apiRest.model.response.CitysResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface CitysRepository extends JpaRepository<CitysEntity,Long> {
    @Query(value = """
            SELECT c.city_id AS cityId, c.city_name AS cityName, c.depa_id AS DepaId, c.city_code AS cityCode
            FROM citys c
                 INNER JOIN departments d ON d.depa_id = c.depa_id
            WHERE c.depa_id= :depaId
            """, nativeQuery = true)
    Set<CitysResponse> findByCityDepaId(String depaId);
}