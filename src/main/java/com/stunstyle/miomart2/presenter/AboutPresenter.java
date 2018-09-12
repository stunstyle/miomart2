package com.stunstyle.miomart2.presenter;

import com.stunstyle.miomart2.view.AboutView;

public class AboutPresenter {
    private AboutView view;
    private MainPresenter mainPresenter;


    public AboutPresenter(AboutView view, MainPresenter mainPresenter) {
        this.view = view;
        this.mainPresenter = mainPresenter;
        this.view.setPresenter(this);
    }
}
