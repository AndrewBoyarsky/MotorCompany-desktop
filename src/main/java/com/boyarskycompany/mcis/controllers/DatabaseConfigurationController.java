package com.boyarskycompany.mcis.controllers;

import com.boyarskycompany.mcis.run.MainApplicationEntry;
import com.boyarskycompany.mcis.util.HibernateUtil;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * Created by zandr on 25.12.2016.
 */
public class DatabaseConfigurationController implements Initializable {
    private static final Logger log = LogManager.getLogger(DatabaseConfigurationController.class.getSimpleName());
    @FXML
    private TextField dbUrlTextField;

    @FXML
    private TextField dbUserTextField;

    @FXML
    private TextField dbPasswordTextField;
    @FXML
    private CheckBox defaultDatabaseUsingCheckBox;

    @FXML
    private Button OKButton;

    @FXML
    private Button testButton;

    @FXML
    private ProgressIndicator connectionIndicator;

    @FXML
    private Text connectionStatusText;

    private int connectionTime = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Properties dbProperties = new Properties();
        OKButton.setText(resources.getString("OKButton"));
        defaultDatabaseUsingCheckBox.setOnAction(e -> {
            try {
                if (defaultDatabaseUsingCheckBox.isSelected()) {
                    dbProperties.clear();
                    dbProperties.load(getClass().getResourceAsStream("/db_conf.properties"));
                    OKButton.setDisable(true);
                    String dbUrl = dbProperties.getProperty("hibernate.connection.url", "null");
                    String dbUser = dbProperties.getProperty("hibernate.connection.username", "null");
                    String dbPassword = dbProperties.getProperty("hibernate.connection.password", "null");
                    if (dbUrl.equals("null") || dbUrl.isEmpty()) {
                        log.error("DB url " + dbUrl + " is not exist or empty");
                        OKButton.setDisable(true);
                        return;
                    }
                    if (dbUser.equals("null") || dbUser.isEmpty()) {
                        log.error("DB user " + dbUser + " is not exist or empty");
                        OKButton.setDisable(true);
                        return;
                    }
                    if (dbPassword.equals("null")) {
                        log.error("DB password" + dbPassword + " is not exist or empty");
                        OKButton.setDisable(true);
                        return;
                    }
                    dbUrlTextField.setText(dbUrl);
                    dbPasswordTextField.setText(dbPassword);
                    dbUserTextField.setText(dbUser);
                    dbUserTextField.setEditable(false);
                    dbUrlTextField.setEditable(false);
                    dbPasswordTextField.setEditable(false);
                } else {
                    dbUserTextField.setEditable(true);
                    dbUrlTextField.setEditable(true);
                    dbPasswordTextField.setEditable(true);
                }
            }

            catch (IOException ex) {
                log.error("Unable to find or load default database configuration file", ex);
                defaultDatabaseUsingCheckBox.setSelected(false);
                defaultDatabaseUsingCheckBox.setDisable(true);
            }
        });
        EventHandler<ActionEvent> testButtonClickHandler = event -> {
            try {
                EventHandler<ActionEvent> currentHandler = testButton.getOnAction();
                Task dbConnectionTask = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        boolean isError = false;
                        connectionIndicator.setVisible(true);
                        dbProperties.clear();
                        dbProperties.setProperty("hibernate.connection.url", "jdbc:mysql://" + dbUrlTextField.getText());
                        dbProperties.setProperty("hibernate.connection.username", dbUserTextField.getText());
                        dbProperties.setProperty("hibernate.connection.password", dbPasswordTextField.getText());
                        try {
                            HibernateUtil.connect(dbProperties);
                        }
                        catch (InterruptedException iex) {
                            log.warn("Db connection: " + dbProperties + " was interrupted.", iex);
                            isError = true;
                        }
                        catch (Exception ex) {
                            log.error("Connection error! Properties: " + dbProperties.toString(), ex);
                            connectionStatusText.setText(resources.getString("connectionBadStatusText"));
                            connectionStatusText.setFill(Color.RED);
                            isError = true;
                        }
                        connectionIndicator.setVisible(false);
                        if (!isError) {
                            connectionStatusText.setText(resources.getString("connectionOKStatusText"));
                            connectionStatusText.setFill(Color.GREEN);
                            OKButton.setDisable(false);
                            ChangeListener<String> changeListener = (observable, oldValue, newValue) -> {
                                if (!dbProperties.getProperty("hibernate.connection.url").equals("jdbc:mysql://" + dbUrlTextField.getText())
                                        || !dbProperties.getProperty("hibernate.connection.username").equals(dbUserTextField.getText())
                                        || !dbProperties.getProperty("hibernate.connection.password").equals(dbPasswordTextField.getText())) {
                                    OKButton.setDisable(true);
                                    connectionStatusText.setText("");
                                } else {
                                    OKButton.setDisable(false);
                                    connectionStatusText.setText(resources.getString("connectionOKStatusText"));
                                }
                            };
                            dbUrlTextField.textProperty().addListener(changeListener);
                            dbUserTextField.textProperty().addListener(changeListener);
                            dbPasswordTextField.textProperty().addListener(changeListener);
                        }

                        return null;
                    }
                };
                dbConnectionTask.setOnSucceeded(e -> {
                    testButton.setText(resources.getString("testButton"));
                    testButton.setOnAction(currentHandler);
                    log.info("Db mysql connection is ready. " + dbProperties);
                });
                Thread dbConnectionThread = new Thread(dbConnectionTask);
                Thread timeThread = new Thread(() -> {
                    try {
                        while (dbConnectionTask.isRunning()) {
                            TimeUnit.SECONDS.sleep(1);
                            connectionTime++;
                            if (connectionTime == 15) {
                                dbConnectionThread.interrupt();
                            }
                            connectionIndicator.setVisible(false);
                            connectionStatusText.setText(resources.getString("connectionTimeoutStatusText"));
                            connectionStatusText.setFill(Color.RED);
                            OKButton.setDisable(true);
                            testButton.setText(resources.getString("testButton"));
                            testButton.setOnAction(currentHandler);
                        }
                    }
                    catch (InterruptedException iex) {
                        log.warn("Time thread for connection was interrupted!", iex);
                        connectionTime = 0;
                    }
                });
                timeThread.start();
                dbConnectionThread.start();
                testButton.setText(resources.getString("cancelButton"));
                testButton.setOnAction(cancelHandler -> {
                    dbConnectionThread.interrupt();
                    timeThread.interrupt();
                    connectionIndicator.setVisible(false);
                    testButton.setOnAction(currentHandler);
                    testButton.setText(resources.getString("testButton"));
                });
            }
            catch (Exception ex) {
                log.error("Error with connecting to database", ex);
            }
        };
        testButton.setOnAction(testButtonClickHandler);
        OKButton.setOnAction(newHandler -> {
            Stage mainStage = MainApplicationEntry.getPrimaryStage();
            mainStage.hide();
            try {
                mainStage.setScene(new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/mainScene.fxml"), MainApplicationEntry.getLanguageResourceBundle())));
                mainStage.show();
            }
            catch (IOException ex) {
                log.error("Unable to load scene! ", ex);
            }
        });
    }

}
