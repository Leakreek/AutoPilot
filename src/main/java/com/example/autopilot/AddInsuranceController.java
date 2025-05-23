package com.example.autopilot;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AddInsuranceController {

    @FXML private DatePicker dateField;
    @FXML private TextField amountField;

    private Vehicle vehicle;

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @FXML
    private void onSave() {
        try {
            LocalDate selectedDate = dateField.getValue();
            double koszt = Double.parseDouble(amountField.getText());

            // ustaw nową datę ważności ubezpieczenia (+1 rok)
            vehicle.setUbezpieczenieDo(selectedDate.plusYears(1).toString());
            Database.updateVehicle(vehicle);
            // dodaj koszt
            CostEvent cost = new CostEvent("Ubezpieczenie", koszt, LocalDate.now());
            vehicle.addCost(cost);
            Database.insertCost(vehicle.getId(), cost);

            closeWindow();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) amountField.getScene().getWindow();
        stage.close();
    }
}
