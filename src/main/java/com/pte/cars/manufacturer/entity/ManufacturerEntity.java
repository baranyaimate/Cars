package com.pte.cars.manufacturer.entity;

import com.pte.cars.core.entity.CoreEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Table(name = "manufacturer")
@Entity
public class ManufacturerEntity extends CoreEntity {

    @Column(name = "name", unique = true)
    private String name;

    public ManufacturerEntity() {
    }

    public ManufacturerEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ManufacturerEntity)) return false;
        ManufacturerEntity that = (ManufacturerEntity) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
