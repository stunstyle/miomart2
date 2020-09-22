package com.stunstyle.miomart2.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.controlsfx.control.textfield.TextFields;

import com.stunstyle.miomart2.presenter.EditRecordPresenter;
import com.stunstyle.miomart2.service.Product;
import com.stunstyle.miomart2.service.Record;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;

public class EditRecordView extends GridPane {
    private EditRecordPresenter presenter;
    private ObservableList<Record> records = FXCollections.<Record>observableArrayList();
    private Text editTitleText;
    private Text buyingPriceTotal;
    private Text sellingPriceTotal;
    private TextField editProduct;
    private TextField editProductQuantity;

    public EditRecordView() {
        buildView();
    }

    public ObservableList<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records.clear();
        this.records.addAll(records);
    }

    public void setPresenter(EditRecordPresenter presenter) {
        this.presenter = presenter;
    }

    public void setEditProductTextFieldText(String s) {
        this.editProduct.setText(s);
    }

    public TextField getEditProductQuantity() {
        return editProductQuantity;
    }

    private void buildView() {
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));

        editTitleText = new Text("");//date.toString());
        editTitleText.setFont(Font.font("Arial", FontWeight.BOLD, 35));
        this.add(editTitleText, 0, 0);

        Label editProductNameLabel = new Label("Продукт");
        this.add(editProductNameLabel, 0, 1);

        Label editProductQuantityLabel = new Label("Количество");
        this.add(editProductQuantityLabel, 1, 1);

        Label editProductBuyingPriceLabel = new Label("Покупна цена");
        this.add(editProductBuyingPriceLabel, 2, 1);

        Label editProductSellingPriceLabel = new Label("Продажна цена");
        this.add(editProductSellingPriceLabel, 3, 1);

        editProduct = new TextField();
        this.add(editProduct, 0, 2);

        editProduct.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
                    if (keyEvent.getCode() == KeyCode.F4) {
                        presenter.showSearchView(editProduct.getText());
                    }
                }
        );

        /*
        ChangeListener<Boolean> autoCompleteListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                ObservableSet<String> productNames = presenter.getAllProductNames();
                TextFields.bindAutoCompletion(editProduct, productNames);
            }
        };*/

        editProduct.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                editProduct.focusedProperty().removeListener(this);
                ObservableSet<String> productNames = presenter.getAllProductNames();
                TextFields.bindAutoCompletion(editProduct, productNames);
            }
        });


        editProductQuantity = new TextField();
        this.add(editProductQuantity, 1, 2);

        TextField editProductBuyingPrice = new TextField();
        this.add(editProductBuyingPrice, 2, 2);
        editProductBuyingPrice.focusedProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (t1) {
                editProductBuyingPrice.setText(presenter.getLatestBuyingPrice(editProduct.getText()));
            }
            ;
        });

        TextField editProductSellingPrice = new TextField();
        this.add(editProductSellingPrice, 3, 2);
        editProductSellingPrice.focusedProperty().addListener((observableValue, aBoolean, t1) ->
        {
            if (t1) {
                editProductSellingPrice.setText(presenter.getLatestSellingPrice(editProduct.getText()));
            }
        });

        Text editProductSmallTotalText = new Text("   ");
        this.add(editProductSmallTotalText, 4, 2);

        Button editProductAddBtn = new Button("Добави");
        this.add(editProductAddBtn, 5, 2);

        // TODO: create util class for table creation

        TableView<Record> table = new TableView<>(records);
        table.setTableMenuButtonVisible(true);
        table.setEditable(true);
        TableColumn<Record, String> productNameCol = new TableColumn<>("Име");
        productNameCol.setCellValueFactory(cellData -> {
            Record r = cellData.getValue();
            Product p = r.getProduct();
            return p.nameProperty();
        });

        TableColumn<Record, Integer> productQuantityCol = new TableColumn<>("К-во");
        productQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        productQuantityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); // that's how it's done for integer cols
        productQuantityCol.setOnEditCommit(recordIntegerCellEditEvent -> {
            recordIntegerCellEditEvent.getTableView()
                    .getItems()
                    .get(recordIntegerCellEditEvent.getTablePosition().getRow())
                    .setQuantity(recordIntegerCellEditEvent.getNewValue());
            presenter.refreshTotals();
        });

        TableColumn<Record, Number> productBuyingPriceCol = new TableColumn<>("Покупна цена");
        productBuyingPriceCol.setCellValueFactory(cellData -> {
            Record r = cellData.getValue();
            Product p = r.getProduct();
            return p.buyingPriceProperty();
        });

        TableColumn<Record, Number> productSellingPriceCol = new TableColumn<>("Продажна цена");
        productSellingPriceCol.setCellValueFactory(cellData -> {
            Record r = cellData.getValue();
            Product p = r.getProduct();
            return p.sellingPriceProperty();
        });

        TableColumn<Record, Number> productSmallTotalCol = new TableColumn<>("К-во x Продажна цена");
        productSmallTotalCol.setCellValueFactory(cellData -> {
            Record r = cellData.getValue();
            return Bindings.createDoubleBinding(
                    () -> {
                        double sellingPrice = r.getProduct().getSellingPrice();
                        int quantity = r.getQuantity();
                        double total = sellingPrice * quantity;
                        BigDecimal roundedTotal = new BigDecimal(total);
                        roundedTotal = roundedTotal.setScale(2, RoundingMode.HALF_UP);
                        return roundedTotal.doubleValue();
                    }
            );
        });


        table.getColumns().addAll(productNameCol, productQuantityCol, productBuyingPriceCol, productSellingPriceCol, productSmallTotalCol);

        this.add(table, 0, 6);

        editProductAddBtn.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                editProductAddBtn.fire();
                keyEvent.consume();
                editProduct.requestFocus();
            }
        });

        editProductAddBtn.setOnAction(actionEvent -> {
                    String productName = editProduct.getText();
                    Double productBuyingPrice = Double.parseDouble(editProductBuyingPrice.getText().replaceAll(",", "."));
                    Double productSellingPrice = Double.parseDouble(editProductSellingPrice.getText().replaceAll(",", "."));
                    Integer productQuantity = Integer.parseInt(editProductQuantity.getText().replaceAll(",", "."));
                    Record currentRecord = new Record(new Product(productName,
                            productBuyingPrice, productSellingPrice), productQuantity, presenter.getDateOfRecord());
                    if (!presenter.productExistsInProductTable(editProduct.getText())) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        Text dialogText = new Text("Product " + editProduct.getText() + " not found in DB. Do you want to add it?");
                        alert.getDialogPane().setContent(dialogText);
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            records.add(currentRecord);
                        } else {
                            return;
                        }
                    } else {
                        records.add(currentRecord);
                    }
                    editProduct.clear();
                    editProductQuantity.clear();
                    editProductBuyingPrice.clear();
                    editProductSellingPrice.clear();
                    presenter.refreshTotals();
                }
        );

        GridPane.setColumnSpan(table, 6);
        table.setPlaceholder(new Label("Все още няма добавени записи за този ден."));

        Label buyingPriceTotalLabel = new Label("A x B");
        this.add(buyingPriceTotalLabel, 4, 7);

        buyingPriceTotal = new Text("0.00");
        this.add(buyingPriceTotal, 4, 8);

        sellingPriceTotal = new Text("0.00");
        this.add(sellingPriceTotal, 5, 8);

        Button refreshBtn = new Button("Обнови");
        this.add(refreshBtn, 4, 9);
        refreshBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setColumnSpan(refreshBtn, 2);
        refreshBtn.setOnAction(actionEvent -> presenter.refreshTotals());

        Button deleteBtn = new Button("Изтрий");
        this.add(deleteBtn, 4, 10);
        deleteBtn.setOnAction(actionEvent -> {
            Record selectedRecord = table.getSelectionModel().getSelectedItem();
            records.remove(selectedRecord);
        });

        setGlobalEventHandler(this, deleteBtn);

        Button saveBtn = new Button("Запиши");
        this.add(saveBtn, 5, 10);
        saveBtn.setOnAction(actionEvent -> {
            presenter.addProductsToProductTable();
            presenter.saveRecords(records);
        });
    }

    public void setEditTitleText(String titleText) {
        this.editTitleText.setText(titleText);
    }

    private void setGlobalEventHandler(Node root, Button btn) {
        root.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.DELETE) {
                btn.fire();
                ev.consume();
            }
        });
    }

    public void setBuyingPriceTotal(double buyingPriceTotal) {
        this.buyingPriceTotal.setText(String.valueOf(Math.floor(buyingPriceTotal * 100) / 100));
    }

    public void setSellingPriceTotal(double sellingPriceTotal) {
        this.sellingPriceTotal.setText(String.valueOf(Math.floor(sellingPriceTotal * 100) / 100));
    }
}
