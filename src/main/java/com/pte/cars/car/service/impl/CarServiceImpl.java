package com.pte.cars.car.service.impl;

import com.pte.cars.car.entity.CarEntity;
import com.pte.cars.car.service.CarService;
import com.pte.cars.core.service.impl.CoreCRUDServiceImpl;
import org.springframework.stereotype.Service;

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
}
