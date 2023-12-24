package com.couriertracking.service;

import com.couriertracking.event.Location;

public interface DistanceCalculationStrategy {

    double calculateDistance(final Location lastLocation, final Location currentLocation);
}
