package com.stunstyle.miomart2.ui.view;

import java.time.LocalDate;

import com.stunstyle.miomart2.ui.presenter.PickDatePresenter;
import com.stunstyle.miomart2.ui.util.SimpleDatePicker;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

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

        datePicker = new SimpleDatePicker(LocalDate.now());


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
