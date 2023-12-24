package com.couriertracking.repository;

import com.couriertracking.model.entity.TrackingEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackingRepository extends JpaRepository<TrackingEntity, Long> {

    Optional<TrackingEntity> findTopByCourierEntityIdOrderByIdDesc(Long courierId);
}
