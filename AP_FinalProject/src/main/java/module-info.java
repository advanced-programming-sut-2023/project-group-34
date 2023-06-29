module AP.FinalProject {
    requires com.google.gson;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.controls;
    requires xstream;
    requires com.google.common;
    requires org.checkerframework.checker.qual;

    exports view.main;
    exports view;
    exports controller;
    exports model;
    exports view.starter;
    exports view.game.shop;
    exports view.game.trade;
    exports view.game;
    exports model.messenger;


    opens model to javafx.fxml, com.google.gson;
    opens model.user to com.google.gson, javafx.fxml, javafx.base;
    opens model.enums to com.google.gson;
    opens model.government to com.google.gson;
    opens model.building to com.google.gson;
    opens model.map to com.google.gson;
    opens model.human to com.google.gson;
    opens model.enums.make_able to com.google.gson;
    opens controller to javafx.fxml;
    opens view to javafx.fxml,javafx.controls, gdx;
    opens view.starter to javafx.controls, javafx.fxml;
    opens view.main to javafx.fxml;
    exports view.profile;
    opens view.profile to javafx.fxml;
    opens view.game.trade to javafx.fxml;
    opens view.game.shop to javafx.fxml;
    opens view.game to javafx.fxml;
    opens model.messenger to javafx.fxml;


}