package com.stunstyle.miomart2.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface DAO {
    Connection getConnection() throws SQLException;

    void setupTables() throws SQLException;

    void createRecordTable(String tableName);

    boolean tableExists(String tableName);
}
