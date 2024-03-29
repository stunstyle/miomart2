package com.stunstyle.miomart2.ui.presenter;

import com.stunstyle.miomart2.exception.CouldNotAddProductException;
import com.stunstyle.miomart2.service.Product;
import com.stunstyle.miomart2.service.ProductService;
import com.stunstyle.miomart2.service.ProductTableUtil;
import com.stunstyle.miomart2.ui.component.ErrorAlert;
import com.stunstyle.miomart2.ui.component.InfoAlert;
import com.stunstyle.miomart2.ui.view.AddProductView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddProductPresenter {
    private AddProductView view;
    private MainPresenter mainPresenter;
    private ProductService productService;
    private Logger logger = LogManager.getLogger(AddProductPresenter.class);

    public AddProductPresenter(AddProductView view, MainPresenter mainPresenter, ProductService productService) {
        this.view = view;
        this.mainPresenter = mainPresenter;
        this.productService = productService;
        this.view.setPresenter(this);
    }

    public AddProductView getView() {
        return view;
    }


    public void addProduct() {
        try {
            Product toAdd = new Product(view.getProductToAddName(), view.getProductToAddBuyingPrice(), view.getProductToAddSellingPrice());
            productService.addProduct(toAdd);
            Alert successAlert = new InfoAlert("Информация", String.format("Успешно добавяне на %s с цени %s, %s", toAdd.getName(), String.valueOf(toAdd.getBuyingPrice()), String.valueOf(toAdd.getSellingPrice())));
            successAlert.showAndWait();
        } catch (Exception e) {
            logger.warn(e);
            Alert errorAlert = new ErrorAlert("Грешка", String.format("%s: %s", e.getMessage(), e.getCause() == null ? "" : e.getCause().getMessage()));
            errorAlert.showAndWait();
        }
    }

    public void deleteProduct() {
        String productToDeleteName = view.getProductToDeleteName();
        boolean productDeleted = productService.removeProduct(productToDeleteName);
        if (productDeleted) {
            view.setProductDeletedStatus("Успешно изтриване");
        } else {
            view.setProductDeletedStatus("Неуспешно изтриване");
        }
    }


    public void showAllProducts() {
        TableView<Product> table = new TableView<>(ProductTableUtil.getProductList());
        table.getColumns().addAll(ProductTableUtil.getNameColumn(),
                ProductTableUtil.getBuyingPriceColumn(),
                ProductTableUtil.getSellingPriceColumn());
        view.setAllProductsTable(table);
    }

    public ObservableSet<String> getAllProductNames() {
        ObservableSet<String> allProductNames = FXCollections.observableSet(productService.getAllProductNames());
        return allProductNames;
    }

}
