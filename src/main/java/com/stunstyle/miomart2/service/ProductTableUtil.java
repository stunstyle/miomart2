package com.stunstyle.miomart2.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.util.List;


public class ProductTableUtil {
    private static Connection conn;

    public ProductTableUtil(Connection conn) {
        this.conn = conn;
    }

    public static ObservableList<Product> getProductList() {
        ProductService productService = SimpleProductService.getInstance();
        List<Product> products = productService.getAllProducts();
        return FXCollections.<Product>observableArrayList(products);
    }

    public static TableColumn<Product, String> getNameColumn() {
        TableColumn<Product, String> productNameCol = new TableColumn<>("Име");
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        return productNameCol;
    }

    public static TableColumn<Product, Double> getBuyingPriceColumn() {
        TableColumn<Product, Double> productBuyingPriceCol = new TableColumn<>("Покупна цена");
        productBuyingPriceCol.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        return productBuyingPriceCol;
    }

    public static TableColumn<Product, Double> getSellingPriceColumn() {
        TableColumn<Product, Double> productSellingPriceCol = new TableColumn<>("Продажна цена");
        productSellingPriceCol.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        return productSellingPriceCol;
    }


}
