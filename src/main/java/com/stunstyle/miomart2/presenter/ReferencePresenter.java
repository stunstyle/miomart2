package com.stunstyle.miomart2.presenter;

import com.stunstyle.miomart2.service.Product;
import com.stunstyle.miomart2.service.ProductService;
import com.stunstyle.miomart2.service.Record;
import com.stunstyle.miomart2.service.RecordService;
import com.stunstyle.miomart2.view.ReferenceView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
        reportGrid.setPadding(new Insets(25,25,25,25));
        reportGrid.setHgap(10);
        reportGrid.setVgap(10);
        reportGrid.add(recordTable, 0, 0);
        GridPane.setColumnSpan(recordTable, 6);

        Text totalTextTitle = new Text("Общо въведени:");
        reportGrid.add(totalTextTitle, 1, 1);

        Text totalQuantity = new Text();
        totalQuantity.setText(String.valueOf(allRecordsForProduct.stream().mapToInt(Record::getQuantity).sum()));

        Text totalBuyingPrice = new Text();
        totalBuyingPrice.setText(String.valueOf(allRecordsForProduct.stream().map(Record::getProduct).mapToDouble(Product::getBuyingPrice).sum()));

        Text totalSellingPrice = new Text();
        totalSellingPrice.setText(String.valueOf(allRecordsForProduct.stream().map(Record::getProduct).mapToDouble(Product::getSellingPrice).sum()));

        reportGrid.add(totalQuantity, 2,1);
        reportGrid.add(totalBuyingPrice, 3, 1);
        reportGrid.add(totalSellingPrice, 4, 1);

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
