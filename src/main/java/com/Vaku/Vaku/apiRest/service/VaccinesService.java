package com.Vaku.Vaku.apiRest.service;

import com.Vaku.Vaku.apiRest.model.entity.VaccinesEntity;
import com.Vaku.Vaku.apiRest.model.response.EmployeesResponse;
import com.Vaku.Vaku.apiRest.model.response.VaccinesResponse;
import com.Vaku.Vaku.apiRest.repository.VaccinesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class VaccinesService {

    @Autowired
    private VaccinesRepository vaccinesRepository;

    public Set<VaccinesResponse> getVaccines(Long id) {
        return vaccinesRepository.findByVaccines(id);
    }

    public List<VaccinesEntity> findByAllVacinnes(){
        return vaccinesRepository.findAll();
    }
}
