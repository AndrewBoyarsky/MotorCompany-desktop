package com.boyarskycompany.src.controllers.converters;

import javafx.util.StringConverter;

/**
 * Created by zandr on 13.10.2016.
 */
public class StringToBooleanConverter extends StringConverter<Boolean> {
    @Override
    public String toString(Boolean object) {
        return Boolean.toString(object);
    }

    @Override
    public Boolean fromString(String string) {
        return Boolean.parseBoolean(string);
    }
}
