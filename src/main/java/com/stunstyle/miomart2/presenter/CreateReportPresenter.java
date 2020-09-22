package com.stunstyle.miomart2.presenter;

import java.time.LocalDate;

import com.stunstyle.miomart2.service.Product;
import com.stunstyle.miomart2.service.ProductService;
import com.stunstyle.miomart2.service.Record;
import com.stunstyle.miomart2.service.RecordService;
import com.stunstyle.miomart2.view.CreateReportView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreateReportPresenter {
    private CreateReportView view;
    private MainPresenter mainPresenter;
    private RecordService recordService;
    private ProductService productService;

    public CreateReportPresenter(CreateReportView view, MainPresenter mainPresenter, RecordService recordService, ProductService productService) {
        this.view = view;
        this.mainPresenter = mainPresenter;
        this.recordService = recordService;
        this.productService = productService;
    }

    public CreateReportView getView() {
        return view;
    }

    public void showReport(String productName, LocalDate startDate, LocalDate endDate) {
        ObservableList<Record> allRecordsForProduct = FXCollections.<Record>observableArrayList();
        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            allRecordsForProduct.addAll(recordService.getAllRecordsForDate(date));
        }

        allRecordsForProduct.removeIf(r -> !r.getProduct().getName().equals(productName));

        TableView<Record> recordTable = new TableView<>(allRecordsForProduct);

        TableColumn<Record, String> nameColumn = new TableColumn<>("Артикул");
        nameColumn.setCellValueFactory(recordStringCellDataFeatures -> {
            Record r = recordStringCellDataFeatures.getValue();
            Product p = r.getProduct();
            return p.nameProperty();
        });

        TableColumn<Record, Integer> quantityColumn = new TableColumn<>("Количество");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Record, Number> buyingPriceColumn = new TableColumn<>("Покупна цена");
        buyingPriceColumn.setCellValueFactory(recordNumberCellDataFeatures -> {
            Record r = recordNumberCellDataFeatures.getValue();
            Product p = r.getProduct();
            return p.buyingPriceProperty();
        });

        TableColumn<Record, Number> sellingPriceColumn = new TableColumn<>("Продажна цена");
        sellingPriceColumn.setCellValueFactory(recordNumberCellDataFeatures -> {
            Record r = recordNumberCellDataFeatures.getValue();
            Product p = r.getProduct();
            return p.sellingPriceProperty();
        });

        TableColumn<Record, LocalDate> dateColumn = new TableColumn<>("Дата");
        dateColumn.setCellValueFactory(recordLocalDateCellDataFeatures -> {
            Record r = recordLocalDateCellDataFeatures.getValue();
            return r.dateOfRecordProperty();
        });

        recordTable.getColumns().addAll(nameColumn, quantityColumn, buyingPriceColumn, sellingPriceColumn, dateColumn);

        GridPane reportGrid = new GridPane();
        reportGrid.setPadding(new Insets(25, 25, 25, 25));
        reportGrid.setHgap(10);
        reportGrid.setVgap(10);
        reportGrid.add(recordTable, 0, 0);
        GridPane.setColumnSpan(recordTable, 5);

        double totalBuyingPrice = allRecordsForProduct.stream().map(Record::getProduct).mapToDouble(Product::getBuyingPrice).sum();
        double roundedBuyingPrice = Math.floor(totalBuyingPrice * 100) / 100;

        double totalSellingPrice = allRecordsForProduct.stream().map(Record::getProduct).mapToDouble(Product::getSellingPrice).sum();
        double roundedSellingPrice = Math.floor(totalSellingPrice * 100) / 100;

        Text totalBuyingPriceText = new Text("Общо въведени: " + roundedBuyingPrice);
        reportGrid.add(totalBuyingPriceText, 4, 1);

        Text totalSellingPriceText = new Text("Общо изведени: " + roundedSellingPrice);
        reportGrid.add(totalSellingPriceText, 4, 2);

        GridPane.setHalignment(totalBuyingPriceText, HPos.RIGHT);
        GridPane.setHalignment(totalSellingPriceText, HPos.RIGHT);

        Stage reportStage = new Stage();
        recordTable.prefHeightProperty().bind(reportStage.heightProperty());
        recordTable.prefWidthProperty().bind(reportStage.widthProperty());
        Scene reportScene = new Scene(reportGrid, 640, 480);
        reportStage.setScene(reportScene);
        reportStage.setTitle("Справка за " + productName + " от " + startDate + " до " + endDate + " - miomart2");
        reportStage.show();
    }

    public ObservableSet<String> getAllProductNames() {
        ObservableSet<String> allProductNames = FXCollections.observableSet(productService.getAllProductNames());
        return allProductNames;
    }
}
