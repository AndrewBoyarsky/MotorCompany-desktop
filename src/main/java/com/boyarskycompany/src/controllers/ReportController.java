package com.boyarskycompany.src.controllers;

import com.boyarskycompany.src.controllers.database.dauimpl.BaseDAUImpl;
import com.boyarskycompany.src.entities.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
			new BaseDAUImpl<AgreementEntity>(AgreementEntity.class).createReportViaConnection("/reports/agreement/AgreementReport.jrxml");
		});
		waybillReportButton.setOnAction(e -> {
			new BaseDAUImpl<WaybillEntity>(WaybillEntity.class).createReportViaConnection("/reports/waybill/WaybillReport.jrxml");
		});
		bidReportButton.setOnAction(e -> {
			new BaseDAUImpl<BidEntity>(BidEntity.class).createReportViaConnection("/reports/bid/BidReport.jrxml");
		});
		transportReportButton.setOnAction(e -> {
			new BaseDAUImpl<TransportEntity>(TransportEntity.class).createReportViaConnection("/reports/transport/TransportReport.jrxml");
		});
		roadListReportButton.setOnAction(e -> {
			new BaseDAUImpl<RoadlistEntity>(RoadlistEntity.class).createReportViaConnection("/reports/roadlist/RoadlistReport.jrxml");
		});
		totalBillReportButton.setOnAction(e -> {
			new BaseDAUImpl<TotalbillEntity>(TotalbillEntity.class).createReportViaConnection("/reports/totalbill/TotalbillReport.jrxml");
		});
		closeButton.setOnAction(event -> {
			((Stage) closeButton.getScene().getWindow()).close();
		});
	}
}
   
    

