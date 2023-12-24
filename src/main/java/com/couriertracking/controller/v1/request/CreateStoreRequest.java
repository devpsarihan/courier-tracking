package com.couriertracking.controller.v1.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStoreRequest implements Serializable {

    @NotNull
    @Length(min = 5, max = 255)
    private String name;

    @NotNull
    @Min(value = 0)
    @Max(value = Long.MAX_VALUE)
    private Double latitude;

    @NotNull
    @Min(value = 0)
    @Max(value = Long.MAX_VALUE)
    private Double longitude;
}
