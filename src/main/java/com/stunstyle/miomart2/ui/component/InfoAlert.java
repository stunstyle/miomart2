package com.stunstyle.miomart2.ui.component;

import javafx.scene.control.Alert;

public class InfoAlert extends Alert {
    public InfoAlert(String title, String content) {
        super(AlertType.INFORMATION);
        setTitle(title);
        setContentText(content);
    }
}
