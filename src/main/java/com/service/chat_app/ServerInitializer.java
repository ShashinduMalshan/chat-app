package com.service.chat_app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
        new SererController().initialize();
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/Server.fxml"))));

        stage.setTitle("Server System");

        stage.show();

    }


}

