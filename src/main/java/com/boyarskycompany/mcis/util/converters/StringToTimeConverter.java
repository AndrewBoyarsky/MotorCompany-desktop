package com.boyarskycompany.mcis.util.converters;

import javafx.scene.control.Alert;
import javafx.util.StringConverter;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by zandr on 02.10.2016.
 */
public class StringToTimeConverter extends StringConverter<Time> {
    @Override
    public String toString(Time object) {
        return object.toString();
    }

    @Override
    public Time fromString(String string) {
        try {
            return new Time(new SimpleDateFormat("HH:mm").parse(string).getTime());
        }
        catch (ParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("");
            alert.show();
        }
        return null;
    }
}
