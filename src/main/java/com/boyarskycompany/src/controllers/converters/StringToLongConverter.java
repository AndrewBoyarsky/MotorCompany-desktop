package com.boyarskycompany.src.controllers.converters;

import javafx.scene.control.Alert;
import javafx.util.StringConverter;

/**
 * Created by zandr on 09.10.2016.
 */
public class StringToLongConverter extends StringConverter<Long> {
        @Override
        public String toString(Long object) {
            if (object == null) {
                return "";
            }
            return object.toString();
        }

    @Override
    public Long fromString(String string) {

        if (string == null || string.length() == 0 || string.isEmpty()) {
            return null;
        }
        Long aLong = -1l;
        try {
            aLong = Long.parseLong(string);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.show();
        }
        return aLong;
    }
}

