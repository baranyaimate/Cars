package com.pte.cars.car.entity;

import com.pte.cars.core.entity.CoreEntity;
import com.pte.cars.manufacturer.entity.ManufacturerEntity;

import javax.persistence.*;

@Table(name = "car")
@Entity
public class CarEntity extends CoreEntity {

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "manufacturerId")
    private ManufacturerEntity manufacturer;

    @Column(name = "doors")
    private int doors;

    @Column(name = "yearOfManufacture")
    private int yearOfManufacture;
}
