package com.boyarskycompany.src.controllers.converters;

import javafx.scene.control.Alert;
import javafx.util.StringConverter;

/**
 * Created by zandr on 02.10.2016.
 */
public class StringToDoubleConverter extends StringConverter<Double> {
    @Override
    public String toString(Double object) {
        if (object == null) {
            return "";
        }return object.toString();
    }

    @Override
    public Double fromString(String string) {
        if (string == null || string.length() == 0 || string.isEmpty()) {
            return null;
        }
        Double d = -1d;
        try {
            d = Double.parseDouble(string);
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
        }
        return d;
    }
}
