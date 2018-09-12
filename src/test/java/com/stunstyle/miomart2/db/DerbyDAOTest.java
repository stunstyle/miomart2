package com.stunstyle.miomart2.db;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;

import static org.junit.Assert.*;

public class DerbyDAOTest {
    private static final String TEST_DB_NAME = "DAO_TEST_DB";
    private static DAO dao;

    @BeforeClass
    public static void setUpClass() {
        dao = new DerbyDAO(TEST_DB_NAME);
    }

    @Test
    public void getConnectionTest() {
        try {
            Connection conn = dao.getConnection();
            assertNotNull("Connection should not be null!", conn);
        } catch (SQLException e) {
            System.err.println("Exception while testing getConnection()!");
            e.printStackTrace();
        }
    }

    @Test
    public void setupTablesTest() {
        try {
            dao.setupTables();
            assertTrue(dao.tableExists("PRODUCT"));
        } catch (SQLException e) {
            System.err.println("Error while testing setupTables!");
            e.printStackTrace();
        }
    }

    @Test
    public void tableExistsTest() {
        try {
            dao.getConnection().prepareStatement("CREATE TABLE TEST_TABLE ( Test VARCHAR(50) )").execute();
            assertTrue("Table TEST_TABLE should exist!", dao.tableExists("TEST_TABLE"));
        } catch (SQLException e) {
            System.err.println("Error while testing tableExists!");
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDownClass() {
        try {
            Path dbFolder = Paths.get(TEST_DB_NAME);
            Files.walk(dbFolder)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    //.peek(System.out::println)
                    .forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}