package com.stunstyle.miomart2.service;

import java.util.List;
import java.util.Set;

import com.stunstyle.miomart2.exception.CouldNotAddProductException;

public interface ProductService {
    void addProduct(Product product) throws CouldNotAddProductException;

    void updateProduct(Product product);

    boolean removeProduct(String productName);

    List<Product> getAllProducts();

    Set<String> getAllProductNames();

    boolean productExistsInProductTable(String productName);
}
