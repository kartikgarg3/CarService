package com.car.service.carservice.service;

import com.car.service.carservice.entity.AppointmentEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Component
public interface AppointmentEntityService {

    public Optional<AppointmentEntity> findByAppointmentId(UUID id);

    public List<AppointmentEntity> findAllByOperatorName(String operatorName);

    public AppointmentEntity saveAppointment(AppointmentEntity appointmentEntity);

    List<String> findAvailableOperators(int appointmentTime);
}
