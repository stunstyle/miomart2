package com.stunstyle.miomart2.presenter;

import com.stunstyle.miomart2.exception.CouldNotAddProductException;
import com.stunstyle.miomart2.service.Product;
import com.stunstyle.miomart2.service.ProductService;
import com.stunstyle.miomart2.service.ProductTableUtil;
import com.stunstyle.miomart2.view.AddProductView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.scene.control.TableView;

public class AddProductPresenter {
    private AddProductView view;
    private MainPresenter mainPresenter;
    private ProductService productService;

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
        Product toAdd = new Product(view.getProductToAddName(), view.getProductToAddBuyingPrice(), view.getProductToAddSellingPrice());
        try {
            productService.addProduct(toAdd);
        } catch (CouldNotAddProductException e) {
            System.err.println("Can't add product!");
            view.setAddResultTextValue("Неуспешно добавяне!");
            e.printStackTrace();
        }
        view.setAddResultTextValue("Успешно добавяне!");
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
