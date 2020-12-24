package com.stunstyle.miomart2.service;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.stunstyle.miomart2.ui.util.SimpleTableNameConstructor;
import com.stunstyle.miomart2.ui.util.TableNameConstructor;

public class SimpleTableNameConstructorTest {

    private TableNameConstructor constructor = new SimpleTableNameConstructor();

    @Test
    public void constructTableNameFromStringTest() {
        LocalDate testDate_November_2017 = LocalDate.of(2017, Month.NOVEMBER, 1);
        LocalDate testDate_December_1999 = LocalDate.of(1999, Month.DECEMBER, 15);
        Assertions.assertTrue(constructor.constructTableNameFromDate(testDate_November_2017).equals("NOVEMBER_2017"), "Should be NOVEMBER_2017");
        Assertions.assertTrue(constructor.constructTableNameFromDate(testDate_December_1999).equals("DECEMBER_1999"), "Should be DECEMBER_1999");

    }

}