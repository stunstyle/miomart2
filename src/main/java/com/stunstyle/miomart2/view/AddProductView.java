package com.stunstyle.miomart2.view;

import org.controlsfx.control.textfield.TextFields;

import com.stunstyle.miomart2.presenter.AddProductPresenter;
import com.stunstyle.miomart2.service.Product;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableSet;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class AddProductView extends GridPane {
    private AddProductPresenter presenter;
    private TextField productToAddName;
    private TextField productToAddBuyingPrice;
    private TextField productToAddSellingPrice;
    private TextField productToDeleteName;
    private Text productDeletedStatus;
    private TableView<Product> allProductsTable;
    private Text addResultText;

    public AddProductView() {
        buildView();
    }

    public void setAllProductsTable(TableView<Product> allProductsTable) {
        this.allProductsTable = allProductsTable;
    }

    public void setAddResultTextValue(String addResultText) {
        this.addResultText.setText(addResultText);
    }

    public void setPresenter(AddProductPresenter presenter) {
        this.presenter = presenter;
    }

    protected void buildView() {
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));

        Text titleText = new Text("miomart2");
        Font titleFont = Font.font("Arial", FontWeight.BOLD, 40);
        Font subTitleFont = Font.font("Arial", FontWeight.BOLD, 20);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFont(titleFont);
        this.add(titleText, 0, 1);


        Label addProductLabel = new Label("Добавяне на продукт");
        addProductLabel.setFont(subTitleFont);

        this.add(addProductLabel, 0, 2);
        Label productLabel = new Label("Продукт");
        this.add(productLabel, 0, 3);

        productToAddName = new TextField();
        this.add(productToAddName, 0, 4);

        Label buyingPriceLabel = new Label("Покупна цена");
        this.add(buyingPriceLabel, 1, 3);

        productToAddBuyingPrice = new TextField();
        this.add(productToAddBuyingPrice, 1, 4);

        Label sellingPriceLabel = new Label("Продажна цена");
        this.add(sellingPriceLabel, 2, 3);

        productToAddSellingPrice = new TextField();
        this.add(productToAddSellingPrice, 2, 4);

        Button addBtn = new Button("Добави");
        this.add(addBtn, 4, 4);

        setGlobalEventHandler(productToAddSellingPrice, addBtn);

        setGlobalEventHandler(addBtn, addBtn);          // this is so the add button can be fired with Enter while focused
                                                        // without it the user needs to press SPACE
        addBtn.setOnAction(actionEvent -> {
            presenter.addProduct();
            productToAddName.clear();
            productToAddBuyingPrice.clear();
            productToAddSellingPrice.clear();
            productToAddName.requestFocus();
        });

        productToAddName.textProperty().addListener((observableValue, s, t1) -> addResultText.setText(""));
        /* NOT YET SURE IF NECESSARY FOR THIS VIEW
        addBtn.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.TAB) {
                    keyEvent.consume();
                    productToAddName.requestFocus();
                }
            }
        });
        */

        Button showBtn = new Button("Покажи всички записи");
        this.add(showBtn, 0, 5);
        showBtn.setOnAction(actionEvent -> {
            Stage stage = new Stage();
            stage.setTitle("Всички записи");
            presenter.showAllProducts();

            GridPane showProductsRoot = new GridPane();
            showProductsRoot.getChildren().add(allProductsTable);
            allProductsTable.prefHeightProperty().bind(stage.heightProperty());
            allProductsTable.prefWidthProperty().bind(stage.widthProperty());
            Scene newScene = new Scene(showProductsRoot, 500, 400);
            stage.setScene(newScene);

            stage.show();
        });

        setGlobalEventHandler(showBtn, showBtn);        // same as addBtn

        addResultText = new Text("");
        this.add(addResultText, 4, 5);

        Label deleteProductLabel = new Label("Изтриване на продукт");
        deleteProductLabel.setFont(subTitleFont);
        this.add(deleteProductLabel, 0, 6);

        Label productToDeleteLabel = new Label("Продукт");
        this.add(productToDeleteLabel, 0, 7);

        productToDeleteName = new TextField();
        this.add(productToDeleteName, 0, 8);
        productToDeleteName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                productToDeleteName.focusedProperty().removeListener(this);
                ObservableSet<String> productNames = presenter.getAllProductNames();
                TextFields.bindAutoCompletion(productToDeleteName, productNames);
            }
        });

        Button deleteBtn = new Button("Изтрий");
        this.add(deleteBtn, 1, 8);
        setGlobalEventHandler(productToDeleteName, deleteBtn);
        deleteBtn.setOnAction(actionEvent -> {
            presenter.deleteProduct();
            productDeletedStatus.setText("");
        });


        productDeletedStatus = new Text("");
        this.add(productDeletedStatus, 4, 8);
    }

    public String getProductToAddName() {
        return productToAddName.getText();
    }

    public double getProductToAddBuyingPrice() {
        return Double.parseDouble(productToAddBuyingPrice.getText());
    }

    public double getProductToAddSellingPrice() {
        return Double.parseDouble(productToAddSellingPrice.getText());
    }

    public String getProductToDeleteName() {
        return productToDeleteName.getText();
    }

    public void setProductDeletedStatus(String status) {
        productDeletedStatus.setText(status);
    }

    private void setGlobalEventHandler(Node root, Button btn) {
        root.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                btn.fire();
                ev.consume();
            }
        });
    }
}
