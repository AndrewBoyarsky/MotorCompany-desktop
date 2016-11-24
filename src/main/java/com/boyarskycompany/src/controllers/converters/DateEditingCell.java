package com.boyarskycompany.src.controllers.converters;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateEditingCell<T> extends TableCell<T, Date> {

    private DatePicker datePicker;

    public DateEditingCell() {
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createDatePicker();
            setText(null);
            setGraphic(datePicker);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        setGraphic(null);
    }

    @Override
    public void updateItem(Date item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (datePicker != null) {
                    datePicker.setValue(getDate());
                }
                setText(null);
                setGraphic(datePicker);
            } else {
                setText(getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                setGraphic(null);
            }
        }
    }

    private void createDatePicker() {
        datePicker = new DatePicker(getDate());
        datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        datePicker.setEditable(false);
        datePicker.setOnAction((e) -> {
            System.out.println("Committed: " + datePicker.getValue().toString());
            String source = datePicker.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date date = null;
            try {
                date = new Date(simpleDateFormat.parse(source).getTime());
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            commitEdit(date);
        });
//            datePicker.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
//                if (!newValue) {
//                    commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
//                }
//            });
    }

    private LocalDate getDate() {
        return getItem() == null ? LocalDate.now() : new java.util.Date(getItem().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}