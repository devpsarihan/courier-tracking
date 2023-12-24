package com.couriertracking.service;

import com.couriertracking.model.dto.StoreDto;
import com.couriertracking.model.entity.StoreEntity;
import com.couriertracking.repository.StoreRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public Long create(final StoreDto store) {
        return storeRepository.save(StoreEntity.builder()
                .name(store.getName())
                .latitude(store.getLatitude())
                .longitude(store.getLongitude())
                .build())
            .getId();
    }

    public List<StoreDto> getAll() {
        return storeRepository.findAll().stream().map(StoreEntity::toModel).collect(Collectors.toList());
    }
}
