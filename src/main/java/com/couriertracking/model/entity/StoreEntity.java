package com.couriertracking.model.entity;

import com.couriertracking.model.base.BaseEntity;
import com.couriertracking.model.dto.StoreDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@Table(name = "STORE")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class StoreEntity extends BaseEntity implements Serializable {

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;

    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;

    public StoreDto toModel() {
        return StoreDto.builder()
            .id(super.getId())
            .name(this.name)
            .latitude(this.latitude)
            .longitude(this.longitude)
            .createDate(super.getCreateDate())
            .updateDate(super.getUpdateDate())
            .build();
    }
}
