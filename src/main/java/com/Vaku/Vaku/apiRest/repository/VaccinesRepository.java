package com.Vaku.Vaku.apiRest.repository;

import com.Vaku.Vaku.apiRest.model.entity.VaccinesEntity;
import com.Vaku.Vaku.apiRest.model.response.VaccinesResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface VaccinesRepository extends JpaRepository<VaccinesEntity,Long> {
    @Query(value = """
            SELECT v.vacc_name AS vaccName,v.vacc_age_dose AS vaccAgeDose,v.vacc_dosage AS vaccDosage,
                               i.inve_laboratory AS inveLaboratory,i.inve_lot AS inveLot,i.inve_quantity AS inveQuantity
            FROM vaccines v
            INNER JOIN inventories i ON v.inve_id = i.inve_id
            WHERE v.vacc_id = :vaccId
            """, nativeQuery = true)
    Set<VaccinesResponse> findByVaccines(Long vaccId);


    @Query(value = """
            SELECT
                v.vacc_name AS vaccName,
                v.vacc_age_dose AS vaccAgeDose,
                v.vacc_dosage AS vaccDosage,
                i.inve_laboratory AS inveLaboratory,
                i.inve_lot AS inveLot,
                i.inve_quantity AS inveQuantity,
                vaap.vaap_date_application AS vaapDateApplication,
                vaap.vaap_next_appointment_date AS vaapNextAppointmentDate
            FROM vaccines v
                     INNER JOIN inventories i ON v.inve_id = i.inve_id
                     LEFT JOIN vaccines_applied vaap ON v.vacc_id = vaap.vacc_id AND vaap.chil_id = :childId""", nativeQuery = true)
    List<VaccinesResponse> findVaccinesByChildId(@Param("childId") Long childId);
}
