module com.example.roka {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.roka to javafx.fxml;
    exports com.example.roka;
}