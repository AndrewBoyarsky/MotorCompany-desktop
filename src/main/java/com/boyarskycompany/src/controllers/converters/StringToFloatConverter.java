package com.boyarskycompany.src.controllers.converters;

import com.boyarskycompany.src.controllers.util.alerts.ErrorParsingAlert;
import javafx.util.StringConverter;

/**
 * Created by zandr on 02.10.2016.
 */
public class StringToFloatConverter extends StringConverter<Float> {
    @Override
    public String toString(Float object) {
        if (object == null) {
            return "";
        }return object.toString();
    }

    @Override
    public Float fromString(String string) {
        if (string == null || string.length() == 0 || string.isEmpty()) {
            return null;
        }
        Float f = null;
        try {
            f = Float.parseFloat(string);
        }
        catch (NumberFormatException e) {
            new ErrorParsingAlert("FloatErrorParsingText", "FloatErrorParsingHeader");
        }
        return f;
    }
}
