package com.example.autopilot;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditVehicleController {

    @FXML private TextField rejestracjaField;
    @FXML private TextField przebiegField;

    private Vehicle vehicle;
    private VehicleDetailsController parentController;

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        if (vehicle != null) {
            rejestracjaField.setText(vehicle.getRejestracja());
            przebiegField.setText(String.valueOf(vehicle.getPrzebieg()));
        }
    }

    public void setParentController(VehicleDetailsController parentController) {
        this.parentController = parentController;
    }

    @FXML
    private void onSave() {
        if (vehicle == null) return;

        String newPlate = rejestracjaField.getText().trim();
        String kmText = przebiegField.getText().trim();

        if (!newPlate.isEmpty()) {
            vehicle.setRejestracja(newPlate);
        }

        try {
            int km = Integer.parseInt(kmText);
            vehicle.setPrzebieg(km);
        } catch (NumberFormatException e) {
            System.err.println("Nieprawidłowy przebieg: " + kmText);
        }

        // Zapisz do bazy danych
        Database.updateVehicle(vehicle);


        if (parentController != null) {
            parentController.updateVehicleDetails();
        }

        closeWindow();
    }

    @FXML
    private void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) przebiegField.getScene().getWindow();
        stage.close();
    }
}
