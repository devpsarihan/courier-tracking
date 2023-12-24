package com.couriertracking.repository;

import com.couriertracking.model.entity.CourierLocationEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierLocationRepository extends JpaRepository<CourierLocationEntity, Long> {

    Optional<CourierLocationEntity> findTopByCourierEntityIdOrderByIdDesc(Long courierId);

    List<CourierLocationEntity> findAllByCourierEntityId(Long courierId);
}
