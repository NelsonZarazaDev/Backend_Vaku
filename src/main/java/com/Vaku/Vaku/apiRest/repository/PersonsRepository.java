package com.Vaku.Vaku.apiRest.repository;

import com.Vaku.Vaku.apiRest.model.entity.PersonsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonsRepository extends JpaRepository<PersonsEntity,Long> {
    Optional<PersonsEntity> findByPersEmail(String persEmail);
    Optional<PersonsEntity> findByPersEmailIgnoreCase(String persEmail);
    Optional<PersonsEntity> findByPersPhone(String persPhone);
    Optional<PersonsEntity> findByPersDocument(String persDocument);
}
