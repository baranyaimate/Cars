package com.pte.cars.car.service;

import com.pte.cars.car.entity.CarEntity;
import com.pte.cars.core.service.CoreCRUDService;

import java.util.List;

public interface CarService extends CoreCRUDService<CarEntity> {

    List<CarEntity> getIdFiltered(String filter);

    List<CarEntity> getDoorsFiltered(String filter);

    List<CarEntity> getTypeFiltered(String filter);

    List<CarEntity> getYearFiltered(String filter);

    List<CarEntity> getManufacturerFiltered(String filter);

}
