package com.couriertracking.model.entity;

import com.couriertracking.model.base.BaseEntity;
import com.couriertracking.model.dto.CourierDto;
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
@Table(name = "COURIER")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class CourierEntity extends BaseEntity implements Serializable {

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    public CourierDto toModel() {
        return CourierDto.builder()
            .id(super.getId())
            .firstName(this.firstName)
            .lastName(this.lastName)
            .createDate(super.getCreateDate())
            .updateDate(super.getUpdateDate())
            .build();
    }
}
