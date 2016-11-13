package com.boyarskycompany.src.controllers.entities.controllers;


import com.boyarskycompany.src.controllers.entities.EntityController;
import com.boyarskycompany.src.entities.CargoEntity;

/**
 * Created by zandr on 16.10.2016.
 */
public class CargoEntityController extends EntityController<CargoEntity> {
    public CargoEntityController() {
        super(CargoEntity.class);
    }

    public <S> CargoEntityController(S record, Class<S> parentEntityClass) {
        super(CargoEntity.class, record, parentEntityClass);
    }
}
