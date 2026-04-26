package com.Vaku.Vaku.apiRest.service;

import com.Vaku.Vaku.apiRest.model.entity.ChildrensEntity;
import com.Vaku.Vaku.apiRest.model.entity.PersonsEntity;
import com.Vaku.Vaku.apiRest.repository.ChildrensRepository;
import com.Vaku.Vaku.apiRest.repository.PersonsRepository;
import com.Vaku.Vaku.exception.NotFoundException;
import com.Vaku.Vaku.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChildrensService {

    private final ChildrensRepository childrensRepository;
    private final PersonsRepository personsRepository;

    public PersonsEntity updateChildren(PersonsEntity personRequest, String token) {
        ChildrensEntity children = childrensRepository.findByChilToken(token)
                .orElseThrow(() -> new NotFoundException(Constants.CHILD_NOT_EXISTS.getMessage()));

        PersonsEntity person = personsRepository.findById(children.getPersons().getPersId())
                .orElseThrow(() -> new NotFoundException("Persona no encontrada"));

        person.setPersNames(personRequest.getPersNames());
        person.setPersLastNames(personRequest.getPersLastNames());
        person.setPersDocument(personRequest.getPersDocument());

        return personsRepository.save(person);
    }
}
