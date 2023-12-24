package com.couriertracking.controller.v1;

import com.couriertracking.controller.v1.request.CreateStoreRequest;
import com.couriertracking.model.dto.StoreDto;
import com.couriertracking.service.StoreService;
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
@RequestMapping("/v1/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> createStore(@Valid @RequestBody CreateStoreRequest request) {
        Long id = storeService.create(StoreDto.builder()
            .name(request.getName()).latitude(request.getLatitude()).longitude(request.getLongitude()).build());
        return ResponseEntity.of(Optional.of(id));
    }

}
