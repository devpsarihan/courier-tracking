package com.couriertracking.service;

import com.couriertracking.controller.v1.request.CreateCourierLocationRequest;
import com.couriertracking.event.CourierLocationCreatedEvent;
import com.couriertracking.event.CourierLocationPayload;
import com.couriertracking.event.Location;
import com.couriertracking.model.dto.CourierDto;
import com.couriertracking.model.entity.CourierLocationEntity;
import com.couriertracking.repository.CourierLocationRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourierLocationService {

    private final CourierLocationRepository courierLocationRepository;
    private final CourierService courierService;
    private final DistanceCalculationStrategy distanceCalculationStrategy;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Long create(final Long courierId, final CreateCourierLocationRequest request) {
        CourierDto courier = courierService.getById(courierId);
        Long courierLocationId = courierLocationRepository.save(CourierLocationEntity.builder()
                .courierId(courier.getId())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .travelDistance(calculateTravelDistance(courierId, request))
                .build())
            .getId();
        log.info("Courier Location is created by id:{}", courierLocationId);

        CourierLocationPayload payload = CourierLocationPayload.builder()
            .courierId(courierId)
            .latitude(request.getLatitude())
            .longitude(request.getLongitude())
            .build();
        CourierLocationCreatedEvent event = new CourierLocationCreatedEvent(this, payload);
        eventPublisher.publishEvent(event);
        log.info("Courier Location Created Event is published courierId:{}, latitude:{}, longitude:{}", courierId,
            request.getLatitude(), request.getLongitude());

        return courierLocationId;
    }

    public Double getTotalTravelDistanceByCourierId(final Long courierId) {
        return courierLocationRepository.findAllByCourierEntityId(courierId).stream()
            .mapToDouble(CourierLocationEntity::getTravelDistance)
            .sum();
    }

    private double calculateTravelDistance(final Long courierId, final CreateCourierLocationRequest request) {
        Optional<CourierLocationEntity> optionalResult = courierLocationRepository.findTopByCourierEntityIdOrderByIdDesc(
            courierId);
        return optionalResult.stream()
            .mapToDouble(location -> distanceCalculationStrategy.calculateDistance(Location.builder()
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build(),
                Location.builder()
                    .latitude(request.getLatitude())
                    .longitude(request.getLongitude())
                    .build()))
            .findFirst()
            .orElse(0.0);
    }
}
