package com.Vaku.Vaku.apiRest.service;

import com.Vaku.Vaku.apiRest.model.entity.DepartmentsEntity;
import com.Vaku.Vaku.apiRest.repository.DepartmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentsService {

    @Autowired
    private DepartmentsRepository departmentsRepository;

    public List<DepartmentsEntity> findAllDepartments(){
        return departmentsRepository.findAll();
    }
}
