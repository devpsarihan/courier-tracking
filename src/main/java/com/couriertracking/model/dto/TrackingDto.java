package com.couriertracking.model.dto;

import com.couriertracking.model.base.BaseDto;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TrackingDto extends BaseDto implements Serializable {

    private CourierDto courier;
    private StoreDto store;
}
