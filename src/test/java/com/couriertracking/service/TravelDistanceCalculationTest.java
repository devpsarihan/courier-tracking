package com.couriertracking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.couriertracking.event.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
class TravelDistanceCalculationTest {

    @InjectMocks
    private TravelDistanceCalculation travelDistanceCalculation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(travelDistanceCalculation, "earthRadius", 6371.0);
    }

    @Test
    void testCalculateDistance() {
        Location lastLocation = new Location(40.9923307, 29.1244229);
        Location currentLocation = new Location(40.986106, 29.1161293);

        double distance = travelDistanceCalculation.calculateDistance(lastLocation, currentLocation);

        assertEquals(0.9816568435212005, distance);
    }
}

