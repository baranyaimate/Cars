package com.pte.cars.car.service.impl;

import com.pte.cars.car.entity.CarEntity;
import com.pte.cars.car.service.CarService;
import com.pte.cars.core.service.impl.CoreCRUDServiceImpl;
import com.pte.cars.manufacturer.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl extends CoreCRUDServiceImpl<CarEntity> implements CarService {

    @Override
    protected void updateCore(CarEntity persistedEntity, CarEntity entity) {
        persistedEntity.setType(entity.getType());
        persistedEntity.setDoors(entity.getDoors());
        persistedEntity.setYearOfManufacture(entity.getYearOfManufacture());
        persistedEntity.setManufacturer(entity.getManufacturer());
    }

    @Override
    protected Class<CarEntity> getManagedClass() {
        return CarEntity.class;
    }

    public List<CarEntity> getIdFiltered(String filter) {
        return entityManager.createQuery("SELECT c FROM " + getManagedClass().getSimpleName() + " c WHERE CAST(c.id AS string) LIKE '%" + filter + "%'", getManagedClass())
                .getResultList();
    }

    public List<CarEntity> getDoorsFiltered(String filter) {
        return entityManager.createQuery("SELECT c FROM " + getManagedClass().getSimpleName() + " c WHERE CAST(c.doors AS string) LIKE '%" + filter + "%'", getManagedClass())
                .getResultList();
    }

    public List<CarEntity> getTypeFiltered(String filter) {
        return entityManager.createQuery("SELECT c FROM " + getManagedClass().getSimpleName() + " c WHERE UPPER(c.type) LIKE '%" + filter.toUpperCase() + "%'", getManagedClass())
                .getResultList();
    }

    public List<CarEntity> getYearFiltered(String filter) {
        return entityManager.createQuery("SELECT c FROM " + getManagedClass().getSimpleName() + " c WHERE CAST(c.year_of_manufacture AS string) LIKE '%" + filter + "%'", getManagedClass())
                .getResultList();
    }

    public List<CarEntity> getManufacturerFiltered(String filter) {
        return entityManager.createQuery("SELECT c FROM " + getManagedClass().getSimpleName() + " c INNER JOIN c.manufacturer WHERE UPPER(c.manufacturer.name) LIKE '%" + filter.toUpperCase() + "%'", getManagedClass())
                .getResultList();
    }
}
