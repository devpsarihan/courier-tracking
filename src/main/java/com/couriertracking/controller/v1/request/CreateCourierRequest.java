package com.couriertracking.controller.v1.request;

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
public class CreateCourierRequest implements Serializable {

    @NotNull
    @Length(min = 5, max = 255)
    private String firstName;

    @NotNull
    @Length(min = 5, max = 255)
    private String lastName;
}
