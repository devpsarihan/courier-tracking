package com.couriertracking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.couriertracking.config.handler.exception.CourierTrackingException;
import com.couriertracking.model.dto.CourierDto;
import com.couriertracking.model.entity.CourierEntity;
import com.couriertracking.repository.CourierRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CourierServiceTest {

    @Mock
    private CourierRepository courierRepository;

    @InjectMocks
    private CourierService courierService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate_WhenGiveValidCourier_ShouldReturnCourierId() {
        CourierDto courierDto = new CourierDto("FirstName", "LastName");
        CourierEntity courierEntity = CourierEntity.builder().id(1L).firstName("FirstName").lastName("LastName")
            .build();
        when(courierRepository.save(any(CourierEntity.class))).thenReturn(courierEntity);

        Long createdId = courierService.create(courierDto);

        assertNotNull(createdId);
        assertEquals(1L, createdId);
    }

    @Test
    void testGetById_WhenGetCourier_ShouldReturnCourierDto() {
        Long existingCourierId = 1L;
        CourierEntity courierEntity = CourierEntity.builder().id(existingCourierId).firstName("FirstName")
            .lastName("LastName").build();
        when(courierRepository.findById(existingCourierId)).thenReturn(Optional.of(courierEntity));

        CourierDto courier = courierService.getById(existingCourierId);

        assertNotNull(courier);
        assertEquals(1L, courier.getId());
        assertEquals("FirstName", courier.getFirstName());
        assertEquals("LastName", courier.getLastName());
    }

    @Test
    void testGetById_NonExistingCourier_ShouldReturnException() {
        Long nonExistingCourierId = 2L;
        when(courierRepository.findById(nonExistingCourierId)).thenReturn(Optional.empty());

        assertThrows(CourierTrackingException.class, () -> courierService.getById(nonExistingCourierId));
    }
}



