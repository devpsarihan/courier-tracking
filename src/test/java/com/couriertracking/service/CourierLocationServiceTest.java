package com.couriertracking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.couriertracking.config.handler.exception.CourierTrackingException;
import com.couriertracking.config.handler.exception.ErrorCode;
import com.couriertracking.controller.v1.request.CreateCourierLocationRequest;
import com.couriertracking.event.CourierLocationCreatedEvent;
import com.couriertracking.event.Location;
import com.couriertracking.model.dto.CourierDto;
import com.couriertracking.model.entity.CourierLocationEntity;
import com.couriertracking.repository.CourierLocationRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

class CourierLocationServiceTest {

    @Mock
    private CourierLocationRepository courierLocationRepository;

    @Mock
    private CourierService courierService;

    @Mock
    private DistanceCalculationStrategy distanceCalculationStrategy;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private CourierLocationService courierLocationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate_WhenGivenCourierLocation_ShouldReturnCourierLocationId() {
        Long courierId = 1L;
        CourierDto courier = CourierDto.builder().firstName("FirstName").lastName("LastName").build();
        CreateCourierLocationRequest request = CreateCourierLocationRequest.builder().latitude(40.9923307).longitude(29.1244229).build();
        CourierLocationEntity courierLocationEntity = CourierLocationEntity.builder()
            .id(1L)
            .courierId(courierId)
            .latitude(request.getLatitude())
            .longitude(request.getLongitude())
            .travelDistance(100.0)
            .build();
        when(courierService.getById(courierId)).thenReturn(courier);
        when(courierLocationRepository.save(any(CourierLocationEntity.class))).thenReturn(courierLocationEntity);
        when(distanceCalculationStrategy.calculateDistance(any(Location.class), any(Location.class))).thenReturn(100.0);

        Long locationId = courierLocationService.create(courierId, request);

        assertNotNull(locationId);
        assertEquals(1L, locationId);

        verify(eventPublisher, times(1)).publishEvent(any(CourierLocationCreatedEvent.class));
    }

    @Test
    void testGetTotalTravelDistanceByCourierId_WithNonExistCourier_ShouldReturnTotalTravelDistance() {
        Long courierId = 1L;
        CreateCourierLocationRequest request = new CreateCourierLocationRequest(40.9923307, 29.1244229);
        when(courierService.getById(courierId)).thenThrow(new CourierTrackingException(ErrorCode.COURIER_NOT_FOUND_ERROR));

        assertThrows(CourierTrackingException.class, () -> courierLocationService.create(courierId, request));
        verify(courierService, times(1)).getById(courierId);
        verifyNoInteractions(courierLocationRepository, distanceCalculationStrategy, eventPublisher);
    }

    @Test
    void testGetTotalTravelDistanceByCourierId_WhenGivenCourierId_ShouldReturnTotalTravelDistance() {
        Long courierId = 1L;
        CourierLocationEntity location1 = CourierLocationEntity.builder().id(1L).courierId(courierId).latitude(40.9923307).longitude(29.1244229).travelDistance(100.0).build();
        CourierLocationEntity location2 = CourierLocationEntity.builder().id(2L).courierId(courierId).latitude(40.986106).longitude(29.1161293).travelDistance(100.0).build();
        List<CourierLocationEntity> locations = Arrays.asList(location1, location2);
        when(courierLocationRepository.findAllByCourierEntityId(courierId)).thenReturn(locations);

        Double totalTravelDistance = courierLocationService.getTotalTravelDistanceByCourierId(courierId);

        assertEquals(200.0, totalTravelDistance);
    }

    @Test
    void testGetTotalTravelDistanceByCourierId_WhenGivenCourierIdAndWithoutLocation_ShouldReturnTotalTravelDistance() {
        Long courierId = 1L;
        when(courierLocationRepository.findAllByCourierEntityId(courierId)).thenReturn(Collections.emptyList());

        Double totalTravelDistance = courierLocationService.getTotalTravelDistanceByCourierId(courierId);

        assertEquals(0.0, totalTravelDistance);
    }
}

