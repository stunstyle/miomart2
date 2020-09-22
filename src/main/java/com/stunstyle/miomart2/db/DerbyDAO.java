package com.stunstyle.miomart2.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DerbyDAO implements DAO {
    private static final String DEFAULT_FRAMEWORK = "embedded";
    private static final String DEFAULT_PROTOCOL = "jdbc:derby:";
    private static final String DEFAULT_DBNAME = "db//miomart2test2";
    private static final String DEFAULT_DRIVERNAME = "org.apache.derby.jdbc.EmbeddedDriver";

    private String framework;
    private String protocol;
    private String dbName;
    private String driverName;

    public DerbyDAO() {
        this.framework = DEFAULT_FRAMEWORK;
        this.protocol = DEFAULT_PROTOCOL;
        this.dbName = DEFAULT_DBNAME;
        this.driverName = DEFAULT_DRIVERNAME;
    }

    public DerbyDAO(String dbName) {
        this();
        this.dbName = dbName;
    }


    public Connection getConnection() throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = DriverManager.getConnection(protocol + dbName + ";create=true");
        return conn;
    }

    public void setupTables() throws SQLException {
        Connection conn = getConnection();
        if (!tableExists("PRODUCT")) {
            String productQuery = "CREATE TABLE PRODUCT ( Name VARCHAR(50) NOT NULL PRIMARY KEY, BuyingPrice DECIMAL(10,3), SellingPrice DECIMAL(10,3))";
            PreparedStatement ps_create = conn.prepareStatement(productQuery);
            ps_create.execute();
        }
    }

    public void createRecordTable(String tableName) {
        try (Connection conn = getConnection()) {
            PreparedStatement ps_create = conn.prepareStatement("CREATE TABLE " + tableName + " ( Name VARCHAR(50) NOT NULL, BuyingPrice DECIMAL(10,3) NOT NULL, SellingPrice DECIMAL(10,3) NOT NULL, Quantity INT NOT NULL, Date DATE NOT NULL, CONSTRAINT PK_RECORDS_" + tableName + " PRIMARY KEY (Name, Date) )");
            ps_create.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean tableExists(String tableName) {
        ResultSet tables = null;
        boolean exists = false;
        try {
            DatabaseMetaData dbm = getConnection().getMetaData();
            tables = dbm.getTables(null, null, tableName, null);
            exists = tables.next();
            tables.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }


}
