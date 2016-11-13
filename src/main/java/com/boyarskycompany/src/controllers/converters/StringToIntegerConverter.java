package com.boyarskycompany.src.controllers.converters;

import javafx.scene.control.Alert;
import javafx.util.StringConverter;

/**
 * Created by zandr on 02.10.2016.
 */
public class StringToIntegerConverter extends StringConverter<Integer> {
    @Override
    public String toString(Integer object) {
        if (object == null) {
            return "";
        }return object.toString();
    }

    @Override
    public Integer fromString(String string) {
        if (string == null || string.length() == 0 || string.isEmpty()) {
            return null;
        }
        Integer integer = -1;
        try {
            integer = Integer.parseInt(string);
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
        }
        return integer;
    }
}
