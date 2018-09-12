package com.stunstyle.miomart2.presenter;

import com.stunstyle.miomart2.view.AboutView;
import com.stunstyle.miomart2.view.MainView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;

public class MainPresenter {
    private MainView view;
    private AddProductPresenter addProductPresenter;
    private PickDatePresenter pickDatePresenter;
    private EditRecordPresenter editRecordPresenter;
    private AboutPresenter aboutPresenter;

    public MainPresenter(MainView view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    public void setAddProductPresenter(AddProductPresenter addProductPresenter) {
        this.addProductPresenter = addProductPresenter;
    }

    public void setPickDatePresenter(PickDatePresenter pickDatePresenter) {
        this.pickDatePresenter = pickDatePresenter;
    }

    public void setAboutPresenter(AboutPresenter aboutPresenter) {
        this.aboutPresenter = aboutPresenter;
    }

    public void setEditRecordPresenter(EditRecordPresenter editRecordPresenter) {
        this.editRecordPresenter = editRecordPresenter;
    }

    public MainView getView() {
        return view;
    }

    public void setView(MainView view) {
        this.view = view;
    }

    public void showAddProduct() {
        view.setContent(addProductPresenter.getView());
    }

    public void showPickDate() {
        view.setContent(pickDatePresenter.getView());
    }

    public void showAbout() {
        Stage aboutStage = new Stage();
        aboutStage.setTitle("За miomart2");

        Scene aboutScene = new Scene(new AboutView(), 480, 272);
        aboutStage.setScene(aboutScene);
        aboutStage.showAndWait();
    }

    public void showEditRecord(LocalDate date) {
        editRecordPresenter.setDateOfRecord(date);
        editRecordPresenter.updateTitleText();
        editRecordPresenter.updateRecords();
        editRecordPresenter.refreshTotals();
        view.setContent(editRecordPresenter.getView());
    }
}
