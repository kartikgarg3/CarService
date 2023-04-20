package com.car.service.carservice.controller;

import com.car.service.carservice.entity.AppointmentEntity;
import com.car.service.carservice.exception.AppointmentNotFoundException;
import com.car.service.carservice.exception.NoOperatorAvailableException;
import com.car.service.carservice.exception.OperatorNotAvailableException;
import com.car.service.carservice.request.AppointmentRequest;
import com.car.service.carservice.request.CancelAppointmentRequest;
import com.car.service.carservice.request.UpdateAppointmentRequest;
import com.car.service.carservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/car-service")
public class CarServiceController {
    @Autowired
    CarService carService;

    @PostMapping("/appointment")
    @ResponseBody
    public AppointmentEntity bookAppointment(@RequestBody AppointmentRequest appointmentReq) throws OperatorNotAvailableException, NoOperatorAvailableException {
        if (appointmentReq.getOperatorName() != null) {
            return carService.bookAppointment(appointmentReq.getAppointmentTime(), appointmentReq.getOperatorName());
        }
        return carService.bookAppointment(appointmentReq.getAppointmentTime());
    }

    @PutMapping("/appointment/update")
    public AppointmentEntity updateAppointment(
            @RequestBody @Valid UpdateAppointmentRequest updateAppointmentReq) throws AppointmentNotFoundException, NoOperatorAvailableException {
        return carService.rescheduleAppointment(updateAppointmentReq);
    }

    @PutMapping("/appointment/cancel")
    public AppointmentEntity cancelAppointment(
            @RequestBody @Valid CancelAppointmentRequest cancelAppointmentRequest) throws AppointmentNotFoundException, NoOperatorAvailableException {
        return carService.cancelAppointment(cancelAppointmentRequest);
    }

    @GetMapping("/appointment/booked-slots")
    public List<String> getBookedSlots(@RequestParam String operatorName) {
        List<AppointmentEntity> appointmentEntities = carService.showBookedAppointments(operatorName);
        return appointmentEntities.stream().map(appointmentEntity -> String.format("%s-%s", appointmentEntity.getAppointmentTime(), appointmentEntity.getAppointmentTime() + 1)).collect(Collectors.toList());
    }

    @GetMapping("/appointment/open-slots")
    public List<String> getOpenSlots(@RequestParam String operatorName) {
        return carService.showOpenSlots(operatorName);
    }
}
