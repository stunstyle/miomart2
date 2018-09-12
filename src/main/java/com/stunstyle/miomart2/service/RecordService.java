package com.stunstyle.miomart2.service;

import java.time.LocalDate;
import java.util.List;

public interface RecordService {
    List<Record> getAllRecordsForDate(LocalDate date);
    
    void addRecordToDate(Record record, LocalDate date);

    void removeRecordFromDate(Record record, LocalDate date);

    void removeAllRecordsFromDate(LocalDate date);   // better impl?

    void addRecordsToDate(List<Record> records, LocalDate date);

    double getLatestBuyingPrice(String productName);

    double getLatestSellingPrice(String productName);
}
