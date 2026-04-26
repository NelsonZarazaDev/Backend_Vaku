package com.Vaku.Vaku.apiRest.service;

import com.Vaku.Vaku.apiRest.model.entity.ChildrensEntity;
import com.Vaku.Vaku.apiRest.model.entity.ChildrensParentsEntity;
import com.Vaku.Vaku.apiRest.model.entity.ParentsEntity;
import com.Vaku.Vaku.apiRest.model.entity.PersonsEntity;
import com.Vaku.Vaku.apiRest.repository.ChildrensParentsRepository;
import com.Vaku.Vaku.apiRest.repository.ChildrensRepository;
import com.Vaku.Vaku.apiRest.repository.ParentsRepository;
import com.Vaku.Vaku.apiRest.repository.PersonsRepository;
import com.Vaku.Vaku.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChildrensParentsService {
    private final ChildrensParentsRepository childrensParentsRepository;
    private final ChildrensRepository childrensRepository;
    private final PersonsRepository personsRepository;
    private final ParentsRepository parentsRepository;

    public ChildrensEntity createChildren(Long personChildren) {
        PersonsEntity person = personsRepository.findById(personChildren)
                .orElseThrow(() -> new NotFoundException("Persona no encontrada"));

        var existingChild = childrensRepository.findByPersons_PersId(personChildren);
        if (existingChild.isPresent()) {
            return existingChild.get();
        }

        ChildrensEntity children = new ChildrensEntity();
        children.setPersons(person);
        return childrensRepository.save(children);
    }

    public ParentsEntity createParent(Long personParent) {
        PersonsEntity person = personsRepository.findById(personParent)
                .orElseThrow(() -> new NotFoundException("Persona no encontrada"));

        var existingParent = parentsRepository.findByPersons_PersId(personParent);
        if (existingParent.isPresent()) {
            return existingParent.get();
        }

        ParentsEntity parent = new ParentsEntity();
        parent.setPersons(person);
        return parentsRepository.save(parent);
    }

    public ChildrensParentsEntity createChildrenParentChildren(Long childId, Long parentId) {
        ChildrensEntity child = childrensRepository.findById(childId)
                .orElseThrow(() -> new NotFoundException("El nino no existe"));
        ParentsEntity parent = parentsRepository.findById(parentId)
                .orElseThrow(() -> new NotFoundException("El padre de familia no existe"));

        boolean relationExists = childrensParentsRepository
                .existsByChildrens_ChilIdAndParents_PareId(childId, parentId);

        if (relationExists) {
            return childrensParentsRepository.findByChildrens_ChilIdAndParents_PareId(childId, parentId)
                    .orElseThrow(() -> new NotFoundException("Relacion entre padre e hijo no encontrada"));
        }

        ChildrensParentsEntity childrensParents = new ChildrensParentsEntity();
        childrensParents.setChildrens(child);
        childrensParents.setParents(parent);
        return childrensParentsRepository.save(childrensParents);
    }
}
