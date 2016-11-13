package com.boyarskycompany.src.controllers.entities.controllers;


import com.boyarskycompany.src.controllers.entities.EntityController;
import com.boyarskycompany.src.entities.AgreementEntity;

/**
 * Created by zandr on 16.10.2016.
 */
public class AgreementEntityController extends EntityController<AgreementEntity> {
    public AgreementEntityController() {
        super(AgreementEntity.class);
//        configureMainMenu(getMainMenu());
    }

//    @Override
//    public void configureMainMenu(MenuBar menuBar) {
//        super.configureMainMenu(menuBar);
//        ResourceBundle res = Main.getResLan();
//        MenuItem addBidButton = new MenuItem(res.getString("addBidButton"));
//        addBidButton.setOnAction(createAddingBidEventHandler());
//    }
//
//    private EventHandler<ActionEvent> createAddingBidEventHandler() {
//        return new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                AgreementEntity agreementEntity = getTable().getSelectionModel().getSelectedItem();
//                if (agreementEntity == null) {
//                    BidEntityController bidController = new BidEntityController();
//                } else {
//                    BidEntityController bidController = new BidEntityController(BidEntity.class, agreementEntity, AgreementEntity.class);
//                }
//            }
//        };
//    }
//
//    @Override
//    public void configureNavigationBar(BorderPane pane) {
//
//    }
}
