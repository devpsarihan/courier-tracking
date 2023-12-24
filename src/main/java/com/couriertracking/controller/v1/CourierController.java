package com.couriertracking.controller.v1;

import com.couriertracking.controller.v1.request.CreateCourierRequest;
import com.couriertracking.model.dto.CourierDto;
import com.couriertracking.service.CourierService;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/couriers")
@RequiredArgsConstructor
public class CourierController {

    private final CourierService courierService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> createCourier(@Valid @RequestBody CreateCourierRequest request) {
        Long id = courierService.create(CourierDto.builder()
            .firstName(request.getFirstName()).lastName(request.getLastName()).build());
        return ResponseEntity.of(Optional.of(id));
    }
}
