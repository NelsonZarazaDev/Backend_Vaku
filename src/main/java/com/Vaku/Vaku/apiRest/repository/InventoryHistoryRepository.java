package com.Vaku.Vaku.apiRest.repository;

import com.Vaku.Vaku.apiRest.model.entity.InventoryHistoryEntity;
import com.Vaku.Vaku.apiRest.model.response.InventoriesResponse;
import com.Vaku.Vaku.apiRest.model.response.InventoryHistoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryHistoryRepository extends JpaRepository<InventoryHistoryEntity, Long> {
    @Query(value = """
        SELECT 
            ih.inhi_id AS inhiId,
            ih.inhi_date AS inhiDate,
            ih.inve_quantity AS inveQuantity,
            ih.inve_lot AS inveLot,
            ih.inve_laboratory AS inveLaboratory,
            ih.inventory_date AS inventoryDate,
            v.vacc_id AS vaccId,
            v.vacc_name AS vaccName,
            v.vacc_age_dose AS vaccAgeDose,
            v.vacc_dosage AS vaccDosage
        FROM inventory_history ih
        JOIN vaccines v ON ih.vacc_id = v.vacc_id
        """, nativeQuery = true)
    List<InventoryHistoryResponse> getVaccineHistoryRaw();
}