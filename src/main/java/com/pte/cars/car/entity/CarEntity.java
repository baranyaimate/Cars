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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ManufacturerEntity getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerEntity manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(int yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }
}
