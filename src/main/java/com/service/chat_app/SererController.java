package com.service.chat_app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class SererController {

    @FXML
    public TextField txtMsg;
    @FXML
    public TextArea severView;
    @FXML
    public Button send;
    @FXML
    public Button file;

    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    public void initialize() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(5000);
                severView.appendText("Server started...\n");

                socket = serverSocket.accept();
                severView.appendText("Client Connected...\n");

                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                while (true) {
                    String msg = dataInputStream.readUTF();
                    severView.appendText("Client : " + msg + "\n");
                    if (msg.equals("exit")) {
                        break;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void SendOnAction(MouseEvent mouseEvent) throws IOException {
        String message = txtMsg.getText();
        dataOutputStream.writeUTF("MSG:" + message);
        dataOutputStream.flush();
        severView.appendText("Me : " + message + "\n");
        txtMsg.clear();
    }

    public void fileOnAction(MouseEvent mouseEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            dataOutputStream.writeUTF("FILE:" + file.getName());
            dataOutputStream.writeInt(fileContent.length);
            dataOutputStream.write(fileContent);
            dataOutputStream.flush();

            severView.appendText("File sent: " + file.getName() + "\n");
        }
    }
}
