package com.boyarskycompany.src.controllers;


import com.boyarskycompany.src.controllers.database.HibernateUtil;
import com.boyarskycompany.src.controllers.entities.util.DoubleTuple;
import com.boyarskycompany.src.controllers.entities.util.StageRegister;
import com.boyarskycompany.src.controllers.entities.util.TimeThread;
import com.boyarskycompany.src.controllers.entities.util.alerts.ConfirmationAlert;
import com.boyarskycompany.src.controllers.entities.util.alerts.ErrorParsingAlert;
import com.boyarskycompany.src.run.Main;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.SessionFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static com.boyarskycompany.src.run.Main.getResLan;


public class MainController implements Initializable {
    private static final Date INIT_TIME = new Date();
    private static TimeThread timeThread = new TimeThread();
    static {
        System.out.println("Static loading...");
    }
    @FXML
    private Button closeButton;

    @FXML
    private MenuItem menuItemClose;

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
    private TextArea sessionTimeField;
    @FXML
    private MenuItem menuItemViewUsers;

    @FXML
    private MenuItem menuItemBackupDatabase;

    @FXML
    private MenuItem menuItemRestoreDatabase;


    @FXML
    private void handleBackupDatabaseClick() throws IOException, InterruptedException {
//        Map<String, Object> properties = HibernateUtil.getSessionFactory().getProperties();
//        mysql -P 3306 -h yourdomain.ru -u mylogin_user -p mylogin_db
        ResourceBundle res = Main.getResLan();
        String cmdExec = "mysqldump -usql7142192 -hsql7.freesqldatabase.com -phHqaefpvS5 sql7142192  > ";
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("backupDatabase.sql");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SQL (*.sql)", "*.sql"));
        fileChooser.setSelectedExtensionFilter(fileChooser.getExtensionFilters().get(0));
        fileChooser.setInitialDirectory(new File("C:\\"));
        File backupFile = fileChooser.showSaveDialog(Main.getPrStg());
        if (!backupFile.exists()) {
            Files.createFile(Paths.get(backupFile.getCanonicalPath()));
        }
        if (!backupFile.exists()) {
            System.err.println("File does not exist!");
            new ErrorParsingAlert("errorFileNotExistContentText", "errorFileNotExistHeader", "errorTitle");
            throw new FileNotFoundException("File " + backupFile.getAbsolutePath() +
                    " does not exist!");
        }
        ConfirmationAlert confirmationAlert = new ConfirmationAlert("confirmationBackupDatabaseContentText");
        confirmationAlert.setContentText(confirmationAlert.getContentText() + ": " + backupFile.getCanonicalPath());
        if (confirmationAlert.showAndWait().get().getButtonData() == ButtonBar.ButtonData.YES) {
            Stage backupProgressStage = getProgressStage(new Image("images/backupIcon.png"), res.getString("backupTitle"),
                    res.getString("backupProgressText"), backupFile);
            cmdExec += backupFile.getCanonicalPath();
            String finalCmdExec = cmdExec;
            Task backupTask = new Task() {
                @Override
                protected Void call() throws Exception {
                    Process process = Runtime.getRuntime().exec(new String[] {"cmd.exe", "/c", finalCmdExec});
                    int status = process.waitFor();
                    System.out.println("process status " + status);
                    return null;
                }
            };
            Thread thread = new Thread(backupTask);
            backupTask.setOnFailed(e -> {
                backupProgressStage.close();
                System.err.println("Backup was failed");
                new ErrorParsingAlert("errorBackupFailedText", "errorTitle", "errorTitle");
            });
            backupTask.setOnRunning(e -> backupProgressStage.show());
            backupTask.setOnSucceeded(e -> {
                backupProgressStage.close();
                System.out.println("Backup was successfully done");
                Alert informationAlert = new Alert(Alert.AlertType.INFORMATION, Main.getResLan().getString("informationBackupDatabaseContentText"));
                ((Stage) informationAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("images/aboutIcon.png"));
                informationAlert.show();
            });
            thread.start();
            backupProgressStage.show();
            System.out.println("Backup to file " + backupFile.getCanonicalPath());
        }
    }

    private Stage getProgressStage(Image icon, String title, String progressText, File processingFile) throws IOException {
        BorderPane dialogPane = new BorderPane();
        ProgressIndicator backupIndicator = new ProgressIndicator();
        backupIndicator.setPrefSize(25, 25);
        dialogPane.setLeft(backupIndicator);
        Text backupText = new Text(progressText);
        backupText.setStyle("-fx-font-size: 18");
        dialogPane.setRight(backupText);
        Text fileName = new Text(processingFile.getCanonicalPath());
        fileName.setTextAlignment(TextAlignment.CENTER);
        fileName.setStyle("-fx-font-size: 14; -fx-font-style: italic;");
        dialogPane.setBottom(fileName);
        dialogPane.setStyle("-fx-background-color: blue");
        Scene scene = new Scene(dialogPane);
        Stage stage = new Stage(StageStyle.UNDECORATED);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(icon);
        StageRegister.register(stage);
        return stage;
    }

