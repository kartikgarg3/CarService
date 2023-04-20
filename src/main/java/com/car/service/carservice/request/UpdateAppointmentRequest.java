package com.car.service.carservice.request;

import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
public class UpdateAppointmentRequest {

    private UUID appointmentId;

    private int appointmentTime;
}
