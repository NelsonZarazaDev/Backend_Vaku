package com.Vaku.Vaku.apiRest.model.response;

import java.time.LocalDate;

public interface InfoParentsChildrensResponse {
    // Niño
    Long getChilId();
    String getChilToken();

    Long getChildPersId();
    String getChildNames();
    String getChildLastNames();
    String getChildDocument();
    String getChildSex();
    String getChildAddress();
    LocalDate getChildBirthDate();
    String getChildRole();
    String getChildPhone();
    String getChildEmail();
    String getChildCity();
    String getChildDepartment();

    // Padre
    Long getPareId();
    String getPareToken();

    Long getParentPersId();
    String getParentNames();
    String getParentLastNames();
    String getParentDocument();
    String getParentSex();
    String getParentAddress();
    LocalDate getParentBirthDate();
    String getParentRole();
    String getParentPhone();
    String getParentEmail();
    String getParentCity();
    String getParentDepartment();
}