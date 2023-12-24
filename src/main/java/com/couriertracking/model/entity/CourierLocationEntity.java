package com.couriertracking.model.entity;

import com.couriertracking.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COURIER_LOCATION")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class CourierLocationEntity extends BaseEntity implements Serializable {

    @Column(name = "COURIER_ID", nullable = false)
    private Long courierId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COURIER_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private CourierEntity courierEntity;

    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;

    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;

    @Column(name = "TRAVEL_DISTANCE", nullable = false)
    private Double travelDistance;
}
