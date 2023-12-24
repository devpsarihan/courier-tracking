package com.couriertracking.controller.v1;

import com.couriertracking.controller.v1.request.CreateCourierLocationRequest;
import com.couriertracking.service.CourierLocationService;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/courier-locations")
@RequiredArgsConstructor
public class CourierLocationController {

    private final CourierLocationService courierLocationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> createCourierLocation(@RequestParam Long courierId,
        @Valid @RequestBody CreateCourierLocationRequest request) {
        Long id = courierLocationService.create(courierId, request);
        return ResponseEntity.of(Optional.of(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Double> getTotalTravelDistance(@RequestParam Long courierId) {
        Double totalTravelDistance = courierLocationService.getTotalTravelDistanceByCourierId(courierId);
        return ResponseEntity.of(Optional.of(totalTravelDistance));
    }
}
