package com.boyarskycompany.mcis.controllers;

import com.boyarskycompany.mcis.util.User;
import com.boyarskycompany.mcis.util.alerts.ConfirmationAlert;
import com.boyarskycompany.mcis.util.alerts.ErrorParsingAlert;
import com.boyarskycompany.mcis.util.alerts.WarningAlert;
import com.boyarskycompany.mcis.util.UserUtil;
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
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> userNameColumn;

    @FXML
    private TableColumn<User, String> userPasswordColumn;

    @FXML
    private TableColumn<User, String> userPrivilegesColumn;

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
    private User currentUser;
    private boolean isUnique(String userName) {
        List<User> list = userTable.getItems();
        ArrayList<String> userNameList = new ArrayList<String>();
        list.forEach(user -> userNameList.add(user.getName()));
        if (userNameList.contains(userName)) {
            return false;
        }
        if (currentUser.getName().equals(userName)) {
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));
        userNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        userPrivilegesColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userPrivileges"));
        userPrivilegesColumn.setCellFactory(ComboBoxTableCell.forTableColumn("Admin", "User", "Manager"));
        userPasswordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userPassword"));
        userPasswordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        userTable.getItems().setAll(UserUtil.getAllUsers());
        currentUser = getCurrentUser();

        userNameColumn.setOnEditCommit(event -> {
            if (isUnique(event.getNewValue())) {
                ConfirmationAlert confirmationAlert = new ConfirmationAlert("confirmationUpdateUserContentText");
                if (confirmationAlert.showAndWait().get().getButtonData() == ButtonBar.ButtonData.YES) {
                    User user = event.getRowValue();
                    event.getRowValue().setName(event.getNewValue());
                    UserUtil.updateUser(user, event.getRowValue());
                }
            }
        });
        userPasswordColumn.setOnEditCommit(event -> {
            ConfirmationAlert confirmationAlert = new ConfirmationAlert("confirmationUpdateUserContentText");
            if (confirmationAlert.showAndWait().get().getButtonData() == ButtonBar.ButtonData.YES) {
                User user = event.getRowValue();
                event.getRowValue().setPassword(event.getNewValue());
                UserUtil.updateUser(user, event.getRowValue());
            }
        });
        userPrivilegesColumn.setOnEditCommit(event -> {
            ConfirmationAlert confirmationAlert = new ConfirmationAlert("confirmationUpdateUserContentText");
            if (confirmationAlert.showAndWait().get().getButtonData() == ButtonBar.ButtonData.YES) {
                User user = event.getRowValue();
                event.getRowValue().setPrivileges(event.getNewValue());
                UserUtil.updateUser(user, event.getRowValue());
            }
        });

        userTable.setOnKeyPressed(e -> {
            if (e.getCode() == (KeyCode.DELETE)) {
                User user = userTable.getSelectionModel().getSelectedItem();
                if (user != null) {
                    WarningAlert warningAlert = new WarningAlert("warningDeleteUser");
                    ((Stage) warningAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("images/warningIcon.png"));
                    if (warningAlert.showAndWait().get().getButtonData() == ButtonBar.ButtonData.YES) {
                        UserUtil.deleteUser(user);
                        userTable.getItems().remove(user);
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
                        User user = new User(userTextField.getText(), passwordTextField.getText(), privilegesComboBox.getValue());
                        userTable.getItems().add(user);
                        UserUtil.addNewUser(user);
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
                + ": " + currentUser.getName() + "\n" + resources.getString("passwordLabel") + ": " + currentUser.getPassword() +
                "\n" + resources.getString("privilegesColumn") + ": " + currentUser.getPrivileges());
        privilegesComboBox.setValue("User");
        privilegesComboBox.getItems().addAll("User", "Admin", "Manager");
    }

    private User getCurrentUser() {
        return LoginController.getCurrentUser();
    }
}
