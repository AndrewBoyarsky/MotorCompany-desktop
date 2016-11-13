package com.boyarskycompany.src.controllers;


import com.boyarskycompany.src.controllers.database.HibernateUtil;
import com.boyarskycompany.src.run.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static com.boyarskycompany.src.run.Main.getResLan;


public class MainController implements Initializable {
    private Date initTime;
    @FXML
    private BorderPane root;

    @FXML
    private Button closeButton;

    @FXML
    private Menu menuFile;

    @FXML
    private MenuItem menuItemClose;

    @FXML
    private Menu menuHelp;

    @FXML
    private MenuItem menuItemAbout;

    @FXML
    private Button createNewDocumentButton;

    @FXML
    private Button reportButton;

    @FXML
    private Button reloginButton;

    @FXML
    private Menu menuCreate;

    @FXML
    private Menu menuTools;
    @FXML
    private TextField sessionTime;

    @FXML
    private void handleReloginButtonClick() throws IOException {
        Main.getPrStg().close();
        HibernateUtil.shutDown();
        Scene loginScene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/login.fxml"), getResLan()));
        Stage stage = Main.getPrStg();
        stage.close();
        stage.setScene(loginScene);
        stage.setTitle(getResLan().getString("loginTitle"));
        stage.show();
    }

    @FXML
    private void handleViewUsersButtonClick() throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/usersScene.fxml"), getResLan()));
        stage.setScene(scene);
        stage.getIcons().add(new Image("images/userIcon.png"));
        stage.setResizable(false);
        stage.setTitle(getResLan().getString("userTitle"));
        stage.show();
    }

    @FXML
    private void handleReportButtonClick() throws IOException {
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle(reportButton.getText());
        stage.getIcons().add(new Image("images/reportIcon.png"));
        Scene scene = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/reportScene.fxml"), getResLan()));
        stage.setScene(scene);
        stage.show();
    }

    private DoubleTuple<List<String>, List<String>> getTablesNames() {
        ResourceBundle resourceBundle = getResLan();
        SessionFactory factory = HibernateUtil.getSessionFactory();
        List<String> entityList = new ArrayList<String>();
        List<String> tableList = new ArrayList<String>();
        factory.getMetamodel().getEntities().forEach(entityType -> {
            String entityName = entityType.getName();
            entityList.add(entityName);
            String tableName = entityName.substring(0, 1).toLowerCase() +
                    entityName.substring(1, entityName.indexOf("Entity"));
            tableList.add(resourceBundle.getString(tableName));
        });
        DoubleTuple<List<String>, List<String>> ev = new DoubleTuple<List<String>, List<String>>(tableList, entityList);
        return ev;
    }

    private void constructDocumentTable(String tableName) {
        DoubleTuple<List<String>, List<String>> map = getTablesNames();
        List<String> entityList = map.getwFiels();
        List<String> tableList = map.gettField();
        Class clazz = null;
        String className = entityList.get(tableList.indexOf(tableName));
        try {
            clazz = Class.forName("controllers.entities.controllers."
                    + className + "Controller");
            try {
                clazz.newInstance();
            }
            catch (InstantiationException e) {
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        catch (ClassNotFoundException e) {
            try {
                clazz = Class.forName("com.boyarskycompany.src.controllers.entities.EntityController");
                Class entityClass = Class.forName("com.boyarskycompany.src.entities." + className);
                Constructor constructor = null;
                Constructor[] constructors = clazz.getDeclaredConstructors();
                for (int i = 0; i < constructors.length; i++) {
                    Constructor constr = constructors[i];
                    if (constr.getParameterCount() == 1 && constr.getParameters()[0].getType().isInstance(entityClass)) {
                        constructor = constr;
                        constructor.newInstance(entityClass);
                        break;
                    }
                }
            }
            catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
            catch (InstantiationException e1) {
                e1.printStackTrace();
            }
            catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }
    }

    @FXML
    private void handleCreateNewDocumentClick() {
        ResourceBundle resourceBundle = getResLan();
        DoubleTuple<List<String>, List<String>> map = getTablesNames();
        List<String> tableList = map.gettField();
        ChoiceDialog<String> dialog = new ChoiceDialog<String>(tableList.get(0), tableList);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setHeaderText(resourceBundle.getString("createNewDocumentHeader"));
        dialog.setTitle(resourceBundle.getString("createNewDocumentTitle"));
        dialog.showAndWait().ifPresent(tableName -> {
            constructDocumentTable(tableName);
        });
    }

    @FXML
    private void handleCloseButton() {
        Main.getPrStg().close();
        HibernateUtil.shutDown();
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTime = new Date();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Date currentDate = new Date();
                    Date sessionDuration = new Date(currentDate.getTime() - initTime.getTime() + 22 * 3600 * 1000);
                    sessionTime.setText(resources.getString("sessionDuration") + ": " + new SimpleDateFormat("HH:mm:ss").format(sessionDuration)
                    + " " + resources.getString("userLabel") + ": " + LogController.getUserName());
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        Main.getPrStg().setTitle(resources.getString("mainTitle"));
        DoubleTuple<List<String>, List<String>> tuple = getTablesNames();
        tuple.gettField().forEach(tableName -> {
            MenuItem item = new MenuItem(tableName);
            item.setOnAction(event -> {
                constructDocumentTable(tableName);
            });
            menuCreate.getItems().add(item);
        });
        menuItemClose.setOnAction(closeButton.getOnAction());
        menuItemAbout.setOnAction(event -> {
            try {
                Scene scene = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/about.fxml"), getResLan()));
                Stage stage = new Stage(StageStyle.UTILITY);
                stage.setTitle(menuItemAbout.getText());
                stage.setScene(scene);
                stage.getIcons().add(new Image("images/aboutIcon.png"));
                stage.setResizable(false);
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
        String userPrivileges = LogController.getUserPrivileges();
        if (userPrivileges.equalsIgnoreCase("user")) {
            menuCreate.setVisible(false);
            createNewDocumentButton.setDisable(true);
            menuTools.setVisible(false);
        } else if (userPrivileges.equalsIgnoreCase("manager")) {
            menuTools.setVisible(false);
            menuCreate.setVisible(true);
            createNewDocumentButton.setDisable(false);
        } else if (userPrivileges.equalsIgnoreCase("admin")) {
            menuTools.setVisible(true);
            menuCreate.setVisible(true);
            createNewDocumentButton.setDisable(false);
        }
        Main.getPrStg().setOnCloseRequest(e -> {
            handleCloseButton();
        });
//        reportButton.setContentDisplay(ContentDisplay.LEFT);
        reportButton.setGraphic(new ImageView("images/createReportIcon.png"));
//        createNewDocumentButton.setContentDisplay(ContentDisplay.LEFT);
        createNewDocumentButton.setGraphic(new ImageView("images/createNewDocumentIcon.png"));
//        reloginButton.setContentDisplay(ContentDisplay.LEFT);
        reloginButton.setGraphic(new ImageView("images/reloginIcon.png"));
//        closeButton.setContentDisplay(ContentDisplay.LEFT);
        closeButton.setGraphic(new ImageView("images/closeIcon.png"));
//        closeButton.setStyle("-fx-background-position: left; -fx-background-image: url('images/docIcon.png')");
    }
}
