package com.stunstyle.miomart2.ui.presenter;

import java.time.LocalDate;

import com.stunstyle.miomart2.ui.view.AboutView;
import com.stunstyle.miomart2.ui.view.MainView;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainPresenter {
    private MainView view;
    private AddProductPresenter addProductPresenter;
    private PickDatePresenter pickDatePresenter;
    private EditRecordPresenter editRecordPresenter;
    private AboutPresenter aboutPresenter;
    private CreateReportPresenter createReportPresenter;

    private Logger logger = LogManager.getLogger(MainPresenter.class);

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

    public void setCreateReportPresenter(CreateReportPresenter createReportPresenter) {
        this.createReportPresenter = createReportPresenter;
    }

    public MainView getView() {
        return view;
    }

    public void setView(MainView view) {
        this.view = view;
    }

    public void showAddProduct() {
        logger.info("Showing AddProductView");
        view.setContent(addProductPresenter.getView());
    }

    public void showPickDate() {
        view.setContent(pickDatePresenter.getView());
    }

    public void showAbout() {
        Stage aboutStage = new Stage();
        aboutStage.setTitle("За miomart2");
        logger.info("Showing about view");
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

    public void showReference() {
        view.setContent(createReportPresenter.getView());
    }
}
