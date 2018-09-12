package com.stunstyle.miomart2.service;

import com.stunstyle.miomart2.exception.CouldNotAddProductException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class SimpleProductServiceTest {
    private static ProductService productService;
    private static final String TEST_DB_NAME = "PRODUCT_SERVICE_TEST_DB";


    @BeforeClass
    public static void setUpClass() {
        productService = SimpleProductService.getInstance(TEST_DB_NAME);
    }

    @Test
    public void addProductTest() {
        Product testProduct1 = new Product("Test Product #1", 0.66, 0.99);
        Product testProduct2 = new Product("Test Product #2", 999.999, 10000.00);

        List<Product> products = productService.getAllProducts();
        assertTrue("There should be no products in the test database at the moment", products.size() == 0);
        try {
            productService.addProduct(testProduct1);
            productService.addProduct(testProduct2);
        }
        catch (CouldNotAddProductException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        products = productService.getAllProducts();
        assertTrue("There should be 2 products in the test database at the moment", products.size() == 2);
        assertTrue("Test Product #1 should be in the test database at the moment", products.contains(testProduct1));
        assertTrue("Test Product #2 should be in the test database at the moment", products.contains(testProduct2));
    }

    @Test
    public void removeProductTest() {
        Product testProduct = new Product("Test Product For Removal", 0.23, 0.3);
        try {
            productService.addProduct(testProduct);
        } catch (CouldNotAddProductException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        List<Product> products = productService.getAllProducts();
        assertTrue("Our test product should be present in the database at this moment", products.contains(testProduct));

        productService.removeProduct(testProduct.getName());
        products = productService.getAllProducts();
        assertFalse("Our test product should not be present in the database at this moment", products.contains(testProduct));
    }

    // TODO: write getAllProducts test



    @AfterClass
    public static void tearDownClass() {
        Path dbFolder = Paths.get(TEST_DB_NAME);
        try {
            Files.walk(dbFolder)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}