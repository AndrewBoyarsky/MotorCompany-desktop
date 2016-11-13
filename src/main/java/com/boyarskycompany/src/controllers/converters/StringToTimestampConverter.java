package com.boyarskycompany.src.controllers.converters;

import javafx.scene.control.Alert;
import javafx.util.StringConverter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Created by zandr on 02.10.2016.
 */
public class StringToTimestampConverter extends StringConverter<Timestamp> {
    @Override
    public String toString(Timestamp object) {
        return object.toString();
    }

    @Override
    public Timestamp fromString(String string) {
        try {
            return new Timestamp(new SimpleDateFormat("dd.MM.yyyy").parse(string).getTime());
        }
        catch (ParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("");
            alert.show();
        }
        return null;
    }
}
