package com.couriertracking.service;

import com.couriertracking.event.CourierLocationCreatedEvent;
import com.couriertracking.event.CourierLocationPayload;
import com.couriertracking.event.Location;
import com.couriertracking.model.dto.CourierDto;
import com.couriertracking.model.dto.StoreDto;
import com.couriertracking.model.entity.TrackingEntity;
import com.couriertracking.repository.TrackingRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackingService {

    private final TrackingRepository trackingRepository;
    private final CourierService courierService;
    private final StoreService storeService;
    private final DistanceCalculationStrategy distanceCalculationStrategy;

    @Value("${courier-tracking.duration-of-seconds}")
    private long courierTrackingDurationOfSeconds;
    @Value("${courier-tracking.distance}")
    private double courierTrackingDistance;

    @EventListener
    public void handleCourierLocationCreatedEvent(final CourierLocationCreatedEvent event) {
        CourierLocationPayload payload = event.getPayload();
        CourierDto courier = courierService.getById(payload.getCourierId());
        storeService.getAll().stream()
            .filter(store -> isNearStore(store, payload))
            .forEach(store -> update(courier.getId(), store.getId()));
    }

    private boolean isNearStore(StoreDto store, CourierLocationPayload payload) {
        return distanceCalculationStrategy.calculateDistance(Location.builder().latitude(
            payload.getLatitude()).longitude(payload.getLongitude()).build(), Location.builder().latitude(
            store.getLatitude()).longitude(store.getLongitude()).build()) <= courierTrackingDistance;
    }

    public void update(final Long courierId, final Long storeId) {
        Optional<TrackingEntity> optionalResult = trackingRepository.findTopByCourierEntityIdOrderByIdDesc(courierId);
        if (optionalResult.isPresent() &&
            courierTrackingDurationOfSeconds >= Duration.between(optionalResult.get().getCreateDate(),
                LocalDateTime.now()).getSeconds()) {
            log.info("Courier already entered by couriedId:{}", courierId);
            return;
        }
        trackingRepository.save(TrackingEntity.builder()
            .courierId(courierId)
            .storeId(storeId)
            .build());
        log.info("Tracking is created by courierId:{}, storeId:{}", courierId, storeId);
    }
}
