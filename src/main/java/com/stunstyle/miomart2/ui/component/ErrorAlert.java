package com.stunstyle.miomart2.ui.component;

public class ErrorAlert extends MMGenericAlert {
    public ErrorAlert(String message, String headerText) {
        super(message, headerText);
        setAlertType(AlertType.ERROR);
    }
}
