package com.Vaku.Vaku.apiRest.service;

import com.Vaku.Vaku.apiRest.model.entity.ChildrensEntity;
import com.Vaku.Vaku.apiRest.model.entity.PersonsEntity;
import com.Vaku.Vaku.apiRest.model.response.InfoParentsChildrensResponse;
import com.Vaku.Vaku.apiRest.model.response.VaccinationCardResponse;
import com.Vaku.Vaku.apiRest.repository.ChildrensRepository;
import com.Vaku.Vaku.apiRest.repository.PersonsRepository;
import com.Vaku.Vaku.apiRest.repository.VaccinationCardRepository;
import com.Vaku.Vaku.exception.NotFoundException;
import com.Vaku.Vaku.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        ChildrensEntity childrenDataBd = findChildByDocument(document);
        return vaccinationCardRepository.getVaccinationCard(childrenDataBd.getChilId());
    }

    public Set<VaccinationCardResponse> getVaccinationCardByChildId(Long childId) {
        childrensRepository.findById(childId)
                .orElseThrow(() -> new NotFoundException(Constants.CHILD_NOT_EXISTS.getMessage()));
        return vaccinationCardRepository.getVaccinationCard(childId);
    }

    public Set<InfoParentsChildrensResponse> getInfoParentsChildrens(String document) {
        ChildrensEntity childrenDataBd = findChildByDocument(document);
        return vaccinationCardRepository.getInfoParentsChildrens(childrenDataBd.getChilId());
    }

    private ChildrensEntity findChildByDocument(String document) {
        PersonsEntity personsDataBd = personsRepository.findByPersDocument(document.trim())
                .orElseThrow(() -> new NotFoundException(Constants.CHILD_NOT_EXISTS.getMessage()));

        return childrensRepository.findByPersons_PersId(personsDataBd.getPersId())
                .orElseThrow(() -> new NotFoundException(Constants.CHILD_NOT_EXISTS.getMessage()));
    }
}
