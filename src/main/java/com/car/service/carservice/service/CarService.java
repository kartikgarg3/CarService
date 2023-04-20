package com.car.service.carservice.service;

import com.car.service.carservice.constants.StatusEnum;
import com.car.service.carservice.entity.AppointmentEntity;
import com.car.service.carservice.exception.AppointmentNotFoundException;
import com.car.service.carservice.exception.NoOperatorAvailableException;
import com.car.service.carservice.exception.OperatorNotAvailableException;
import com.car.service.carservice.request.CancelAppointmentRequest;
import com.car.service.carservice.request.UpdateAppointmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    private AppointmentEntityService appointmentEntityService;


    //Books appointment for given appointmentTime with any of the available operator
    public AppointmentEntity bookAppointment(int appointmentTime) throws NoOperatorAvailableException {
        List<String> operators = getAvailableOperators(appointmentTime);
        if (operators.isEmpty()) {
            throw new NoOperatorAvailableException();
        }

        AppointmentEntity appointmentEntity = new AppointmentEntity();
        appointmentEntity.setAppointmentTime(appointmentTime);
        appointmentEntity.setOperatorName(operators.get(0));
        appointmentEntity.setStatus(StatusEnum.ACTIVE.toString());

        return appointmentEntityService.saveAppointment(appointmentEntity);
    }

    //Books appointment for given appointmentTime with given operator
    public AppointmentEntity bookAppointment(int appointmentTime, String operatorName) throws OperatorNotAvailableException {
        List<String> availableOperators = getAvailableOperators(appointmentTime);
        if (!availableOperators.contains(operatorName)) {
            throw new OperatorNotAvailableException();
        }

        AppointmentEntity appointmentEntity = new AppointmentEntity();
        appointmentEntity.setAppointmentTime(appointmentTime);
        appointmentEntity.setOperatorName(operatorName);
        appointmentEntity.setStatus(StatusEnum.ACTIVE.toString());

        return appointmentEntityService.saveAppointment(appointmentEntity);
    }

    public AppointmentEntity rescheduleAppointment(UpdateAppointmentRequest updateAppointmentReq) throws AppointmentNotFoundException, NoOperatorAvailableException {
        Optional<AppointmentEntity> appointmentEntity = appointmentEntityService.findByAppointmentId(updateAppointmentReq.getAppointmentId());
        if (appointmentEntity.isEmpty()) {
            throw new AppointmentNotFoundException();
        }

        List<String> operators = getAvailableOperators(updateAppointmentReq.getAppointmentTime());
        if (operators.isEmpty()) {
            throw new NoOperatorAvailableException();
        }

        appointmentEntity.get().setAppointmentTime(updateAppointmentReq.getAppointmentTime());
        if (!operators.contains(appointmentEntity.get().getOperatorName())) {
            appointmentEntity.get().setOperatorName(operators.get(0));
        }
        return appointmentEntityService.saveAppointment(appointmentEntity.get());
    }

    public AppointmentEntity cancelAppointment(CancelAppointmentRequest cancelAppointmentRequest) throws AppointmentNotFoundException {
        Optional<AppointmentEntity> appointmentEntity = appointmentEntityService.findByAppointmentId(cancelAppointmentRequest.getAppointmentId());
        if (appointmentEntity.isEmpty()) {
            throw new AppointmentNotFoundException();
        }
        appointmentEntity.get().setStatus(StatusEnum.CANCELLED.toString());
        return appointmentEntityService.saveAppointment(appointmentEntity.get());
    }

    public List<AppointmentEntity> showBookedAppointments(String operatorName) {
        List<AppointmentEntity> appointmentEntities = appointmentEntityService.findAllByOperatorName(operatorName);
        return appointmentEntities;
    }

    public List<String> showOpenSlots(String operatorName) {
        List<AppointmentEntity> appointmentEntities = appointmentEntityService.findAllByOperatorName(operatorName);
        List<Integer> bookedSlots = appointmentEntities.stream().map(AppointmentEntity::getAppointmentTime).sorted().collect(Collectors.toList());
        return getMergedFreeSlots(bookedSlots);
    }

    private List<String> getMergedFreeSlots(List<Integer> bookedSlots) {
        List<Integer> freeSlots = new ArrayList<>();
        List<String> freeSlotsMerged = new ArrayList<>();
        for (int i = 0; i <= 23; i++) {
            if (bookedSlots.contains(i)) {
                continue;
            }
            freeSlots.add(i);
        }
        if (freeSlots.isEmpty()) {
            return freeSlotsMerged;
        }

        int start = freeSlots.get(0);
        for (int i = 1; i < freeSlots.size(); i++) {
            if (freeSlots.get(i) != freeSlots.get(i - 1) + 1) {
                freeSlotsMerged.add(Integer.toString(start) + "-" + Integer.toString(freeSlots.get(i - 1) + 1));
                start = freeSlots.get(i);
            }
        }
        freeSlotsMerged.add(Integer.toString(start) + "-" + Integer.toString(freeSlots.get(freeSlots.size() - 1) + 1));
        return freeSlotsMerged;
    }

    private List<String> getAvailableOperators(int appointmentTime) {
        return appointmentEntityService.findAvailableOperators(appointmentTime);
    }

}
