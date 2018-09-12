package com.stunstyle.miomart2.presenter;

import com.stunstyle.miomart2.view.PickDateView;

public class PickDatePresenter {
    private PickDateView view;
    private MainPresenter mainPresenter;

    public PickDatePresenter(PickDateView view, MainPresenter mainPresenter) {
        this.view = view;
        this.mainPresenter = mainPresenter;
        this.view.setPresenter(this);
    }

    public PickDateView getView() {
        return view;
    }


    public void showEditRecord() {
        mainPresenter.showEditRecord(view.getDatePicked());
    }
}
