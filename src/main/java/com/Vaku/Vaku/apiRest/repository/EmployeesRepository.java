package com.Vaku.Vaku.apiRest.repository;

import com.Vaku.Vaku.apiRest.model.entity.EmployeesEntity;
import com.Vaku.Vaku.apiRest.model.entity.PersonsEntity;
import com.Vaku.Vaku.apiRest.model.response.EmployeesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface EmployeesRepository extends JpaRepository<EmployeesEntity, Long> {
    Optional<EmployeesEntity> findByEmplToken(String emplToken);

    @Query(value = """
            SELECT e.empl_date_admission AS emplDateAdmission,e.empl_state AS emplState,e.empl_token AS emplToken,
                   e.empl_id AS emplId,p.pers_id AS persId, p.pers_names AS persNames,
                   p.pers_last_names AS persLastNames, p.pers_document AS persDocument,
                   p.pers_sex AS persSex, p.pers_address AS persAddress,
                   p.pers_date_birth AS persDateBirth, p.pers_role AS persRole,
                   p.pers_email AS persEmail, p.pers_phone AS persPhone,
                       c.city_name AS CityName, d.depa_name AS DepaName
            FROM employees e
            INNER JOIN persons p ON e.pers_id = p.pers_id
            INNER JOIN citys c ON p.city_id = c.city_id
            INNER JOIN departments d ON c.depa_id = d.depa_id
            WHERE p.pers_email = :email
            """, nativeQuery = true)
    Set<EmployeesResponse> findByJsonEmployeeEmail(String email);

    @Query(value = """
            SELECT e.empl_token AS emplToken,e.empl_state AS emplState,
                               p.pers_names AS persNames,e.empl_id AS emplId,
                               p.pers_last_names AS persLastNames, p.pers_role AS persRole,
                            p.pers_email AS PersEmail
            FROM employees e
                            INNER JOIN persons p ON e.pers_id = p.pers_id
                            INNER JOIN citys c ON p.city_id = c.city_id
                            INNER JOIN departments d ON c.depa_id = d.depa_id
            WHERE p.pers_role='Jefe de enfermeriÌa' OR p.pers_role='Enfermera'
            """, nativeQuery = true)
    Set<EmployeesResponse> findByAllEmployee();

    @Query(value = """
            SELECT e.empl_token AS emplToken,e.empl_state AS emplState,
                               p.pers_names AS persNames,e.empl_id AS emplId,
                               p.pers_last_names AS persLastNames, p.pers_role AS persRole,
                            p.pers_email AS PersEmail
            FROM employees e
                            INNER JOIN persons p ON e.pers_id = p.pers_id
                            INNER JOIN citys c ON p.city_id = c.city_id
                            INNER JOIN departments d ON c.depa_id = d.depa_id
            WHERE p.pers_role='Jefe de enfermeriÌa' OR p.pers_role='Enfermera'
            """,
            countQuery = """
            SELECT COUNT(*)
            FROM employees e
                            INNER JOIN persons p ON e.pers_id = p.pers_id
                            INNER JOIN citys c ON p.city_id = c.city_id
                            INNER JOIN departments d ON c.depa_id = d.depa_id
            WHERE p.pers_role='Jefe de enfermeriÌa' OR p.pers_role='Enfermera'
            """,
            nativeQuery = true)
    Page<EmployeesResponse> findByAllEmployee(Pageable pageable);

    Optional<EmployeesEntity> findByPersons(PersonsEntity persons);

    Optional<EmployeesEntity> findByPersons_PersId(Long persId);
}
