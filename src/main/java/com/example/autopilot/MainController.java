package com.example.autopilot;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class MainController {

    private List<Vehicle> pojazdy = new ArrayList<>();
    @FXML private ListView<String> vehicleList;


    @FXML
    private void onAddVehicle() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add_vehicle.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Dodaj pojazd");
            stage.showAndWait();
            loadVehicles(); // odśwież listę po zamknięciu okna


            // odświeżenie listy (na później)
            // loadVehicles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadVehicles() {
        pojazdy = Database.getAllVehicles(); // ← przypisujemy do pola, a nie tworzymy nową lokalną zmienną
        ObservableList<String> data = FXCollections.observableArrayList();

        for (Vehicle v : pojazdy) {
            data.add(v.toString());
        }

        vehicleList.setItems(data);
    }

    @FXML
    public void initialize() {
        loadVehicles();
    }

    @FXML
    private void onDeleteVehicle() {
        int selectedIndex = vehicleList.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Vehicle selected = pojazdy.get(selectedIndex); // musi istnieć lista pojazdów
            Database.deleteVehicleById(selected.getId());
            loadVehicles(); // odświeżenie listy
        }
    }

    @FXML
    private void onSelectVehicle() {
        int index = vehicleList.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Vehicle selected = pojazdy.get(index);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("vehicle_details.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Szczegóły pojazdu");

                VehicleDetailsController controller = loader.getController();
                controller.setVehicle(selected);

                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




}
