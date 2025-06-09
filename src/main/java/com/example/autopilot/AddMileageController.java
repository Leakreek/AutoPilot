package com.example.autopilot;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AddMileageController {

    @FXML private TextField kmField;

    private Vehicle vehicle;

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @FXML
    private void handleSave() {
        try {
            int km = Integer.parseInt(kmField.getText());
            MileageEvent event = new MileageEvent(km, LocalDate.now());
            vehicle.addMileage(event);

            // Zapisz do bazy danych
            Database.insertMileage(vehicle.getId(), event);

            Stage stage = (Stage) kmField.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
