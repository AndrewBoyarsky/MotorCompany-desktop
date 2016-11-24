package com.boyarskycompany.src.controllers.converters;

import com.boyarskycompany.src.run.Main;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import jfxtras.scene.control.CalendarPicker;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zandr on 05.10.2016.
 */
public class DateTimeEditingCell<T> extends TableCell<T, Timestamp> {
    private CalendarPicker dateTimePicker;

    public DateTimeEditingCell() {
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createDatePicker();
            setText(null);
            setGraphic(dateTimePicker);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(getDate()));
        setGraphic(null);
    }

    @Override
    public void updateItem(Timestamp item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (dateTimePicker != null) {

                    Calendar cal = Calendar.getInstance(Main.getResLan().getLocale());
                    cal.setTime(getDate());
                    dateTimePicker.setCalendar(cal);
                }
                setText(null);
                setGraphic(dateTimePicker);
            } else {
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm", Main.getResLan().getLocale());
                this.setText(format.format(getDate()));
                setGraphic(null);
            }
        }
    }

    private void createDatePicker() {
        dateTimePicker = new CalendarPicker();
        dateTimePicker.setLocale(Main.getResLan().getLocale());
        Calendar cal = Calendar.getInstance(Main.getResLan().getLocale());
        cal.setTime(getDate());
        dateTimePicker.setShowTime(true);
        dateTimePicker.setCalendar(cal);
        dateTimePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        dateTimePicker.focusedProperty().addListener(e -> {
            System.out.println(2);
        });
        dateTimePicker.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                    commitEdit(new Timestamp(dateTimePicker.getCalendar().getTime().getTime()));
            }
        });
    }

    private Date getDate() {
        return getItem() == null ? new Date() : new Date(getItem().getTime());
    }


}
