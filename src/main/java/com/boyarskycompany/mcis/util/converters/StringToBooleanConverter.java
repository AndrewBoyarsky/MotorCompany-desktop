package com.boyarskycompany.mcis.util.converters;

import com.boyarskycompany.mcis.util.alerts.ErrorParsingAlert;
import javafx.util.StringConverter;

/**
 * Created by zandr on 13.10.2016.
 */
public class StringToBooleanConverter extends StringConverter<Boolean> {
    @Override
    public String toString(Boolean object) {
        if (Boolean.toString(object).equals("true")) {
            return "+";
        } else if (Boolean.toString(object).equals("false")) {
            return "-";
        }
        return "";
    }

    @Override
    public Boolean fromString(String string) {
        Boolean b = null;
        if (string.equals("+")) {
            b = true;
        } else if (string.equals("-")) {
            b = false;
        } else {
            new ErrorParsingAlert("BooleanErrorParsingText", "BooleanErrorParsingHeader");
        }
        return b;
    }
}
