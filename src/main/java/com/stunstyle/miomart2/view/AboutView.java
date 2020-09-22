package com.stunstyle.miomart2.view;

import com.stunstyle.miomart2.presenter.AboutPresenter;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class AboutView extends BorderPane {
    private AboutPresenter presenter;

    public AboutView() {
        buildView();
    }

    private void buildView() {
        setPadding(new Insets(25,25,25,25));
        Text aboutText = new Text("About miomart2.0");
        aboutText.setTextAlignment(TextAlignment.CENTER);
        this.setCenter(aboutText);
    }

    public void setPresenter(AboutPresenter presenter) {
        this.presenter = presenter;
    }
}
