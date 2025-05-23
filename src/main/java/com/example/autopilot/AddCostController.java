package com.example.autopilot;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AddCostController {

    @FXML private TextField descriptionField;
    @FXML private TextField amountField;

    private Vehicle vehicle;

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @FXML
    private void handleSave() {
        try {
            String description = descriptionField.getText();
            double amount = Double.parseDouble(amountField.getText());

            CostEvent cost = new CostEvent(description, amount, LocalDate.now());
            vehicle.addCost(cost);

            Database.insertCost(vehicle.getId(), cost); // zapis do bazy

            Stage stage = (Stage) amountField.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
