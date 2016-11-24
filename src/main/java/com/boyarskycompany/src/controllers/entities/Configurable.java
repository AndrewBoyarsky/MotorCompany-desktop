package com.boyarskycompany.src.controllers.entities;

import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;

/**
 * Created by zandr on 16.10.2016.
 */
public interface Configurable {
    public void configureMainMenu(MenuBar menuBar);

    public<E> void configureNavigationBar(BorderPane pane);
}
