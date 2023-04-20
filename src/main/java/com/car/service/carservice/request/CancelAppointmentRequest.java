package com.car.service.carservice.request;

import lombok.Data;

import java.util.UUID;

@Data
public class CancelAppointmentRequest {
    private UUID appointmentId;
}
