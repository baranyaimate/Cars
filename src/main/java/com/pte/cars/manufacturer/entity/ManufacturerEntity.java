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

}
