package com.stunstyle.miomart2.service;

import java.time.LocalDate;

public class SimpleTableNameConstructor implements TableNameConstructor {

    @Override
    public String constructTableNameFromDate(LocalDate date) {
        String month = date.getMonth().toString();
        int year = date.getYear();

        return month.toUpperCase() + "_" + year;
    }
}
