package com.Vaku.Vaku.apiRest.service;

import com.Vaku.Vaku.apiRest.model.entity.ChildrensEntity;
import com.Vaku.Vaku.apiRest.model.entity.PersonsEntity;
import com.Vaku.Vaku.apiRest.model.response.InfoParentsChildrensResponse;
import com.Vaku.Vaku.apiRest.model.response.VaccinationCardResponse;
import com.Vaku.Vaku.apiRest.repository.ChildrensRepository;
import com.Vaku.Vaku.apiRest.repository.PersonsRepository;
import com.Vaku.Vaku.apiRest.repository.VaccinationCardRepository;
import com.Vaku.Vaku.exception.AlreadyExistsException;
import com.Vaku.Vaku.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.Set;

@Service
public class VaccinationCardService {

    @Autowired
    private VaccinationCardRepository vaccinationCardRepository;

    @Autowired
    private PersonsRepository personsRepository;

    @Autowired
    private ChildrensRepository childrensRepository;

    public Set<VaccinationCardResponse> getVaccinationCard(String document) {
        Optional<PersonsEntity> personsDataBd = personsRepository.findByPersDocument(document);

        if (personsDataBd.isEmpty()) {
            throw new AlreadyExistsException(Constants.CHILD_NOT_EXISTS.getMessage());
        }

        PersonsEntity person = personsDataBd.get();

        if (!person.getPersRole().equals("Niño")) {
            throw new AlreadyExistsException(Constants.CHILD_NOT_EXISTS.getMessage());
        }

        Optional<ChildrensEntity> childrenDataBd = childrensRepository.findByPersons_PersId(personsDataBd.get().getPersId());

        if (childrenDataBd.isEmpty()) {
            throw new AlreadyExistsException(Constants.CHILD_NOT_EXISTS.getMessage());
        }
        return vaccinationCardRepository.getVaccinationCard(childrenDataBd.get().getChilId());
    }

    public Set<InfoParentsChildrensResponse> getInfoParentsChildrens(String document) {
        Optional<PersonsEntity> personsDataBd = personsRepository.findByPersDocument(document);
        Optional<ChildrensEntity> childrenDataBd = childrensRepository.findByPersons_PersId(personsDataBd.get().getPersId());
        return vaccinationCardRepository.getInfoParentsChildrens(childrenDataBd.get().getChilId());
    }

}
