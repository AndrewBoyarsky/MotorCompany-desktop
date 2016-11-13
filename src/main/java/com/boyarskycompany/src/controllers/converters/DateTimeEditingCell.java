package com.boyarskycompany.src.controllers.converters;

import com.boyarskycompany.src.run.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
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

        setText(getDate().toString());
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
        dateTimePicker.calendarProperty().addListener(new ChangeListener<Calendar>() {
            @Override
            public void changed(ObservableValue<? extends Calendar> observable, Calendar oldValue, Calendar newValue) {
                System.out.println("Committed: " + dateTimePicker.getCalendar().getTime());
                Date localDateTime = dateTimePicker.getCalendar().getTime();
                if (localDateTime.getMinutes() != oldValue.getTime().getMinutes()) {
                    commitEdit(new Timestamp(newValue.getTime().getTime()));
                }
            }
        });
    }

    private Date getDate() {
        return getItem() == null ? new Date() : new Date(getItem().getTime());
    }


}
