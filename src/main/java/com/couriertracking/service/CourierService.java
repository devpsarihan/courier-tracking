package com.couriertracking.service;

import com.couriertracking.config.handler.exception.CourierTrackingException;
import com.couriertracking.config.handler.exception.ErrorCode;
import com.couriertracking.model.dto.CourierDto;
import com.couriertracking.model.entity.CourierEntity;
import com.couriertracking.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourierService {

    private final CourierRepository courierRepository;

    public Long create(final CourierDto courier) {
        return courierRepository.save(CourierEntity.builder()
            .firstName(courier.getFirstName())
            .lastName(courier.getLastName())
            .build()).getId();
    }

    public CourierDto getById(final Long id) {
        return courierRepository.findById(id)
            .orElseThrow(() -> new CourierTrackingException(ErrorCode.COURIER_NOT_FOUND_ERROR)).toModel();
    }
}
