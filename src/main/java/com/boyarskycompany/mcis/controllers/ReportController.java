package com.boyarskycompany.mcis.controllers;

import com.boyarskycompany.mcis.persistence.GenericDAOImpl;
import com.boyarskycompany.mcis.entities.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zandr on 31.10.2016.
 */
public class ReportController implements Initializable {
	@FXML
	private Button waybillReportButton;
	
	@FXML
	private Button transportReportButton;
	
	@FXML
	private Button bidReportButton;
	
	@FXML
	private Button agreementReportButton;
	
	@FXML
	private Button roadListReportButton;
	
	@FXML
	private Button totalBillReportButton;
	
	@FXML
	private Button closeButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		agreementReportButton.setOnAction(e -> {
			try {
				new GenericDAOImpl<AgreementEntity>(AgreementEntity.class).createReportViaConnection("/reports/agreement/AgreementReport.jrxml");
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		waybillReportButton.setOnAction(e -> {
			try {
				new GenericDAOImpl<WaybillEntity>(WaybillEntity.class).createReportViaConnection("/reports/waybill/WaybillReport.jrxml");
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		bidReportButton.setOnAction(e -> {
			try {
				new GenericDAOImpl<BidEntity>(BidEntity.class).createReportViaConnection("/reports/bid/BidReport.jrxml");
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		transportReportButton.setOnAction(e -> {
			try {
				new GenericDAOImpl<TransportEntity>(TransportEntity.class).createReportViaConnection("/reports/transport/TransportReport.jrxml");
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		roadListReportButton.setOnAction(e -> {
			try {
				new GenericDAOImpl<RoadlistEntity>(RoadlistEntity.class).createReportViaConnection("/reports/roadlist/RoadlistReport.jrxml");
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		totalBillReportButton.setOnAction(e -> {
			try {
				new GenericDAOImpl<TotalbillEntity>(TotalbillEntity.class).createReportViaConnection("/reports/totalbill/TotalbillReport.jrxml");
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		closeButton.setOnAction(event -> {
			((Stage) closeButton.getScene().getWindow()).close();
		});
		closeButton.setGraphic(new ImageView("images/closeIcon.png"));
		bidReportButton.setGraphic(new ImageView("images/createBidReportIcon.png"));
		agreementReportButton.setGraphic(new ImageView("images/createAgreementReportIcon.png"));
		totalBillReportButton.setGraphic(new ImageView("images/createTotalBillReportIcon.png"));
		waybillReportButton.setGraphic(new ImageView("images/createWaybillReportIcon.png"));
		transportReportButton.setGraphic(new ImageView("images/createTransportReportIcon.png"));
		roadListReportButton.setGraphic(new ImageView("images/createRoadListReportIcon.png"));
	}

}
   
    

