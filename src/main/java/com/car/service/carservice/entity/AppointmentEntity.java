package com.car.service.carservice.entity;

import com.car.service.carservice.constants.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "appointment")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "appointment_id")
    private UUID appointmentId;

    @Column(name = "appointment_time")
    private int appointmentTime;

    @Column(name = "operator_name")
    private String operatorName;

    @Column(name = "status")
    private String status;

    public AppointmentEntity() {
    }

    public AppointmentEntity(UUID appointmentId, int appointmentTime, String operatorName, String status) {
        this.appointmentId = appointmentId;
        this.appointmentTime = appointmentTime;
        this.operatorName = operatorName;
        this.status = status;
    }
}
