package com.Vaku.Vaku.apiRest.model.response;

import java.time.LocalDate;


public interface ChildrensResponse {
    Long getChilId();
    String getChilToken();
    Long getPersId();
    String getPersNames();
    String getPersLastNames();
    String getPersDocument();
    String getPersSex();
    String getPersAddress();
    LocalDate getPersDateBirth();
    String getPersRole();
    String getPersEmail();
    String getPersPhone();
    String getCityName();
    String getDepaName();
}
