package com.boyarskycompany.mcis.util.converters;

import javafx.scene.control.Alert;
import javafx.util.StringConverter;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Created by zandr on 02.10.2016.
 */
public class StringToDateConverter extends StringConverter<Date> {
    @Override
    public String toString(Date object) {
        return object.toString();
    }

    @Override
    public Date fromString(String string) {
        try {
            return new Date(new SimpleDateFormat("dd.MM.yyyy").parse(string).getTime());
        }
        catch (ParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("");
            alert.show();
        }
        return null;
    }
}