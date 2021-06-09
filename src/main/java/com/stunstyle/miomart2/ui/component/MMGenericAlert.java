package com.stunstyle.miomart2.ui.component;

import javafx.scene.control.Alert;

public class MMGenericAlert extends Alert {
    public MMGenericAlert(String title, String headerText) {
        super(AlertType.NONE);
        setTitle(title);
        setHeaderText(headerText);
    }
}
