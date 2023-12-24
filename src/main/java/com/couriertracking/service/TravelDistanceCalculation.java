package com.couriertracking.service;

import com.couriertracking.event.Location;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TravelDistanceCalculation implements DistanceCalculationStrategy {

    @Value("${courier-tracking.earth-radius}")
    private double earthRadius;

    @Override
    public double calculateDistance(Location lastLocation, Location currentLocation) {
        double lastLatitudeRadian = Math.toRadians(lastLocation.getLatitude());
        double lastLongitudeRadian = Math.toRadians(lastLocation.getLongitude());
        double currentLatitudeRadian = Math.toRadians(currentLocation.getLatitude());
        double currentLongitudeRadian = Math.toRadians(currentLocation.getLongitude());

        double differenceLatitude = currentLatitudeRadian - lastLatitudeRadian;
        double differenceLongitude = currentLongitudeRadian - lastLongitudeRadian;

        double a = Math.pow(Math.sin(differenceLatitude / 2), 2) + Math.cos(lastLatitudeRadian) *
            Math.cos(currentLatitudeRadian) * Math.pow(Math.sin(differenceLongitude / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }
}
