package com.stunstyle.miomart2.view;

import com.stunstyle.miomart2.presenter.ReferencePresenter;
import com.stunstyle.miomart2.util.SimpleDatePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableSet;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.time.LocalDate;

public class ReferenceView extends GridPane {
    private ReferencePresenter presenter;

    public ReferenceView() {
        buildView();
    }

    public void setPresenter(ReferencePresenter presenter) {
        this.presenter = presenter;
    }

    protected void buildView() {
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));

        Text titleText = new Text("Справка");
        Font titleFont = Font.font("Arial", FontWeight.BOLD, 40);
        titleText.setFont(titleFont);

        this.add(titleText,0,1);

        Font subTitleFont = Font.font("Arial", FontWeight.BOLD, 20);

        Label chooseProductLabel = new Label("Избор на продукт");
        // chooseProductLabel.setFont(subTitleFont);
        this.add(chooseProductLabel,0,2);

        Label chooseStartDateLabel = new Label("Начална дата");
        this.add(chooseStartDateLabel, 1, 2);

        Label chooseEndDateLabel = new Label("Крайна дата");
        this.add(chooseEndDateLabel, 2, 2);

        TextField chooseProduct = new TextField();
        this.add(chooseProduct, 0, 3);

        chooseProduct.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                chooseProduct.focusedProperty().removeListener(this);
                ObservableSet<String> productNames = presenter.getAllProductNames();
                TextFields.bindAutoCompletion(chooseProduct, productNames);
            }
        });

        DatePicker startDatePicker = new SimpleDatePicker(LocalDate.now());
        this.add(startDatePicker, 1,3);

        DatePicker endDatePicker = new SimpleDatePicker(LocalDate.now());
        this.add(endDatePicker, 2, 3);

        Button btn = new Button("Справка");
        this.add(btn, 3, 3);

        btn.setOnAction(actionEvent -> {
            presenter.showReport(chooseProduct.getText(), startDatePicker.getValue(), endDatePicker.getValue());

        });


        //Stage referenceStage = new Stage();
       // referenceStage.setTitle("Справка - miomart2");

       // Scene referenceScene = new Scene(this, 640, 480);
       // referenceStage.setScene(referenceScene);
        //referenceStage.show();
    }


}
