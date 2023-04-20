package com.car.service.carservice.service;

import com.car.service.carservice.entity.AppointmentEntity;
import com.car.service.carservice.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentEntityServiceImpl implements AppointmentEntityService {

    @Autowired
    private AppointmentRepository appointmentRepository;


    @Override
    public Optional<AppointmentEntity> findByAppointmentId(UUID appointmentId) {
        return appointmentRepository.findByAppointmentId(appointmentId);
    }

    @Override
    public List<AppointmentEntity> findAllByOperatorName(String operatorName) {
        return appointmentRepository.findByOperatorName(operatorName);
    }

    @Override
    public AppointmentEntity saveAppointment(AppointmentEntity appointmentEntity) {
        return appointmentRepository.save(appointmentEntity);
    }

    public List<String> findAvailableOperators(int appointmentTime) {
        return appointmentRepository.findAvailableOperators(appointmentTime);
    }
}
