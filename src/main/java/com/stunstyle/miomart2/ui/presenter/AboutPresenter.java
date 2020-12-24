package com.stunstyle.miomart2.ui.presenter;

import com.stunstyle.miomart2.ui.view.AboutView;

public class AboutPresenter {
    private AboutView view;
    private MainPresenter mainPresenter;


    public AboutPresenter(AboutView view, MainPresenter mainPresenter) {
        this.view = view;
        this.mainPresenter = mainPresenter;
        this.view.setPresenter(this);
    }
}
