package com.car.service.carservice.repository;

import com.car.service.carservice.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, UUID> {

    @Query(
            value = "Select * from appointment ap where ap.appointment_id = :appointmentId and ap.status='ACTIVE'",
            nativeQuery = true
    )
    public Optional<AppointmentEntity> findByAppointmentId(UUID appointmentId);

    @Query(
            value = "Select * from appointment ap where ap.operator_name = :operatorName and ap.status='ACTIVE'",
            nativeQuery = true
    )
    public List<AppointmentEntity> findByOperatorName(String operatorName);

    @Query(
            value = "Select op.operator_name from operator op where op.operator_name not in (select ap.operator_name from appointment ap where ap.appointment_time = :appointmentTime and ap.status='ACTIVE')",
            nativeQuery = true
    )
    public List<String> findAvailableOperators(int appointmentTime);


}
