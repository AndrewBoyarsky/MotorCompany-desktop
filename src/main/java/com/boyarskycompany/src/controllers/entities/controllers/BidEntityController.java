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

//    @Override
//    public void configureMainMenu(MenuBar mainMenu) {
//        super.configureMainMenu(mainMenu);
//        ResourceBundle res = Main.getResLan();
//        MenuItem addCargoItem = new MenuItem(res.getString("addCargoButton"));
//        addCargoItem.setOnAction(createAddCargoEventHandler());
//        Menu menuFile = mainMenu.getMenus().get(0);
//        menuFile.getItems().addAll(addCargoItem);
//    }

//    private EventHandler<ActionEvent> createAddCargoEventHandler() {
//        return new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                BidEntity bid = getTable().getSelectionModel().getSelectedItem();
//                if (bid == null) {
//                    CargoEntityController cargoController = new CargoEntityController();
//                } else {
//                    CargoEntityController cargoController = new CargoEntityController(bid, BidEntity.class);
//                }
//            }
//        };
//    }

    public <S> BidEntityController(Class<BidEntity> childEntityClass, S record, Class<S> parentEntityClass) {
        super(childEntityClass, record, parentEntityClass);
    }


}
