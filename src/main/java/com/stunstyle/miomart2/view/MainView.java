package com.stunstyle.miomart2.view;

import com.stunstyle.miomart2.presenter.MainPresenter;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class MainView extends BorderPane {
    private MainPresenter presenter;
    private BorderPane contentArea;

    public MainView() {
        buildView();
    }

    public void setPresenter(MainPresenter presenter) {
        this.presenter = presenter;
    }

    public void setContent(Node content) {
        contentArea.setCenter(content);
    }

    protected void buildView() {
        MenuBar menuBar = new MenuBar();
        menuBar.setUseSystemMenuBar(true);
        Menu fileMenu = new Menu("Файл");
        MenuItem manageProductsItem = new MenuItem("Управление на данни за продукти");
        manageProductsItem.setOnAction(actionEvent -> presenter.showAddProduct());
        MenuItem browseOrEditItem = new MenuItem("Преглед/Промяна на записи");
        browseOrEditItem.setOnAction(actionEvent -> presenter.showPickDate());
        MenuItem exitItem = new MenuItem("Изход");
        exitItem.setOnAction(actionEvent -> Platform.exit());
        fileMenu.getItems().addAll(manageProductsItem, browseOrEditItem, exitItem);

        Menu viewMenu = new Menu("Отчет");
        Menu printMenu = new Menu("Принт");
        Menu helpMenu = new Menu("Помощ");
        MenuItem aboutItem = new MenuItem("Относно...");
        aboutItem.setOnAction(actionEvent -> presenter.showAbout());
        helpMenu.getItems().add(aboutItem);
        menuBar.getMenus().addAll(fileMenu, viewMenu, printMenu, helpMenu);

        contentArea = new BorderPane();

        // needed to prevent ALT key from selecting menu bar items
        // this way, we enable the user to switch language with ALT + SHIFT
        // or similar key combos
        contentArea.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ALT) {
                keyEvent.consume();
            }
        });

        setTop(menuBar);
        setCenter(contentArea);
    }
}
