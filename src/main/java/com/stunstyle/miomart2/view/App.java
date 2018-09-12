package com.stunstyle.miomart2.view;

import com.stunstyle.miomart2.presenter.MainPresenter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private int DEFAULT_APP_HEIGHT = 768;
    private int DEFAULT_APP_WIDTH = 1024;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Miomart2AppFactory factory = new Miomart2AppFactory();
        MainPresenter mainPresenter = factory.getMainPresenter();
        mainPresenter.showAddProduct();
        Scene scene = new Scene(mainPresenter.getView());
        primaryStage.setScene(scene);
        primaryStage.setTitle("miomart2");
        primaryStage.setHeight(DEFAULT_APP_HEIGHT);
        primaryStage.setWidth(DEFAULT_APP_WIDTH);
        primaryStage.show();
    }

}
