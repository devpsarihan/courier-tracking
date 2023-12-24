package com.couriertracking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.couriertracking.model.dto.StoreDto;
import com.couriertracking.model.entity.StoreEntity;
import com.couriertracking.repository.StoreRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate_WhenGivenStore_ShouldReturnStoreId() {
        StoreDto storeDto = new StoreDto("Ataşehir MMM Migros", 40.9923307, 29.1244229);
        StoreEntity storeEntity = StoreEntity.builder().id(1L).name("Ataşehir MMM Migros").latitude(40.9923307).longitude(29.1244229).build();
        when(storeRepository.save(any(StoreEntity.class))).thenReturn(storeEntity);

        Long createdId = storeService.create(storeDto);

        assertNotNull(createdId);
        assertEquals(1L, createdId);
    }

    @Test
    void testGetAll_WhenNonParameter_ShouldReturnStores() {
        List<StoreEntity> storeEntities = Arrays.asList(
            StoreEntity.builder().id(1L).name("Ataşehir MMM Migros").latitude(40.9923307).longitude(29.1244229).build(),
            StoreEntity.builder().id(1L).name("Novada MMM Migros").latitude(40.986106).longitude(29.1161293).build()
        );
        when(storeRepository.findAll()).thenReturn(storeEntities);

        List<StoreDto> storeDtos = storeService.getAll();

        assertNotNull(storeDtos);
        assertEquals(2, storeDtos.size());
        assertEquals("Ataşehir MMM Migros", storeDtos.get(0).getName());
        assertEquals("Novada MMM Migros", storeDtos.get(1).getName());
    }

    @Test
    void testGetAll_WhenGivenNonParameter_ShouldReturnEmptyList() {
        when(storeRepository.findAll()).thenReturn(Collections.emptyList());
        List<StoreDto> stores = storeService.getAll();

        assertNotNull(stores);
        assertTrue(stores.isEmpty());
    }
}