    @FXML
    private void handleRestoreDatabaseClick() throws IOException, InterruptedException {
        ResourceBundle res = Main.getResLan();
        String restoreExecCommand = "mysql -usql7142192 -hsql7.freesqldatabase.com -phHqaefpvS5 sql7142192 < ";
        FileChooser openDiallog = new FileChooser();
        openDiallog.getExtensionFilters().add(new FileChooser.ExtensionFilter("SQL (*.sql)", "*.sql"));
        openDiallog.setInitialDirectory(new File("C:\\"));
        File databaseRestoringFile = openDiallog.showOpenDialog(Main.getPrStg());
        ConfirmationAlert confirmationAlert = new ConfirmationAlert("confirmationRestoreDatabaseContentText");
        confirmationAlert.setContentText(confirmationAlert.getContentText() + ": " + databaseRestoringFile.getCanonicalPath());
        if (!databaseRestoringFile.exists()) {
            System.err.println("File does not exist!");
            new ErrorParsingAlert("errorFileNotExistContentText", "errorFileNotExistHeader", "errorTitle");
            throw new FileNotFoundException("File " + databaseRestoringFile.getAbsolutePath() +
                    " does not exist!");
        }
        if (confirmationAlert.showAndWait().get().getButtonData() == ButtonBar.ButtonData.YES) {
            Stage restoreProgressStage = getProgressStage(new Image("images/restoreIcon.png"), res.getString("restoreTitle"), res.getString("restoreProgressText"), databaseRestoringFile);
            restoreExecCommand += databaseRestoringFile.getCanonicalPath();
            String finalRestoreExecCommand = restoreExecCommand;
            Task restoreTask = new Task() {
                @Override
                protected Void call() throws Exception {
                    Process process = Runtime.getRuntime().exec(new String[] {"cmd.exe", "/c", finalRestoreExecCommand});
                    int status = process.waitFor();
                    System.out.println("process status " + status);
                    return null;
                }
            };
            restoreTask.setOnSucceeded(e -> {
                restoreProgressStage.close();
                System.out.println("Restore was successfully completed");
                Alert informationAlert = new Alert(Alert.AlertType.INFORMATION, res.getString("informationRestoreDatabaseContentText"));
                ((Stage) informationAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("images/aboutIcon.png"));
                informationAlert.show();
            });
            restoreTask.setOnRunning(e -> restoreProgressStage.show());
            restoreTask.setOnFailed(e -> {
                restoreProgressStage.close();
                new ErrorParsingAlert("errorRestoreFailedText", "errorTitle", "errorTitle");
            });
            Thread thread = new Thread(restoreTask);
            thread.start();
        }
    }

    @FXML
    private void handleReloginButtonClick() throws IOException {
        timeThread.suspend();
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
        StageRegister.register(stage);
        stage.show();
    }

    @FXML
    private void handleChangeLanguageButtonClick() throws IOException, InterruptedException {
        ConfirmationAlert confirmationAlert = new ConfirmationAlert("confirmationChangeLanguageText");
        if (confirmationAlert.showAndWait().get().getButtonData() == ButtonBar.ButtonData.YES) {
            timeThread.suspend();
            StageRegister.closeStages();
            Main.chooseLanguage();
            Scene mainScene = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/mainScene.fxml"), getResLan()));
            Main.getPrStg().setScene(mainScene);
            Main.getPrStg().show();
        }
    }

    @FXML
    private void handleReportButtonClick() throws IOException {
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle(reportButton.getText());
        stage.getIcons().add(new Image("images/reportIcon.png"));
        Scene scene = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/reportScene.fxml"), getResLan()));
        stage.setScene(scene);
        StageRegister.register(stage);
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
        timeThread.stop();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sessionTimeField.setStyle("-fx-wrap-text: true");
        timeThread.setTextArea(sessionTimeField);
        timeThread.setRes(Main.getResLan());
        if (timeThread.isSuspended()) {
            timeThread.resume();
        } else {
            timeThread.start();
        }
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
                StageRegister.register(stage);
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
        String userPrivileges = LogController.getCurrentUser().getUserPrivileges();
        if (userPrivileges.equalsIgnoreCase("user")) {
            menuCreate.setVisible(false);
            createNewDocumentButton.setDisable(true);
            menuItemViewUsers.setVisible(false);
            menuItemBackupDatabase.setVisible(false);
            menuItemRestoreDatabase.setVisible(false);
        } else if (userPrivileges.equalsIgnoreCase("manager")) {
            menuCreate.setVisible(true);
            createNewDocumentButton.setDisable(false);
            menuItemViewUsers.setVisible(false);
            menuItemBackupDatabase.setVisible(false);
            menuItemRestoreDatabase.setVisible(false);
        } else if (userPrivileges.equalsIgnoreCase("admin")) {
            menuItemViewUsers.setVisible(true);
            menuItemBackupDatabase.setVisible(true);
            menuItemRestoreDatabase.setVisible(true);
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
