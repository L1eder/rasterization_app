module com.example.rasterization {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.rasterization to javafx.fxml;
    exports com.example.rasterization;
}