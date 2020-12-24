package com.stunstyle.miomart2.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.stunstyle.miomart2.db.DAO;
import com.stunstyle.miomart2.db.DerbyDAO;
import com.stunstyle.miomart2.ui.util.SimpleTableNameConstructor;
import com.stunstyle.miomart2.ui.util.TableNameConstructor;

public class SimpleRecordService implements RecordService {
    private DAO dao;
    private TableNameConstructor tableNameConstructor = new SimpleTableNameConstructor();

    public static SimpleRecordService getInstance() {
        return getInstance("");
    }

    public static SimpleRecordService getInstance(String dbName) {
        SimpleRecordService service = new SimpleRecordService();
        if (dbName.isEmpty()) {
            service.dao = new DerbyDAO();
        } else {
            service.dao = new DerbyDAO(dbName);
        }
        try {
            service.dao.setupTables();
        } catch (SQLException e) {
            System.err.println("Could not retrieve instance of SimpleRecordService!");
            e.printStackTrace();
        }

        return service;
    }

    @Override
    public List<Record> getAllRecordsForDate(LocalDate date) {
        String tableNameForDate = tableNameConstructor.constructTableNameFromDate(date);
        List<Record> records = new ArrayList<>();
        try (Connection conn = dao.getConnection()) {
            if (!dao.tableExists(tableNameForDate)) {
                dao.createRecordTable(tableNameForDate);
            }
            PreparedStatement ps_select = conn.prepareStatement("SELECT * FROM " + tableNameForDate + " WHERE DATE = ?");
            ps_select.setDate(1, Date.valueOf(date));
            ResultSet rs = ps_select.executeQuery();
            while (rs.next()) {
                records.add(new Record(new Product(rs.getString(1), rs.getDouble(2), rs.getDouble(3)), rs.getInt(4), date));
            }

        } catch (SQLException e) {
            System.err.println("Error while getting all records for a date!");
            e.printStackTrace();
        }
        return records;
    }

    @Override
    public void addRecordToDate(Record record, LocalDate date) {
        String tableNameForDate = tableNameConstructor.constructTableNameFromDate(date);
        if (!dao.tableExists(tableNameForDate)) {
            dao.createRecordTable(tableNameForDate);
        }
        try (Connection conn = dao.getConnection()) {
            PreparedStatement ps_insert = conn.prepareStatement("INSERT INTO " + tableNameForDate + " VALUES(?, ?, ?, ?, ?)");
            ps_insert.setString(1, record.getProduct().getName());
            ps_insert.setDouble(2, record.getProduct().getBuyingPrice());
            ps_insert.setDouble(3, record.getProduct().getSellingPrice());
            ps_insert.setInt(4, record.getQuantity());
            ps_insert.setDate(5, Date.valueOf(date));
            ps_insert.execute();
        } catch (SQLException e) {
            System.err.println("Error while adding record!");
            e.printStackTrace();
        }
    }

    @Override
    public void removeRecordFromDate(Record record, LocalDate date) {
        String tableNameForDate = tableNameConstructor.constructTableNameFromDate(date);
        try (Connection conn = dao.getConnection()) {
            PreparedStatement ps_delete = conn.prepareStatement("DELETE FROM " + tableNameForDate + " WHERE NAME = ? AND DATE = ?");
            ps_delete.setString(1, record.getProduct().getName());
            ps_delete.setDate(2, Date.valueOf(date));
            ps_delete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAllRecordsFromDate(LocalDate date) {
        String tableNameForDate = tableNameConstructor.constructTableNameFromDate(date);
        if (!dao.tableExists(tableNameForDate)) {
            dao.createRecordTable(tableNameForDate);
        }
        try (Connection conn = dao.getConnection()) {
            PreparedStatement ps_delete = conn.prepareStatement("DELETE FROM " + tableNameForDate + " WHERE DATE = ?");
            ps_delete.setDate(1, Date.valueOf(date));
            ps_delete.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addRecordsToDate(List<Record> records, LocalDate date) {
        for (Record r : records) {
            addRecordToDate(r, date);
        }
    }


    @Override
    public double getLatestBuyingPrice(String productName) {
        try (Connection conn = dao.getConnection()) {
            PreparedStatement ps_select = conn.prepareStatement("SELECT BuyingPrice FROM PRODUCT WHERE NAME = ?");
            ps_select.setString(1, productName);
            ResultSet rs = ps_select.executeQuery();
            if (rs.next()) {
                return rs.getDouble("BuyingPrice");
            }
        } catch (SQLException e) {
            System.err.println("Unable to get product " + productName + "'s buying price");
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public double getLatestSellingPrice(String productName) {
        try (Connection conn = dao.getConnection()) {
            PreparedStatement ps_select = conn.prepareStatement("SELECT SellingPrice FROM PRODUCT WHERE NAME = ?");
            ps_select.setString(1, productName);
            ResultSet rs = ps_select.executeQuery();
            if (rs.next()) {
                return rs.getDouble("SellingPrice");
            }
        } catch (SQLException e) {
            System.err.println("Unable to get product " + productName + "'s selling price");
            e.printStackTrace();
        }
        return -1;
    }


}
