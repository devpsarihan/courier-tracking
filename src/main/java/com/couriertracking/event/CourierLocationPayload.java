package com.couriertracking.event;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CourierLocationPayload implements Serializable {

    private Long courierId;
    private Double latitude;
    private Double longitude;
}
