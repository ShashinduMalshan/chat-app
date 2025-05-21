package com.service.chat_app;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientController {
    public  TextArea clientView;
    public Button send;
    public TextField txtMsg;
    @FXML
    private Label welcomeText;
 ServerSocket serverSocket;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String message;

    public void SendOnAction(MouseEvent mouseEvent) {

        System.out.println(txtMsg.getText());
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(txtMsg.getText());
            dataOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void initialize() {
        new Thread(() -> {  try {
            socket = new Socket("localhost", 5000);
            dataInputStream = new DataInputStream(socket.getInputStream());

            do {
                message = dataInputStream.readUTF();
                clientView.appendText(message);
            } while (!message.equals("exit"));
           socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }}).start();

    }
}