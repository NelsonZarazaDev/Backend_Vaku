package com.Vaku.Vaku.apiRest.repository;

import com.Vaku.Vaku.apiRest.model.entity.ChildrensEntity;
import com.Vaku.Vaku.apiRest.model.response.ChildrensResponse;
import com.Vaku.Vaku.apiRest.model.response.OverdueAppointmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface ChildrensRepository extends JpaRepository<ChildrensEntity,Long> {
    @Query(value = """
            SELECT ch.chil_id AS chilId, ch.chil_token AS chilToken,
                   p.pers_id AS persId, p.pers_names AS persNames,
                   p.pers_document AS persDocument,
                   p.pers_last_names AS persLastNames, p.pers_document AS persDocument,
                   p.pers_sex AS persSex, p.pers_address AS persAddress,
                   p.pers_date_birth AS persDateBirth, p.pers_role AS persRole,
                   p.pers_email AS persEmail, p.pers_phone AS persPhone,
                   c.city_name AS CityName, d.depa_name AS DepaName
            FROM childrens ch
            INNER JOIN persons p ON ch.pers_id = p.pers_id
            INNER JOIN citys c ON p.city_id = c.city_id
            INNER JOIN departments d ON c.depa_id = d.depa_id
            WHERE p.pers_id = :persId
            """, nativeQuery = true)
    Set<ChildrensResponse> findByChildrenId(@Param("persId") Long persId);

    Optional<ChildrensEntity> findByChilToken(String chilToken);

    Optional<ChildrensEntity> findByPersons_PersId(Long persId);

    @Query(value = """
            SELECT
        ch.chil_id AS chilId,
        ch.chil_token AS chilToken,
        p_child.pers_names AS childNames,
        p_child.pers_last_names AS childLastNames,
        p_child.pers_document AS childDocument,
        p_child.pers_date_birth AS childBirthDate,

        p_parent.pers_names AS parentNames,
        p_parent.pers_last_names AS parentLastNames,
        p_parent.pers_email AS parentEmail,
        p_parent.pers_phone AS parentPhone,

        va.vaap_id AS vaapId,
        va.vaap_next_appointment_date AS vaapNextAppointmentDate,
        va.vaap_applied AS vaapApplied,
        va.vaap_date_application AS vaapDateApplication,
        va.vaap_time_application AS vaapTimeApplication,
        va.vaap_token AS vaapToken

    FROM vaccines_applied va
        INNER JOIN (
            SELECT chil_id, MAX(vaap_next_appointment_date) AS last_date
            FROM vaccines_applied
            WHERE vaap_next_appointment_date IS NOT NULL
            GROUP BY chil_id
        ) latest_vaccine
        ON va.chil_id = latest_vaccine.chil_id
        AND va.vaap_next_appointment_date = latest_vaccine.last_date
        AND va.vaap_next_appointment_date <= CURRENT_DATE

        INNER JOIN childrens ch ON va.chil_id = ch.chil_id
        INNER JOIN persons p_child ON ch.pers_id = p_child.pers_id
        INNER JOIN childrens_parents cp ON ch.chil_id = cp.chil_id
        INNER JOIN parents pa ON cp.pare_id = pa.pare_id
        INNER JOIN persons p_parent ON pa.pers_id = p_parent.pers_id
""", nativeQuery = true)
    Set<OverdueAppointmentResponse> findByNextAppointmentDateBefore();

    @Query(value = """
            SELECT
        ch.chil_id AS chilId,
        ch.chil_token AS chilToken,
        p_child.pers_names AS childNames,
        p_child.pers_last_names AS childLastNames,
        p_child.pers_document AS childDocument,
        p_child.pers_date_birth AS childBirthDate,

        p_parent.pers_names AS parentNames,
        p_parent.pers_last_names AS parentLastNames,
        p_parent.pers_email AS parentEmail,
        p_parent.pers_phone AS parentPhone,

        va.vaap_id AS vaapId,
        va.vaap_next_appointment_date AS vaapNextAppointmentDate,
        va.vaap_applied AS vaapApplied,
        va.vaap_date_application AS vaapDateApplication,
        va.vaap_time_application AS vaapTimeApplication,
        va.vaap_token AS vaapToken

    FROM vaccines_applied va
        INNER JOIN (
            SELECT chil_id, MAX(vaap_next_appointment_date) AS last_date
            FROM vaccines_applied
            WHERE vaap_next_appointment_date IS NOT NULL
            GROUP BY chil_id
        ) latest_vaccine
        ON va.chil_id = latest_vaccine.chil_id
        AND va.vaap_next_appointment_date = latest_vaccine.last_date
        AND va.vaap_next_appointment_date <= CURRENT_DATE

        INNER JOIN childrens ch ON va.chil_id = ch.chil_id
        INNER JOIN persons p_child ON ch.pers_id = p_child.pers_id
        INNER JOIN childrens_parents cp ON ch.chil_id = cp.chil_id
        INNER JOIN parents pa ON cp.pare_id = pa.pare_id
        INNER JOIN persons p_parent ON pa.pers_id = p_parent.pers_id
        ORDER BY va.vaap_next_appointment_date ASC
""",
            countQuery = """
            SELECT COUNT(*)
            FROM (
                SELECT va.chil_id
                FROM vaccines_applied va
                INNER JOIN (
                    SELECT chil_id, MAX(vaap_next_appointment_date) AS last_date
                    FROM vaccines_applied
                    WHERE vaap_next_appointment_date IS NOT NULL
                    GROUP BY chil_id
                ) latest_vaccine
                ON va.chil_id = latest_vaccine.chil_id
                AND va.vaap_next_appointment_date = latest_vaccine.last_date
                AND va.vaap_next_appointment_date <= CURRENT_DATE
            ) overdue_children
            """,
            nativeQuery = true)
    Page<OverdueAppointmentResponse> findByNextAppointmentDateBefore(Pageable pageable);
}
