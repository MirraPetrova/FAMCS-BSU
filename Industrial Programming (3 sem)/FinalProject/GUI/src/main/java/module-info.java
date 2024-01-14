
module org.example.gui {
        requires javafx.controls;
        requires javafx.fxml;
        requires java.xml;
        requires json.simple;
        requires exp4j;


        opens com.example.demo to javafx.fxml;
        exports org.example.gui;
        }
