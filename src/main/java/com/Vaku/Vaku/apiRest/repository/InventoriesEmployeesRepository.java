package com.Vaku.Vaku.apiRest.repository;

import com.Vaku.Vaku.apiRest.model.entity.InventoriesEmployeesEntity;
import com.Vaku.Vaku.apiRest.model.entity.InventoriesEntity;
import com.Vaku.Vaku.apiRest.model.response.InventoriesResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface InventoriesEmployeesRepository extends JpaRepository<InventoriesEmployeesEntity, Long> {
}
