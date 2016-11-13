package com.boyarskycompany.src.run;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {
    private static Stage prStg;
    private static ResourceBundle resLan;

    private static void setResLan(ResourceBundle resLan) {
        Main.resLan = resLan;
    }

    public static ResourceBundle getResLan() {
        return resLan;
    }

    public static Stage getPrStg() {
        return prStg;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image("images/car.png"));
        chooseLanguage();
        if (resLan == null) {
            System.exit(0);
        }
        setPrStg(primaryStage);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        Scene loadScene = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/load.fxml"), resLan));
        primaryStage.setOnCloseRequest(e -> {
            System.exit(0);
        });
        primaryStage.setScene(loadScene);
//        primaryStage.show(); // only for test

    }

    private static void setPrStg(Stage prStgage) {
        prStg = prStgage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void chooseLanguage() {
        ArrayList<String> listOfLanguages = new ArrayList<>(Arrays.asList("Русский", "Українська", "English"));
        ChoiceDialog<String> languageChooser = new ChoiceDialog<>("English", listOfLanguages);
        ((Stage) languageChooser.getDialogPane().getScene().getWindow()).getIcons().add(new Image("images/languageIcon.png"));
        Locale defaultLocale = Locale.getDefault();
        ResourceBundle defaultRes = null;
        if (resLan != null) {
            defaultRes = resLan;
        } else if (defaultLocale.getLanguage().equalsIgnoreCase("ru")) {
            defaultRes = ResourceBundle.getBundle("internationalization/languages", CharsetControl.RUS);
        } else if (defaultLocale.getLanguage().equalsIgnoreCase("ua")) {
            defaultRes = ResourceBundle.getBundle("internationalization/languages_uk", CharsetControl.UA);
        } else {
            defaultRes = ResourceBundle.getBundle("internationalization/languages", CharsetControl.ENG);
        }
        languageChooser.setContentText(defaultRes.getString("chooseLanguageText"));
        languageChooser.setTitle(defaultRes.getString("chooseLanguageTitle"));
        languageChooser.setHeaderText(defaultRes.getString("chooseLanguageHeader"));
        Optional<String> result = languageChooser.showAndWait();
        result.ifPresent(language -> {
            if (language.equals("Русский")) {
                setResLan(ResourceBundle.getBundle("internationalization/languages", CharsetControl.RUS));
            } else if (language.equals("Українська")) {
                setResLan(ResourceBundle.getBundle("internationalization/languages_uk", CharsetControl.UA));
            } else if (language.equals("English")) {
                setResLan(ResourceBundle.getBundle("internationalization/languages", CharsetControl.ENG));
            }
        });
    }
}

