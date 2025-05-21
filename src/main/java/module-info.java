module com.service.chat_app {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.service.chat_app to javafx.fxml;
    exports com.service.chat_app;
}