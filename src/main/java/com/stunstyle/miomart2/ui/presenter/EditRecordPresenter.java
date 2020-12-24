package com.stunstyle.miomart2.ui.presenter;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.stunstyle.miomart2.exception.CouldNotAddProductException;
import com.stunstyle.miomart2.service.ProductService;
import com.stunstyle.miomart2.service.Record;
import com.stunstyle.miomart2.service.RecordService;
import com.stunstyle.miomart2.ui.view.EditRecordView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class EditRecordPresenter {
    private EditRecordView view;
    private MainPresenter mainPresenter;
    private RecordService recordService;
    private ProductService productService;
    private LocalDate dateOfRecord;

    public EditRecordPresenter(EditRecordView view, MainPresenter mainPresenter, RecordService recordService, ProductService productService) {
        this.view = view;
        this.mainPresenter = mainPresenter;
        this.recordService = recordService;
        this.productService = productService;
        this.view.setPresenter(this);
    }

    public LocalDate getDateOfRecord() {
        return dateOfRecord;
    }

    public void setDateOfRecord(LocalDate dateOfRecord) {
        this.dateOfRecord = dateOfRecord;
    }

    public EditRecordView getView() {
        return view;
    }

    public void updateTitleText() {
        view.setEditTitleText(dateOfRecord.toString());
    }

    public void updateRecords() {
        view.setRecords(recordService.getAllRecordsForDate(dateOfRecord));
    }

    public void saveRecords(List<Record> records) {
        recordService.removeAllRecordsFromDate(dateOfRecord);
        recordService.addRecordsToDate(records, dateOfRecord);
    }

    public ObservableSet<String> getAllProductNames() {
        ObservableSet<String> allProductNames = FXCollections.observableSet(productService.getAllProductNames());
        return allProductNames;
    }

    public String getLatestBuyingPrice(String productName) {
        double latestBuyingPrice = recordService.getLatestBuyingPrice(productName);
        return String.valueOf(latestBuyingPrice);
    }

    public String getLatestSellingPrice(String productName) {
        double latestSellingPrice = recordService.getLatestSellingPrice(productName);
        return String.valueOf(latestSellingPrice);
    }

    public boolean productExistsInProductTable(String productName) {
        return productService.productExistsInProductTable(productName);
    }

    public void addProductsToProductTable() {
        List<Record> records = view.getRecords();
        for (Record r : records) {
            if (!productService.productExistsInProductTable(r.getProduct().getName())) {
                try {
                    productService.addProduct(r.getProduct());
                } catch (CouldNotAddProductException e) {
                    System.err.println("Unable to add product " + r.getProduct().getName());
                    e.printStackTrace();
                }
            } else {
                productService.updateProduct(r.getProduct());
            }
        }
    }

    public void refreshTotals() {
        view.setBuyingPriceTotal(calculateBuyingPriceTotal());
        view.setSellingPriceTotal(calculateSellingPriceTotal());
    }


    private double calculateBuyingPriceTotal() {
        List<Record> recordList = view.getRecords();
        double total = 0;
        for (Record r : recordList) {
            total += r.getProduct().getBuyingPrice() * r.getQuantity();
        }
        return total;
    }

    private double calculateSellingPriceTotal() {
        List<Record> recordList = view.getRecords();
        double total = 0;
        for (Record r : recordList) {
            total += r.getProduct().getSellingPrice() * r.getQuantity();
        }
        return total;
    }

    public void showSearchView(String partOfName) {
        Stage searchStage = new Stage();
        BorderPane searchPane = new BorderPane();
        Scene searchScene = new Scene(searchPane);
        Set<String> allProductNames = productService.getAllProductNames();
        for (Iterator<String> it = allProductNames.iterator(); it.hasNext(); ) {
            String s = it.next();
            if (!s.toLowerCase().contains(partOfName.toLowerCase())) {
                it.remove();
            }
        }
        ObservableList<String> test = FXCollections.observableArrayList(allProductNames);
        ListView<String> listView = new ListView<>(test);
        searchPane.setCenter(listView);
        searchStage.setScene(searchScene);
        searchStage.setTitle(partOfName);
        searchStage.show();

        listView.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                view.setEditProductTextFieldText(listView.getSelectionModel().getSelectedItem());
                view.getEditProductQuantity().requestFocus();
                searchStage.close();
            }
        });
    }
}
