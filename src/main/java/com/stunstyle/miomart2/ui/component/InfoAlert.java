package com.stunstyle.miomart2.ui.component;

import javafx.scene.control.Alert;

public class InfoAlert extends MMGenericAlert {
    public InfoAlert(String title, String headerText) {
        super(title, headerText);
        setAlertType(AlertType.INFORMATION);
    }
}
