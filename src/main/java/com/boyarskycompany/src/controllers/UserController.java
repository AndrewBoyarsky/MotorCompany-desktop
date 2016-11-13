package com.boyarskycompany.src.controllers;

import com.boyarskycompany.src.controllers.entities.util.alerts.ConfirmationAlert;
import com.boyarskycompany.src.controllers.entities.util.alerts.WarningAlert;
import com.boyarskycompany.src.privacy.UserBean;
import com.boyarskycompany.src.privacy.Users;
import com.boyarskycompany.src.run.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;

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

    private boolean isUnique(String userName) {
        List<UserBean> list = userTable.getItems();
        ArrayList<String> userNameList = new ArrayList<String>();
        list.forEach(userBean -> userNameList.add(userBean.getUserName()));
        if (userNameList.contains(userName)) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText(Main.getResLan().getString("errorUniqueUserName"));
            errorAlert.show();
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

        userNameColumn.setOnEditCommit(event -> {
            if (isUnique(event.getNewValue())) {
                ConfirmationAlert confirmationAlert = new ConfirmationAlert("confirmUpdateUser");
                if (confirmationAlert.showAndWait().get().getButtonData() == ButtonBar.ButtonData.YES) {
                    UserBean user = event.getRowValue();
                    Users.changeUser(event.getOldValue(), event.getNewValue(), user.getUserPassword(), user.getUserPassword(), user.getUserPrivileges());
                    event.getRowValue().setUserName(event.getNewValue());
                }
            }
        });
        userPasswordColumn.setOnEditCommit(event -> {
            ConfirmationAlert confirmationAlert = new ConfirmationAlert("confirmUpdateUser");
            if (confirmationAlert.showAndWait().get().getButtonData() == ButtonBar.ButtonData.YES) {
                UserBean user = event.getRowValue();
                Users.changeUser(user.getUserName(), user.getUserName(), event.getOldValue(), event.getNewValue(), user.getUserPrivileges());
                event.getRowValue().setUserPassword(event.getNewValue());
            }
        });
        userPrivilegesColumn.setOnEditCommit(event -> {
            ConfirmationAlert confirmationAlert = new ConfirmationAlert("confirmUpdateUser");
            if (confirmationAlert.showAndWait().get().getButtonData() == ButtonBar.ButtonData.YES) {
                UserBean user = event.getRowValue();
                Users.changeUser(user.getUserName(), user.getUserName(), user.getUserPassword(), user.getUserPassword(), event.getNewValue());
                event.getRowValue().setUserPrivileges(event.getNewValue());
            }
        });

        userTable.setOnKeyPressed(e -> {
            if (e.getCode() == (KeyCode.DELETE)) {
                UserBean userBean = userTable.getSelectionModel().getSelectedItem();
                if (userBean != null) {
                    WarningAlert warningAlert = new WarningAlert("warningDeleteUser");
                    if (warningAlert.showAndWait().get().getButtonData() == ButtonBar.ButtonData.YES) {
                        Users.deleteUser(userBean);
                    }
                }
            }
        });

        addUserButton.setOnAction(e -> {
            if (userTextField.getText().isEmpty() || passwordTextField.getText().isEmpty() || privilegesComboBox.getValue().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                ResourceBundle res = Main.getResLan();
                alert.setContentText(res.getString("StringErrorParsingText"));
                alert.setTitle(res.getString("ErrorParsingTitle"));
                alert.setHeaderText(res.getString("StringErrorParsingHeader"));
                alert.show();
            } else if (isUnique(userTextField.getText())) {
                ConfirmationAlert confirmationAlert = new ConfirmationAlert("confirmAddNewUser");
                if (confirmationAlert.showAndWait().get().getButtonData() == ButtonBar.ButtonData.YES) {

                    UserBean user = new UserBean(userTextField.getText(), passwordTextField.getText(), privilegesComboBox.getValue());
                    userTable.getItems().add(user);
                    Users.addnewUser(user);
                }
            }
        });
        UserBean currentUser = getCurrentUser();
        userTable.getItems().remove(currentUser);
        currentUserArea.setText(resources.getString("currentUserText") + "\n" + resources.getString("userLabel")
                + ": " + currentUser.getUserName() + "\n" + resources.getString("passwordLabel") + ": " + currentUser.getUserPassword() +
                "\n" + resources.getString("privilegesColumn") + ": " + currentUser.getUserPrivileges());
        privilegesComboBox.setValue("User");
        privilegesComboBox.getItems().addAll("User", "Admin", "Manager");
    }

    private UserBean getCurrentUser() {
        List<UserBean> list = userTable.getItems();
        for (UserBean user : list) {
            if (user.getUserName().equals(LogController.getUserName())) {
                return user;
            }
        }
        return new UserBean();
    }
}
