package com.stunstyle.miomart2.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.FormatStyle;
import java.util.Locale;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.converter.LocalDateStringConverter;

public class SimpleDatePicker extends DatePicker {
    public SimpleDatePicker(LocalDate date) {
        super(date);
        this.setEditable(false);
        Locale.setDefault(new Locale.Builder().setLanguage("bg").setRegion("BG").setScript("Cyrl").build());
        // TODO: change first day of week
        this.setConverter(new LocalDateStringConverter(FormatStyle.MEDIUM));
        Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isAfter(LocalDate.now())) {
                                    this.setDisable(true);
                                }

                                DayOfWeek day = DayOfWeek.from(item);
                                if (day == DayOfWeek.SUNDAY) {
                                    this.setTextFill(Color.RED);
                                }
                            }
                        };
                    }
                };
        this.setDayCellFactory(dayCellFactory);
    }
}
