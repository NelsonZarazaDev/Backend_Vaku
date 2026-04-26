package com.Vaku.Vaku.apiRest.model.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface InventoryHistoryResponse {
    Long getInhiId();
    LocalDateTime getInhiDate();
    Integer getInveQuantity();
    String getInveLot();
    String getInveLaboratory();
    LocalDate getInventoryDate();
    Integer getVaccId();
    String getVaccName();
    Integer getVaccAgeDose();
    String getVaccDosage();
}