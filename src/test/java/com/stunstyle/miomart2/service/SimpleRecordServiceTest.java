package com.stunstyle.miomart2.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class SimpleRecordServiceTest {
    private static RecordService recordService;
    private static final String TEST_DB_NAME = "RECORD_SERVICE_TEST_DB";

    @BeforeAll
    public static void setUpClass() {
        recordService = SimpleRecordService.getInstance(TEST_DB_NAME);
    }

    @Test
    public void getAllRecordsForDateTest() {
        LocalDate testDate = LocalDate.of(2018, 2, 2);
        Record testRecord1 = new Record(new Product("Test Product #1", 5, 6), 10, testDate);
        Record testRecord2 = new Record(new Product("Test Product #2", 9, 10), 10, testDate);
        Record testRecord3 = new Record(new Product("Test Product #3", 12, 14), 10, testDate);

        recordService.removeAllRecordsFromDate(testDate);

        recordService.addRecordToDate(testRecord1, testDate);
        recordService.addRecordToDate(testRecord2, testDate);
        recordService.addRecordToDate(testRecord3, testDate);

        List<Record> records = recordService.getAllRecordsForDate(testDate);
        Assertions.assertTrue(records.contains(testRecord1), "Should contain test #1");
        Assertions.assertTrue(records.contains(testRecord2), "Should contain test #2");
        Assertions.assertTrue(records.contains(testRecord3), "Should contain test #3");

        Assertions.assertTrue( records.size() == 3, "Should only contain 3 records");
    }

    @Test
    public void addRecordToDateTest() {
        LocalDate testDate = LocalDate.of(2018,1,1);
        Record testRecord1 = new Record(new Product("Прясно мляко Верея 3%", 1.22, 1.49), 10, testDate);
        Record testRecord2 = new Record(new Product("Кисело мляко Верея 2,9%", 0.90, 1), 12, testDate);
        recordService.addRecordToDate(testRecord1, testDate);
        recordService.addRecordToDate(testRecord2, testDate);

        List<Record> records = recordService.getAllRecordsForDate(testDate);

        Assertions.assertTrue(records.contains(testRecord1), "List should contain the first record we added");
        Assertions.assertTrue(records.contains(testRecord2), "List should contain the second record we added");

        recordService.removeAllRecordsFromDate(testDate);
    }

    @Test
    public void removeRecordFromDateTest() {
        LocalDate testDate = LocalDate.of(2014, 1, 1);
        Record testRecord1 = new Record(new Product("Test Product #1", 5, 6), 10, testDate);
        Record testRecord2 = new Record(new Product("Test Product #2", 9, 10), 10, testDate);
        Record testRecord3 = new Record(new Product("Test Product #3", 12, 14), 10, testDate);

        recordService.addRecordToDate(testRecord1, testDate);
        recordService.addRecordToDate(testRecord2, testDate);
        recordService.addRecordToDate(testRecord3, testDate);

        List<Record> records = recordService.getAllRecordsForDate(testDate);
        Assertions.assertTrue(records.size() == 3, "There should be 3 records for this test date");

        recordService.removeRecordFromDate(testRecord2, testDate);
        records = recordService.getAllRecordsForDate(testDate);
        Assertions.assertTrue(records.size() == 2, "There should now be 2 records for this test date");
        Assertions.assertFalse(records.contains(testRecord2), "Record2 should not be present for this date at the moment");
    }

    @Test
    public void removeAllRecordsFromDateTest() {
        LocalDate testDate = LocalDate.of(2012, 1, 1);
        Record testRecord1 = new Record(new Product("Test Product #1", 5, 6), 10, testDate);
        Record testRecord2 = new Record(new Product("Test Product #2", 9, 10), 10, testDate);
        Record testRecord3 = new Record(new Product("Test Product #3", 12, 14), 10, testDate);

        recordService.addRecordToDate(testRecord1, testDate);
        recordService.addRecordToDate(testRecord2, testDate);
        recordService.addRecordToDate(testRecord3, testDate);

        List<Record> records = recordService.getAllRecordsForDate(testDate);
        Assertions.assertTrue(records.size() == 3, "There should now be 3 records for this test date");

        recordService.removeAllRecordsFromDate(testDate);
        records = recordService.getAllRecordsForDate(testDate);
        Assertions.assertTrue(records.size() == 0, "There should now be no records for this test date");
    }


    @Test
    public void addRecordsToDateTest() {
        LocalDate testDate = LocalDate.of(2010, 1, 1);
        Record testRecord1 = new Record(new Product("Test Product #1", 5, 6), 10, testDate);
        Record testRecord2 = new Record(new Product("Test Product #2", 9, 10), 10, testDate);
        Record testRecord3 = new Record(new Product("Test Product #3", 12, 14), 10, testDate);
        List<Record> testRecords = new ArrayList<>();
        testRecords.add(testRecord1);
        testRecords.add(testRecord2);
        testRecords.add(testRecord3);

        List<Record> records = recordService.getAllRecordsForDate(testDate);
        Assertions.assertTrue(records.size() == 0, "There should be 0 records for this test date");

        recordService.addRecordsToDate(testRecords, testDate);
        records = recordService.getAllRecordsForDate(testDate);
        Assertions.assertTrue(records.size() == 3, "There should now be 3 records for this test date");

    }

    // TODO: fix getLatestPrice methods and test them
    @AfterAll
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