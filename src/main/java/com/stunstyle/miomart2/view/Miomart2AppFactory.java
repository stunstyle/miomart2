package com.stunstyle.miomart2.view;

import com.stunstyle.miomart2.presenter.AboutPresenter;
import com.stunstyle.miomart2.presenter.AddProductPresenter;
import com.stunstyle.miomart2.presenter.CreateReportPresenter;
import com.stunstyle.miomart2.presenter.EditRecordPresenter;
import com.stunstyle.miomart2.presenter.MainPresenter;
import com.stunstyle.miomart2.presenter.PickDatePresenter;
import com.stunstyle.miomart2.service.ProductService;
import com.stunstyle.miomart2.service.RecordService;
import com.stunstyle.miomart2.service.SimpleProductService;
import com.stunstyle.miomart2.service.SimpleRecordService;

public class Miomart2AppFactory {
    private MainPresenter mainPresenter;
    private AddProductPresenter addProductPresenter;
    private PickDatePresenter pickDatePresenter;
    private EditRecordPresenter editRecordPresenter;
    private AboutPresenter aboutPresenter;
    private CreateReportPresenter createReportPresenter;

    private ProductService productService;
    private RecordService recordService;


    public MainPresenter getMainPresenter() {
        if (mainPresenter == null) {
            MainView view = new MainView();
            mainPresenter = new MainPresenter(view);
            mainPresenter.setAddProductPresenter(getAddProductPresenter());
            mainPresenter.setPickDatePresenter(getPickDatePresenter());
            mainPresenter.setEditRecordPresenter(getEditRecordPresenter());
            mainPresenter.setAboutPresenter(getAboutPresenter());
            mainPresenter.setCreateReportPresenter(getCreateReportPresenter());
        }
        return mainPresenter;
    }

    private PickDatePresenter getPickDatePresenter() {
        if (pickDatePresenter == null) {
            PickDateView view = new PickDateView();
            pickDatePresenter = new PickDatePresenter(view, getMainPresenter());
        }
        return pickDatePresenter;
    }

    private AddProductPresenter getAddProductPresenter() {
        if (addProductPresenter == null) {
            AddProductView view = new AddProductView();
            addProductPresenter = new AddProductPresenter(view, getMainPresenter(), getProductService());
        }
        return addProductPresenter;
    }

    private EditRecordPresenter getEditRecordPresenter() {
        if (editRecordPresenter == null) {
            EditRecordView view = new EditRecordView();
            editRecordPresenter = new EditRecordPresenter(view, mainPresenter, getRecordService(), getProductService());
            view.setPresenter(editRecordPresenter);
        }
        return editRecordPresenter;
    }

    private RecordService getRecordService() {
        if (recordService == null) {
            recordService = SimpleRecordService.getInstance();
        }
        return recordService;
    }

    private AboutPresenter getAboutPresenter() {
        if (aboutPresenter == null) {
            AboutView view = new AboutView();
            aboutPresenter = new AboutPresenter(view, mainPresenter);
        }
        return aboutPresenter;
    }

    private ProductService getProductService() {
        if (productService == null) {
            productService = SimpleProductService.getInstance();
        }
        return productService;
    }

    private CreateReportPresenter getCreateReportPresenter() {
        if(createReportPresenter == null){
            CreateReportView view = new CreateReportView();
            createReportPresenter = new CreateReportPresenter(view, mainPresenter, recordService, productService);
            view.setPresenter(createReportPresenter);
        }
        return createReportPresenter;
    }
}
