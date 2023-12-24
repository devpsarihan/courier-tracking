package com.couriertracking.model.entity;

import com.couriertracking.model.base.BaseEntity;
import com.couriertracking.model.dto.TrackingDto;
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
@Table(name = "TRACKING")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class TrackingEntity extends BaseEntity implements Serializable {

    @Column(name = "COURIER_ID", nullable = false)
    private Long courierId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COURIER_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private CourierEntity courierEntity;

    @Column(name = "STORE_ID", nullable = false)
    private Long storeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORE_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private StoreEntity storeEntity;

    public TrackingDto toModel() {
        return TrackingDto.builder()
            .id(super.getId())
            .courier(this.courierEntity.toModel())
            .store(this.getStoreEntity().toModel())
            .createDate(super.getCreateDate())
            .updateDate(super.getUpdateDate())
            .build();
    }
}
