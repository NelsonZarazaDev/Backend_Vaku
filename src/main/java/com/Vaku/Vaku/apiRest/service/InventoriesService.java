package com.Vaku.Vaku.apiRest.service;

import com.Vaku.Vaku.apiRest.model.entity.InventoriesEntity;
import com.Vaku.Vaku.apiRest.model.response.InventoriesResponse;
import com.Vaku.Vaku.apiRest.repository.InventoriesRepository;
import com.Vaku.Vaku.exception.NotFoundException;
import com.Vaku.Vaku.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
public class InventoriesService {

    @Autowired
    private InventoriesRepository inventoriesRepository;

    @Autowired
    private InventoriesEmployeesService inventoriesEmployeesService;

    public InventoriesEntity updateInventarie(InventoriesEntity inventoriesRequest, String token, Long emplId) {
        InventoriesEntity inventoriBd = inventoriesRepository.findByinveToken(token)
                .orElseThrow(() -> new NotFoundException(Constants.INVENTORIE_NOT_EXISTS.getMessage()));

        inventoriesEmployeesService.createInventarie(emplId, inventoriBd.getInveId());

        inventoriBd.setInveLaboratory(inventoriesRequest.getInveLaboratory());
        inventoriBd.setInveLot(inventoriesRequest.getInveLot());
        inventoriBd.setInveQuantity(inventoriesRequest.getInveQuantity());
        inventoriBd.setInveDate(LocalDate.now());

        return inventoriesRepository.save(inventoriBd);
    }

    public Set<InventoriesResponse> getInventoriByToken(String token) {
        Optional<InventoriesEntity> inventori = inventoriesRepository.findByinveToken(token);
        if (!inventori.isPresent()) {
            throw new NotFoundException(Constants.INVENTORIE_NOT_EXISTS.getMessage());
        }
        return inventoriesRepository.getInventoriByToken(inventori.get().getInveId());
    }

    public Set<InventoriesResponse> getInventoriAll() {
        return inventoriesRepository.getInventoriAll();
    }

}
