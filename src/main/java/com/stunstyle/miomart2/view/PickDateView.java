package com.stunstyle.miomart2.view;

import com.stunstyle.miomart2.presenter.PickDatePresenter;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.converter.LocalDateStringConverter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.FormatStyle;
import java.util.Locale;

public class PickDateView extends GridPane {
    private PickDatePresenter presenter;
    private DatePicker datePicker;

    public PickDateView() {
        buildView();
    }

    public void setPresenter(PickDatePresenter presenter) {
        this.presenter = presenter;
    }

    protected void buildView() {
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));

        datePicker = new DatePicker(LocalDate.now());

        Locale.setDefault(new Locale.Builder().setLanguage("bg").setRegion("BG").setScript("Cyrl").build());
        // TODO: change first day of week
        datePicker.setEditable(false);
        datePicker.setConverter(new LocalDateStringConverter(FormatStyle.MEDIUM));
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
        datePicker.setDayCellFactory(dayCellFactory);
        Label chooseRecordLabel = new Label("Избор на запис");
        Button chooseRecordButton = new Button("Преглед");
        chooseRecordButton.setOnAction(actionEvent -> presenter.showEditRecord());
        this.add(chooseRecordLabel, 0, 0);
        this.add(datePicker, 0, 1);
        this.add(chooseRecordButton, 1, 1);
    }

    public LocalDate getDatePicked() {
        return datePicker.getValue();
    }
}
