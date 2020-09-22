package com.stunstyle.miomart2.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.stunstyle.miomart2.db.DerbyDAO;
import com.stunstyle.miomart2.exception.CouldNotAddProductException;

public class SimpleProductService implements ProductService {
    private DerbyDAO dao;

    private SimpleProductService() {

    }

    public static SimpleProductService getInstance() {
        return getInstance("");
    }

    public static SimpleProductService getInstance(String dbName) {
        SimpleProductService service = new SimpleProductService();
        if (dbName.isEmpty()) {
            service.dao = new DerbyDAO();
        } else {
            service.dao = new DerbyDAO(dbName);
        }
        try {
            service.dao.setupTables();
        } catch (SQLException e) {
            System.err.println("Could not retrieve instance of SimpleProductService!");
            e.printStackTrace();
        }

        return service;
    }

    @Override
    public void addProduct(Product product) throws CouldNotAddProductException {
        try (Connection conn = dao.getConnection()) {
            String query = "INSERT INTO PRODUCT VALUES(?,?,?)";
            try (PreparedStatement ps_insert = conn.prepareStatement(query)) {
                ps_insert.setString(1, product.getName());
                ps_insert.setDouble(2, product.getBuyingPrice());
                ps_insert.setDouble(3, product.getSellingPrice());
                ps_insert.executeUpdate();
            }
        } catch (SQLException e) {
            throw new CouldNotAddProductException("Could not add product to DB!");
        }
    }

    @Override
    public void updateProduct(Product product) {
        try (Connection conn = dao.getConnection()) {
            String query = "UPDATE PRODUCT SET BuyingPrice = ?, SellingPrice = ? WHERE Name = ?";
            try (PreparedStatement ps_update = conn.prepareStatement(query)) {
                ps_update.setDouble(1, product.getBuyingPrice());
                ps_update.setDouble(2, product.getSellingPrice());
                ps_update.setString(3, product.getName());
                ps_update.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean removeProduct(String productName) {
        PreparedStatement psDelete = null;
        try (Connection conn = dao.getConnection()) {
            psDelete = conn.prepareStatement("DELETE FROM PRODUCT WHERE NAME=?");
            psDelete.setString(1, productName);

            int rowsDeleted = psDelete.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println("Error while deleting " + productName + "!");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        ResultSet rs = null;
        try (Connection conn = dao.getConnection()) {
            Statement s = conn.createStatement();
            rs = s.executeQuery("SELECT * FROM PRODUCT");
            while (rs.next()) {
                Product currentProduct = new Product(rs.getString("Name"), rs.getDouble("BuyingPrice"), rs.getDouble("SellingPrice"));
                products.add(currentProduct);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Set<String> getAllProductNames() {
        Set<String> products = new HashSet<>();
        ResultSet rs = null;
        try (Connection conn = dao.getConnection()) {
            Statement s = conn.createStatement();
            rs = s.executeQuery("SELECT NAME FROM PRODUCT");
            while (rs.next()) {
                products.add(rs.getString("Name"));
            }
            rs.close();
        } catch (SQLException e1) {
            System.err.println("Unable to return product names!");
            e1.printStackTrace();
        }
        return products;
    }

    @Override
    public boolean productExistsInProductTable(String productName) {
        String query = "SELECT * FROM PRODUCT WHERE NAME = ?";
        try (Connection conn = dao.getConnection()) {
            PreparedStatement ps_select = conn.prepareStatement(query);
            ps_select.setString(1, productName);
            ResultSet rs = ps_select.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
