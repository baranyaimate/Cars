package com.pte.cars.manufacturer.service.impl;

import com.pte.cars.car.entity.CarEntity;
import com.pte.cars.core.service.impl.CoreCRUDServiceImpl;
import com.pte.cars.manufacturer.entity.ManufacturerEntity;
import com.pte.cars.manufacturer.service.ManufacturerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerServiceImpl extends CoreCRUDServiceImpl<ManufacturerEntity> implements ManufacturerService {

    @Override
    protected void updateCore(ManufacturerEntity persistedEntity, ManufacturerEntity entity) {
        persistedEntity.setName(entity.getName());
    }

    @Override
    protected Class<ManufacturerEntity> getManagedClass() {
        return ManufacturerEntity.class;
    }

    public List<ManufacturerEntity> getIdFiltered(String filter) {
        return entityManager.createQuery("SELECT m FROM " + getManagedClass().getSimpleName() + " m WHERE CAST(m.id AS string) LIKE '%" + filter + "%'", getManagedClass())
                .getResultList();
    }

    public List<ManufacturerEntity> getNameFiltered(String filter) {
        return entityManager.createQuery("SELECT m FROM " + getManagedClass().getSimpleName() + " m WHERE m.name LIKE '%" + filter + "%'", getManagedClass())
                .getResultList();
    }

}
