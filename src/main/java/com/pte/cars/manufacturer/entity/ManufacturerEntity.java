package com.pte.cars.manufacturer.entity;

import com.pte.cars.core.entity.CoreEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "manufacturer")
@Entity
public class ManufacturerEntity extends CoreEntity {

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ManufacturerEntity() {
    }

    public ManufacturerEntity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
