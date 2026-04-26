package com.Vaku.Vaku.apiRest.service;

import com.Vaku.Vaku.apiRest.model.entity.EmployeesEntity;
import com.Vaku.Vaku.apiRest.model.entity.InventoriesEmployeesEntity;
import com.Vaku.Vaku.apiRest.model.entity.InventoriesEntity;
import com.Vaku.Vaku.apiRest.repository.EmployeesRepository;
import com.Vaku.Vaku.apiRest.repository.InventoriesEmployeesRepository;
import com.Vaku.Vaku.apiRest.repository.InventoriesRepository;
import com.Vaku.Vaku.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class InventoriesEmployeesService {

    private final InventoriesEmployeesRepository inventoriesEmployeesRepository;
    private final EmployeesRepository employeesRepository;
    private final InventoriesRepository inventoriesRepository;

    public InventoriesEmployeesEntity createInventarie(Long emplId, Long inveId) {
        InventoriesEmployeesEntity inventoriesEmployees = new InventoriesEmployeesEntity();

        EmployeesEntity employees = employeesRepository.findById(emplId)
                .orElseThrow(() -> new NotFoundException("Empleado no encontrado"));

        InventoriesEntity inventories = inventoriesRepository.findById(inveId)
                .orElseThrow(() -> new NotFoundException("Inventario no encontrado"));

        inventoriesEmployees.setEmployees(employees);
        inventoriesEmployees.setInventories(inventories);
        inventoriesEmployees.setInemDate(LocalDate.now());

        return inventoriesEmployeesRepository.save(inventoriesEmployees);
    }
}
