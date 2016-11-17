package com.boyarskycompany.src.controllers;

import com.boyarskycompany.src.controllers.entities.util.alerts.ConfirmationAlert;
import com.boyarskycompany.src.controllers.entities.util.alerts.ErrorParsingAlert;
import com.boyarskycompany.src.controllers.entities.util.alerts.WarningAlert;
import com.boyarskycompany.src.privacy.UserBean;
import com.boyarskycompany.src.privacy.Users;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by zandr on 12.11.2016.
 */
public class UserController implements Initializable {
    @FXML
    private TableView<UserBean> userTable;

    @FXML
    private TableColumn<UserBean, String> userNameColumn;

    @FXML
    private TableColumn<UserBean, String> userPasswordColumn;

    @FXML
    private TableColumn<UserBean, String> userPrivilegesColumn;

    @FXML
    private TextField userTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private ComboBox<String> privilegesComboBox;

    @FXML
    private Button addUserButton;

    @FXML
    private TextArea currentUserArea;
    private UserBean currentUser;
    private boolean isUnique(String userName) {
        List<UserBean> list = userTable.getItems();
        ArrayList<String> userNameList = new ArrayList<String>();
        list.forEach(userBean -> userNameList.add(userBean.getUserName()));
        if (userNameList.contains(userName)) {
            return false;
        }
        if (currentUser.getUserName().equals(userName)) {
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userNameColumn.setCellValueFactory(new PropertyValueFactory<UserBean, String>("userName"));
        userNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        userPrivilegesColumn.setCellValueFactory(new PropertyValueFactory<UserBean, String>("userPrivileges"));
        userPrivilegesColumn.setCellFactory(ComboBoxTableCell.forTableColumn("Admin", "User", "Manager"));
        userPasswordColumn.setCellValueFactory(new PropertyValueFactory<UserBean, String>("userPassword"));
        userPasswordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        userTable.getItems().setAll(Users.getAllUsers());
        currentUser = getCurrentUser();

        userNameColumn.setOnEditCommit(event -> {
            if (isUnique(event.getNewValue())) {
                ConfirmationAlert confirmationAlert = new ConfirmationAlert("confirmationUpdateUserContentText");
                if (confirmationAlert.showAndWait().get().getButtonData() == ButtonBar.ButtonData.YES) {
                    UserBean user = event.getRowValue();
                    event.getRowValue().setUserName(event.getNewValue());
                    Users.changeUser(user, event.getRowValue());
                }
            }
        });
        userPasswordColumn.setOnEditCommit(event -> {
            ConfirmationAlert confirmationAlert = new ConfirmationAlert("confirmationUpdateUserContentText");
            if (confirmationAlert.showAndWait().get().getButtonData() == ButtonBar.ButtonData.YES) {
                UserBean user = event.getRowValue();
                event.getRowValue().setUserPassword(event.getNewValue());
                Users.changeUser(user, event.getRowValue());
            }
        });
        userPrivilegesColumn.setOnEditCommit(event -> {
            ConfirmationAlert confirmationAlert = new ConfirmationAlert("confirmationUpdateUserContentText");
            if (confirmationAlert.showAndWait().get().getButtonData() == ButtonBar.ButtonData.YES) {
                UserBean user = event.getRowValue();
                event.getRowValue().setUserPrivileges(event.getNewValue());
                Users.changeUser(user, event.getRowValue());
            }
        });

        userTable.setOnKeyPressed(e -> {
            if (e.getCode() == (KeyCode.DELETE)) {
                UserBean userBean = userTable.getSelectionModel().getSelectedItem();
                if (userBean != null) {
                    WarningAlert warningAlert = new WarningAlert("warningDeleteUser");
                    ((Stage) warningAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("images/warningIcon.png"));
                    if (warningAlert.showAndWait().get().getButtonData() == ButtonBar.ButtonData.YES) {
                        Users.deleteUser(userBean);
                        userTable.getItems().remove(userBean);
                    }
                }
            }
        });

        addUserButton.setOnAction(e -> {
            if (!isUnique(userTextField.getText())) {
                userTextField.setStyle("-fx-border-color: red");
                ErrorParsingAlert errorParsingAlert = new ErrorParsingAlert("errorUniqueUserName", "StringErrorParsingHeader");
            } else {
                if (userTextField.getText().isEmpty()) {
                    userTextField.setStyle("-fx-border-color: red");
                    passwordTextField.setStyle("-fx-border-color: lightgray");
                    privilegesComboBox.setStyle("-fx-border-color: lightgray");
                    ErrorParsingAlert errorParsingAlert = new ErrorParsingAlert("StringErrorParsingText", "StringErrorParsingHeader");
                } else if (passwordTextField.getText().isEmpty()) {
                    userTextField.setStyle("-fx-border-color: greenyellow");
                    passwordTextField.setStyle("-fx-border-color: red");
                    privilegesComboBox.setStyle("-fx-border-color: lightgray");
                    ErrorParsingAlert errorParsingAlert = new ErrorParsingAlert("StringErrorParsingText", "StringErrorParsingHeader");
                } else {
                    privilegesComboBox.setStyle("-fx-border-color: greenyellow");
                    passwordTextField.setStyle("-fx-border-color: greenyellow");
                    userTextField.setStyle("-fx-border-color: greenyellow");

                    ConfirmationAlert confirmationAlert = new ConfirmationAlert("confirmAddNewUser");
                    if (confirmationAlert.showAndWait().get().getButtonData() == ButtonBar.ButtonData.YES) {
                        UserBean user = new UserBean(userTextField.getText(), passwordTextField.getText(), privilegesComboBox.getValue());
                        userTable.getItems().add(user);
                        Users.addnewUser(user);
                        privilegesComboBox.setStyle("-fx-border-color: lightgray");
                        passwordTextField.setStyle("-fx-border-color: lightgray");
                        userTextField.setStyle("-fx-border-color: lightgray");
                        passwordTextField.clear();
                        userTextField.clear();
                        privilegesComboBox.setValue("User");
                    }
                }
            }
        });
        userTable.getItems().remove(currentUser);
        currentUserArea.setText(resources.getString("currentUserText") + "\n" + resources.getString("userLabel")
                + ": " + currentUser.getUserName() + "\n" + resources.getString("passwordLabel") + ": " + currentUser.getUserPassword() +
                "\n" + resources.getString("privilegesColumn") + ": " + currentUser.getUserPrivileges());
        privilegesComboBox.setValue("User");
        privilegesComboBox.getItems().addAll("User", "Admin", "Manager");
    }

    private UserBean getCurrentUser() {
        return LogController.getCurrentUser();
    }
}
