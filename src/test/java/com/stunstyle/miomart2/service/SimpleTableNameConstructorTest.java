package com.stunstyle.miomart2.service;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.*;

public class SimpleTableNameConstructorTest {

    private TableNameConstructor constructor = new SimpleTableNameConstructor();

    @Test
    public void constructTableNameFromStringTest() {
        LocalDate testDate_November_2017 = LocalDate.of(2017, Month.NOVEMBER, 1);
        LocalDate testDate_December_1999 = LocalDate.of(1999, Month.DECEMBER, 15);
        assertTrue("Should be NOVEMBER_2017", constructor.constructTableNameFromDate(testDate_November_2017).equals("NOVEMBER_2017"));
        assertTrue("Should be DECEMBER_1999", constructor.constructTableNameFromDate(testDate_December_1999).equals("DECEMBER_1999"));

    }

}