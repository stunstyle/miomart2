package com.stunstyle.miomart2.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class SimpleRecordServiceTest {
    private static RecordService recordService;
    private static final String TEST_DB_NAME = "RECORD_SERVICE_TEST_DB";

    @BeforeClass
    public static void setUpClass() {
        recordService = SimpleRecordService.getInstance(TEST_DB_NAME);
    }

    @Test
    public void getAllRecordsForDateTest() {
        LocalDate testDate = LocalDate.of(2018, 2, 2);
        Record testRecord1 = new Record(new Product("Test Product #1", 5, 6), 10);
        Record testRecord2 = new Record(new Product("Test Product #2", 9, 10), 10);
        Record testRecord3 = new Record(new Product("Test Product #3", 12, 14), 10);

        recordService.removeAllRecordsFromDate(testDate);

        recordService.addRecordToDate(testRecord1, testDate);
        recordService.addRecordToDate(testRecord2, testDate);
        recordService.addRecordToDate(testRecord3, testDate);

        List<Record> records = recordService.getAllRecordsForDate(testDate);
        assertTrue("Should contain test #1", records.contains(testRecord1));
        assertTrue("Should contain test #2", records.contains(testRecord2));
        assertTrue("Should contain test #3", records.contains(testRecord3));

        assertTrue("Should only contain 3 records", records.size() == 3);
    }

    @Test
    public void addRecordToDateTest() {
        LocalDate testDate = LocalDate.of(2018,1,1);
        Record testRecord1 = new Record(new Product("Прясно мляко Верея 3%", 1.22, 1.49), 10);
        Record testRecord2 = new Record(new Product("Кисело мляко Верея 2,9%", 0.90, 1), 12);
        recordService.addRecordToDate(testRecord1, testDate);
        recordService.addRecordToDate(testRecord2, testDate);

        List<Record> records = recordService.getAllRecordsForDate(testDate);

        assertTrue("List should contain the first record we added", records.contains(testRecord1));
        assertTrue("List should contain the second record we added", records.contains(testRecord2));

        recordService.removeAllRecordsFromDate(testDate);
    }

    @Test
    public void removeRecordFromDateTest() {
        LocalDate testDate = LocalDate.of(2014, 1, 1);
        Record testRecord1 = new Record(new Product("Test Product #1", 5, 6), 10);
        Record testRecord2 = new Record(new Product("Test Product #2", 9, 10), 10);
        Record testRecord3 = new Record(new Product("Test Product #3", 12, 14), 10);

        recordService.addRecordToDate(testRecord1, testDate);
        recordService.addRecordToDate(testRecord2, testDate);
        recordService.addRecordToDate(testRecord3, testDate);

        List<Record> records = recordService.getAllRecordsForDate(testDate);
        assertTrue("There should be 3 records for this test date", records.size() == 3);

        recordService.removeRecordFromDate(testRecord2, testDate);
        records = recordService.getAllRecordsForDate(testDate);
        assertTrue("There should now be 2 records for this test date", records.size() == 2);
        assertFalse("Record2 should not be present for this date at the moment", records.contains(testRecord2));
    }

    @Test
    public void removeAllRecordsFromDateTest() {
        LocalDate testDate = LocalDate.of(2012, 1, 1);
        Record testRecord1 = new Record(new Product("Test Product #1", 5, 6), 10);
        Record testRecord2 = new Record(new Product("Test Product #2", 9, 10), 10);
        Record testRecord3 = new Record(new Product("Test Product #3", 12, 14), 10);

        recordService.addRecordToDate(testRecord1, testDate);
        recordService.addRecordToDate(testRecord2, testDate);
        recordService.addRecordToDate(testRecord3, testDate);

        List<Record> records = recordService.getAllRecordsForDate(testDate);
        assertTrue("There should now be 3 records for this test date", records.size() == 3);

        recordService.removeAllRecordsFromDate(testDate);
        records = recordService.getAllRecordsForDate(testDate);
        assertTrue("There should now be no records for this test date", records.size() == 0);
    }


    @Test
    public void addRecordsToDateTest() {
        Record testRecord1 = new Record(new Product("Test Product #1", 5, 6), 10);
        Record testRecord2 = new Record(new Product("Test Product #2", 9, 10), 10);
        Record testRecord3 = new Record(new Product("Test Product #3", 12, 14), 10);
        List<Record> testRecords = new ArrayList<>();
        testRecords.add(testRecord1);
        testRecords.add(testRecord2);
        testRecords.add(testRecord3);

        LocalDate testDate = LocalDate.of(2010, 1, 1);

        List<Record> records = recordService.getAllRecordsForDate(testDate);
        assertTrue("There should be 0 records for this test date", records.size() == 0);

        recordService.addRecordsToDate(testRecords, testDate);
        records = recordService.getAllRecordsForDate(testDate);
        assertTrue("There should now be 3 records for this test date",records.size() == 3);

    }

    // TODO: fix getLatestPrice methods and test them
    @AfterClass
    public static void tearDownClass() {
        Path dbFolder = Paths.get(TEST_DB_NAME);
        try {
            Files.walk(dbFolder)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    //.peek(System.out::println)
                    .forEach(File::delete);
        } catch (IOException e) {
            System.err.println("Could not delete test database!");
            e.printStackTrace();
        }
    }



}