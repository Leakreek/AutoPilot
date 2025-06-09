package com.example.autopilot;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddVehicleController {

    @FXML private TextField typPojazduField;
    @FXML private TextField markaField;
    @FXML private TextField modelField;
    @FXML private TextField rokField;
    @FXML private TextField rejestracjaField;
    @FXML private TextField typSilnikaField;
    @FXML private TextField pojemnoscField;
    @FXML private TextField mocField;
    @FXML private TextField przebiegField;
    @FXML private DatePicker ubezpieczenieField;
    @FXML private DatePicker przegladField;

    @FXML
    private void handleSave() {
        try {
            String typ = typPojazduField.getText();
            String marka = markaField.getText();
            String model = modelField.getText();
            int rok = Integer.parseInt(rokField.getText());
            String rej = rejestracjaField.getText();
            String silnik = typSilnikaField.getText();
            double poj = Double.parseDouble(pojemnoscField.getText());
            int moc = Integer.parseInt(mocField.getText());
            int przebieg = Integer.parseInt(przebiegField.getText());
            String ubezp = przegladField.getValue().toString();
            String przegl = ubezpieczenieField.getValue().toString();

            Vehicle v = new Vehicle(typ, marka, model, rok, rej, silnik, poj, moc, przebieg, ubezp, przegl);
            Database.insertVehicle(v);

            // Zamknij okno po zapisaniu
            Stage stage = (Stage) markaField.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
