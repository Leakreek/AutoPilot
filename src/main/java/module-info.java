module com.example.autopilot {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // ← to dodaj

    opens com.example.autopilot to javafx.fxml;
    exports com.example.autopilot;
}