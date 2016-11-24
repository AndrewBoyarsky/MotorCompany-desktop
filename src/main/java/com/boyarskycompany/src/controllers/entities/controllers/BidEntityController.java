package com.boyarskycompany.src.controllers.entities.controllers;


import com.boyarskycompany.src.controllers.entities.EntityController;
import com.boyarskycompany.src.entities.BidEntity;

/**
 * Created by zandr on 16.10.2016.
 */
public class BidEntityController extends EntityController<BidEntity> {
    public BidEntityController() {
        super(BidEntity.class);
    }

    public <S> BidEntityController(Class<BidEntity> childEntityClass, S record, Class<S> parentEntityClass) {
        super(childEntityClass, record, parentEntityClass);
    }


}
