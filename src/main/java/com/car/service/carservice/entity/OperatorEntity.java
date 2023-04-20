package com.car.service.carservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "operator")
public class OperatorEntity {
    @Id
    @Column(name = "operator_name")
    private String operatorName;
}
