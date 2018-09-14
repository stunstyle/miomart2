package com.stunstyle.miomart2.util;

import com.stunstyle.miomart2.service.Record;
import javafx.scene.control.ListCell;

public class RecordListViewCell extends ListCell<Record> {
    public RecordListViewCell(){

    }

    @Override
    protected void updateItem(Record record, boolean empty){
        super.updateItem(record, empty);

        if(empty || record == null) {
            setText(null);
            setGraphic(null);
        }

        else {
            setText(record.getProduct().getName() + " - " + record.getQuantity() + " на " + record.getDateOfRecord());
        }
    }
}
