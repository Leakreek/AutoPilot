package com.example.autopilot;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AddRefuelController {

    @FXML private TextField litersField;
    @FXML private TextField priceField;
    @FXML private ListView<String> historyList;

    private Vehicle vehicle;

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        updateHistory();
    }

    @FXML
    private void handleSave() {
        try {
            double liters = Double.parseDouble(litersField.getText());
            double price = Double.parseDouble(priceField.getText());

            RefuelEvent refuel = new RefuelEvent(liters, price, LocalDate.now());
            vehicle.addRefuel(refuel);

            // 💾 Zapisz do bazy danych
            Database.insertRefuel(vehicle.getId(), refuel);

            updateHistory();

            Stage stage = (Stage) litersField.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            e.printStackTrace(); // możesz zamienić na alert
        }
    }

    private void updateHistory() {
        historyList.getItems().clear();
        for (RefuelEvent r : vehicle.getRefuels()) {
            String line = r.getDate() + " - " + r.getLiters() + " l, " + r.getPrice() + " zł";
            historyList.getItems().add(line);
        }
    }
}
