package com.Vaku.Vaku.apiRest.repository;

import com.Vaku.Vaku.apiRest.model.entity.ChildrensEntity;
import com.Vaku.Vaku.apiRest.model.response.InfoParentsChildrensResponse;
import com.Vaku.Vaku.apiRest.model.response.VaccinationCardResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface VaccinationCardRepository extends JpaRepository<ChildrensEntity, Long> {
    @Query(value = """
            SELECT
                vp.vaap_id AS vaap_id,
                vp.vaap_next_appointment_date AS vaap_next_appointment_date,
                vp.vaap_date_application AS vaap_date_application,
                vp.empl_id AS vaap_empl_id,
                vp.vaap_token AS vaap_token, 
            
                v.vacc_id AS vVacc_id,
                v.vacc_name AS vVacc_name,
            
                pe.pers_names AS pePers_names,
                pe.pers_last_names AS pePers_last_names
            FROM childrens ch
            INNER JOIN persons p ON ch.pers_id = p.pers_id
            INNER JOIN childrens_parents cp ON ch.chil_id = cp.chil_id
            INNER JOIN parents pa ON cp.pare_id = pa.pare_id
            INNER JOIN persons ppa ON pa.pers_id = ppa.pers_id -- Person de Parents
            INNER JOIN vaccines_applied vp ON vp.chil_id = ch.chil_id
            INNER JOIN vaccines v ON v.vacc_id = vp.vacc_id
            INNER JOIN employees e ON vp.empl_id = e.empl_id
            INNER JOIN persons pe ON e.pers_id = pe.pers_id -- Person de Employees
            INNER JOIN citys c ON p.city_id = c.city_id
            INNER JOIN departments d ON c.depa_id = d.depa_id
            WHERE ch.chil_id = :childId
            ORDER BY vp.vacc_id ASC
            """, nativeQuery = true)
    Set<VaccinationCardResponse> getVaccinationCard(Long childId);

    @Query(value = """
        SELECT
            -- Información del niño
            ch.chil_id AS chil_id,
            ch.chil_token AS chil_token,
            p_child.pers_id AS child_pers_id,
            p_child.pers_names AS child_names,
            p_child.pers_last_names AS child_last_names,
            p_child.pers_document AS child_document,
            p_child.pers_sex AS child_sex,
            p_child.pers_address AS child_address,
            p_child.pers_date_birth AS child_birth_date,
            p_child.pers_role AS child_role,
            p_child.pers_phone AS child_phone,
            p_child.pers_email AS child_email,
            c.city_name AS child_city,
            d.depa_name AS child_department,
        
            -- Información del padre
            pa.pare_id AS pare_id,
            pa.pare_token AS pare_token,
            p_parent.pers_id AS parent_pers_id,
            p_parent.pers_names AS parent_names,
            p_parent.pers_last_names AS parent_last_names,
            p_parent.pers_document AS parent_document,
            p_parent.pers_sex AS parent_sex,
            p_parent.pers_address AS parent_address,
            p_parent.pers_date_birth AS parent_birth_date,
            p_parent.pers_role AS parent_role,
            p_parent.pers_phone AS parent_phone,
            p_parent.pers_email AS parent_email,
            c_parent.city_name AS parent_city,
            d_parent.depa_name AS parent_department
        
        FROM childrens ch
        INNER JOIN persons p_child ON ch.pers_id = p_child.pers_id
        LEFT JOIN citys c ON p_child.city_id = c.city_id
        LEFT JOIN departments d ON c.depa_id = d.depa_id
        
        INNER JOIN childrens_parents cp ON ch.chil_id = cp.chil_id
        INNER JOIN parents pa ON cp.pare_id = pa.pare_id
        INNER JOIN persons p_parent ON pa.pers_id = p_parent.pers_id
        LEFT JOIN citys c_parent ON p_parent.city_id = c_parent.city_id
        LEFT JOIN departments d_parent ON c_parent.depa_id = d_parent.depa_id
        
        WHERE ch.chil_id = :childId;
            """, nativeQuery = true)
    Set<InfoParentsChildrensResponse> getInfoParentsChildrens(Long childId);
}
