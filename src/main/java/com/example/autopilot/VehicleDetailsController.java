package com.example.autopilot;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VehicleDetailsController {

    @FXML private TextField typField;
    @FXML private TextField markaField;
    @FXML private TextField modelField;
    @FXML private TextField rokField;
    @FXML private TextField rejestracjaField;
    @FXML private TextField typSilnikaField;
    @FXML private TextField pojemnoscField;
    @FXML private TextField mocField;
    @FXML private TextField przebiegField;
    @FXML private TextField ubezpieczenieField;
    @FXML private TextField przegladField;
    @FXML private Label srednieSpalanieLabel;
    @FXML private ListView<String> kosztorysList;
    @FXML private Label sumaKosztowLabel;


    private Vehicle vehicle;

    public void setVehicle(Vehicle v) {
        this.vehicle = v;

        typField.setText(v.getTypPojazdu());
        markaField.setText(v.getMarka());
        modelField.setText(v.getModel());
        rokField.setText(String.valueOf(v.getRokProdukcji()));
        rejestracjaField.setText(v.getRejestracja());
        typSilnikaField.setText(v.getTypSilnika());
        pojemnoscField.setText(String.valueOf(v.getPojemnosc()));
        mocField.setText(String.valueOf(v.getMoc()));
        przebiegField.setText(String.valueOf(v.getPrzebieg()));
        ubezpieczenieField.setText(v.getUbezpieczenieDo());
        przegladField.setText(v.getPrzegladDo());

        updateVehicleDetails();
        updateKosztorys();
    }

    public void updateVehicleDetails() {
        if (vehicle == null) return;

        typField.setText(vehicle.getTypPojazdu());
        markaField.setText(vehicle.getMarka());
        modelField.setText(vehicle.getModel());
        rokField.setText(String.valueOf(vehicle.getRokProdukcji()));
        rejestracjaField.setText(vehicle.getRejestracja());
        typSilnikaField.setText(vehicle.getTypSilnika());
        pojemnoscField.setText(String.valueOf(vehicle.getPojemnosc()));
        mocField.setText(String.valueOf(vehicle.getMoc()));
        przebiegField.setText(String.valueOf(vehicle.getPrzebieg()));
        ubezpieczenieField.setText(vehicle.getUbezpieczenieDo());
        przegladField.setText(vehicle.getPrzegladDo());

        double spalanie = vehicle.getAverageFuelConsumption();
        srednieSpalanieLabel.setText(String.format("%.1f l/100km", spalanie));
    }


    @FXML
    private void onAddRefuel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add_refuel.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/com/example/autopilot/style.css").toExternalForm());
            stage.setScene(scene);
            stage.showAndWait();stage.setTitle("Dodaj tankowanie");

            AddRefuelController controller = loader.getController();
            controller.setVehicle(vehicle);

            stage.showAndWait();
            updateVehicleDetails();
            updateKosztorys();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onAddMileage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add_mileage.fxml"));
            Scene scene = new Scene(loader.load());

            // Dodaj style.css
            scene.getStylesheets().add(getClass().getResource("/com/example/autopilot/style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Dodaj przebieg");

            AddMileageController controller = loader.getController();
            controller.setVehicle(vehicle);

            stage.showAndWait();
            updateVehicleDetails();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onAddCost() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("add_cost.fxml")); // ← TO BYŁO POMINIĘTE
            Scene scene = new Scene(loader.load());

            // Dodaj style.css
            scene.getStylesheets().add(getClass().getResource("/com/example/autopilot/style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Dodaj koszt");

            AddCostController controller = loader.getController();
            controller.setVehicle(vehicle);

            stage.showAndWait();
            updateKosztorys(); // odświeżenie listy

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateKosztorys() {
        kosztorysList.getItems().clear();

        double suma = 0.0;

        for (RefuelEvent r : vehicle.getRefuels()) {
            double kosztTankowania = r.getPrice() * r.getLiters();
            String line = String.format("%s: Tankowanie - %.2f L x %.2f zł = %.2f zł",
                    r.getDate(),
                    r.getLiters(),
                    r.getPrice(),
                    kosztTankowania);
            kosztorysList.getItems().add(line);
            suma += kosztTankowania;
        }


        for (CostEvent c : vehicle.getCosts()) {
            kosztorysList.getItems().add(c.toString());
            suma += c.getAmount();
        }

        sumaKosztowLabel.setText(String.format("Łączny koszt: %.2f zł", suma));
    }
    @FXML
    private void onEditInfo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit_vehicle.fxml"));
            Scene scene = new Scene(loader.load());

            // Dodaj style.css
            scene.getStylesheets().add(getClass().getResource("/com/example/autopilot/style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Edytuj pojazd");

            EditVehicleController controller = loader.getController();
            controller.setVehicle(vehicle);
            controller.setParentController(this); // <- to dodaj

            stage.showAndWait();
            updateVehicleDetails(); // odśwież dane po edycji

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void onEditVehicle() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit_vehicle.fxml"));
            Scene scene = new Scene(loader.load());

            // Dodaj style.css
            scene.getStylesheets().add(getClass().getResource("/com/example/autopilot/style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Edytuj pojazd");

            EditVehicleController controller = loader.getController();
            controller.setVehicle(vehicle);

            stage.showAndWait();
            updateVehicleDetails(); // odśwież widok po edycji
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onAddInspection() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add_inspection.fxml"));
            Scene scene = new Scene(loader.load());

            // Dodaj style.css
            scene.getStylesheets().add(getClass().getResource("/com/example/autopilot/style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Dodaj przegląd");

            AddInspectionController controller = loader.getController();
            controller.setVehicle(vehicle);

            stage.showAndWait();
            updateVehicleDetails();
            updateKosztorys();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onAddInsurance() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add_insurance.fxml"));
            Scene scene = new Scene(loader.load());

            // Dodaj style.css
            scene.getStylesheets().add(getClass().getResource("/com/example/autopilot/style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Dodaj ubezpieczenie");

            AddInsuranceController controller = loader.getController();
            controller.setVehicle(vehicle);

            stage.showAndWait();
            updateVehicleDetails();
            updateKosztorys();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
