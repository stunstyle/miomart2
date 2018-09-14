package com.stunstyle.miomart2.presenter;

import com.stunstyle.miomart2.service.ProductService;
import com.stunstyle.miomart2.service.Record;
import com.stunstyle.miomart2.service.RecordService;
import com.stunstyle.miomart2.util.RecordListViewCell;
import com.stunstyle.miomart2.view.ReferenceView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDate;

public class ReferencePresenter {
    private ReferenceView view;
    private MainPresenter mainPresenter;
    private RecordService recordService;
    private ProductService productService;

    public ReferencePresenter(ReferenceView view, MainPresenter mainPresenter, RecordService recordService, ProductService productService) {
        this.view = view;
        this.mainPresenter = mainPresenter;
        this.recordService = recordService;
        this.productService = productService;
    }

    public ReferenceView getView() {
        return view;
    }


    public void showReport(String productName, LocalDate startDate, LocalDate endDate) {
        ObservableList<Record> allRecordsForProduct = FXCollections.<Record>observableArrayList();
        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            allRecordsForProduct.addAll(recordService.getAllRecordsForDate(date));
        }

        allRecordsForProduct.removeIf(r -> !r.getProduct().getName().equals(productName));
        ListView<Record> listView = new ListView<>(allRecordsForProduct);
        listView.setCellFactory(new Callback<ListView<Record>, ListCell<Record>>() {
            @Override
            public ListCell<Record> call(ListView<Record> recordListView) {
                return new RecordListViewCell();
            }
        });

        Stage reportStage = new Stage();
        Scene reportScene = new Scene(listView);
        reportStage.setScene(reportScene);
        reportStage.setTitle("Справка за " + productName + " от " + startDate + " до " + endDate + " - miomart2");
        reportStage.show();

    }

    public ObservableSet<String> getAllProductNames() {
        ObservableSet<String> allProductNames = FXCollections.observableSet(productService.getAllProductNames());
        return allProductNames;
    }
}
