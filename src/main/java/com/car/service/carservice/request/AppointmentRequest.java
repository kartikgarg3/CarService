package com.car.service.carservice.request;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class AppointmentRequest {

    @Min(value=0, message="appointment time must be equal or greater than 0")
    @Max(value=23, message="appointment time must be equal or less than 23")
    private Integer appointmentTime;
    private String operatorName;
}
