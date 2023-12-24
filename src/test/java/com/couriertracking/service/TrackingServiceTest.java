package com.couriertracking.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.couriertracking.event.CourierLocationCreatedEvent;
import com.couriertracking.event.CourierLocationPayload;
import com.couriertracking.event.Location;
import com.couriertracking.model.dto.CourierDto;
import com.couriertracking.model.dto.StoreDto;
import com.couriertracking.model.entity.TrackingEntity;
import com.couriertracking.repository.TrackingRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
class TrackingServiceTest {

    @Mock
    private TrackingRepository trackingRepository;

    @Mock
    private CourierService courierService;

    @Mock
    private StoreService storeService;

    @Mock
    private TravelDistanceCalculation travelDistanceCalculation;

    @InjectMocks
    private TrackingService trackingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(travelDistanceCalculation, "earthRadius", 6371.0);
        ReflectionTestUtils.setField(trackingService, "courierTrackingDurationOfSeconds", 60);
        ReflectionTestUtils.setField(trackingService, "courierTrackingDistance", 0.1);
    }

    @Test
    void testHandleLocationCreatedEvent_WithNearStore_ShouldSuccess() {
        Long courierId = 1L;
        Long storeId = 1L;
        CourierLocationPayload payload = new CourierLocationPayload(courierId, 40.0, -75.0);

        CourierDto courierDto = new CourierDto();
        courierDto.setId(courierId);

        StoreDto storeDto = new StoreDto();
        storeDto.setId(storeId);
        storeDto.setLatitude(40.0);
        storeDto.setLongitude(-75.0);

        when(courierService.getById(courierId)).thenReturn(courierDto);
        when(storeService.getAll()).thenReturn(Collections.singletonList(storeDto));
        when(travelDistanceCalculation.calculateDistance(any(Location.class), any(Location.class))).thenReturn(0.0);

        trackingService.handleCourierLocationCreatedEvent(new CourierLocationCreatedEvent(this, payload));

        verify(storeService, times(1)).getAll();
        verify(trackingRepository, times(1)).save(any(TrackingEntity.class));
    }

    @Test
    void testHandleLocationCreatedEvent_WithNotNearStores_ShouldSuccess() {
        Long courierId = 1L;
        CourierLocationPayload payload = new CourierLocationPayload(courierId, 40.0, -75.0);

        CourierDto courierDto = new CourierDto();
        courierDto.setId(courierId);

        when(courierService.getById(courierId)).thenReturn(courierDto);
        when(storeService.getAll()).thenReturn(Collections.emptyList());

        trackingService.handleCourierLocationCreatedEvent(new CourierLocationCreatedEvent(this, payload));

        verify(storeService, times(1)).getAll();
        verify(trackingRepository, never()).save(any(TrackingEntity.class));
    }

    @Test
    void testUpdateTracking_WithTimeDuration_ShouldSuccess() {
        Long courierId = 1L;
        Long storeId = 1L;
        TrackingEntity trackingEntity = TrackingEntity.builder()
            .courierId(courierId)
            .storeId(storeId)
            .createDate(LocalDateTime.now().minus(Duration.ofSeconds(30)))
            .build();
        when(trackingRepository.findTopByCourierEntityIdOrderByIdDesc(courierId)).thenReturn(
            Optional.of(trackingEntity));

        trackingService.update(courierId, storeId);

        verify(trackingRepository, never()).save(any(TrackingEntity.class));
    }

    @Test
    void testUpdateTracking_WithOutsideTimeDuration_ShouldSuccess() {
        Long courierId = 1L;
        Long storeId = 1L;
        TrackingEntity trackingEntity = TrackingEntity.builder()
            .id(1L)
            .courierId(courierId)
            .storeId(storeId)
            .createDate(LocalDateTime.now().minus(Duration.ofSeconds(3600)))
            .build();
        when(trackingRepository.findTopByCourierEntityIdOrderByIdDesc(courierId))
            .thenReturn(Optional.of(trackingEntity));
        when(trackingRepository.save(any(TrackingEntity.class)))
            .thenReturn(new TrackingEntity());

        trackingService.update(courierId, storeId);

        verify(trackingRepository, atLeastOnce()).save(any(TrackingEntity.class));
    }
}

