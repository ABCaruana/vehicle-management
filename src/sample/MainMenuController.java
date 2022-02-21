package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainMenuController {

    @FXML
    CheckBox newquayCheck;
    @FXML
    CheckBox plymouthCheck;
    @FXML
    CheckBox torquayCheck;
    @FXML
    CheckBox tavistockCheck;
    @FXML
    Button submitButton;

    @FXML
    private void submitForm() throws IOException {
        ArrayList<String> locations = getLocations();

        // make sure at least one location is selected
        if (locations.size() == 0) {
            Alert noLocationsError = new Alert(Alert.AlertType.ERROR, "Please Choose at least one location.");
            noLocationsError.setTitle("Error");
            noLocationsError.showAndWait();
            return;
        }

        // get all vehicles from that location as a list of Vehicle Objects
        ArrayList<Vehicle> vehicles = JSONProcessing.getVehicleByLocation(locations);
        for (Vehicle vehicle : vehicles){
            System.out.println(vehicle.getLocation());
        }

        // open TableView
        VehicleTableController.showVehicles(locations);
    }

    private ArrayList<String> getLocations(){
        CheckBox[] locations = new CheckBox[4];
        locations[0] = newquayCheck;
        locations[1] = plymouthCheck;
        locations[2] = torquayCheck;
        locations[3] = tavistockCheck;
        ArrayList<String> output = new ArrayList<>();

        for (CheckBox location : locations) {
            if (location.isSelected()) {
                output.add(location.getText());
            }
        }

        return output;
    }
}
