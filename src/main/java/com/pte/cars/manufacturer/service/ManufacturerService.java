package com.pte.cars.manufacturer.service;

import com.pte.cars.core.service.CoreCRUDService;
import com.pte.cars.manufacturer.entity.ManufacturerEntity;

import java.util.List;

public interface ManufacturerService extends CoreCRUDService<ManufacturerEntity> {

    List<ManufacturerEntity> getIdFiltered(String filter);

    List<ManufacturerEntity> getNameFiltered(String filter);

}
