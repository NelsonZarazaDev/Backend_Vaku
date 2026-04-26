package com.Vaku.Vaku.apiRest.service;

import com.Vaku.Vaku.apiRest.model.response.OverdueAppointmentResponse;
import com.Vaku.Vaku.apiRest.repository.ChildrensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OverdueVaccinationsService {
    @Autowired
    private ChildrensRepository childrensRepository;

    public Set<OverdueAppointmentResponse> findByNextAppointmentDateBefore() {
        return childrensRepository.findByNextAppointmentDateBefore();
    }

    public Page<OverdueAppointmentResponse> findByNextAppointmentDateBefore(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return childrensRepository.findByNextAppointmentDateBefore(pageable);
    }
}
