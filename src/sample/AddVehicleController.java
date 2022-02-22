package sample;

import com.sun.javafx.scene.control.InputField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AddVehicleController {
    @FXML
    ChoiceBox brandChoiceBox;
    @FXML
    ChoiceBox locationChoiceBox;
    @FXML
    ChoiceBox typeChoiceBox;
    @FXML
    TextField yearField;
    @FXML
    TextField colourField;
    @FXML
    TextField modelField;
    @FXML
    Button cancelButton;
    @FXML
    Button saveButton;

    public static JSONObject getVehicleConfig() throws IOException {
        // read the vehicle config json
        String content = new String(Files.readAllBytes(Paths.get("vehicleConfig.json")));
        return new JSONObject(content);
    }

    public void populateDropDowns(JSONObject jsonObject) throws IOException {
        // fill in the dropdown bars with information
        addBrands((JSONArray) jsonObject.get("brands"));
        addVehicleTypes((JSONArray) jsonObject.get("types"));
        addLocations((JSONArray) jsonObject.get("locations"));
    }

    public void addBrands(JSONArray brands) throws FileNotFoundException {
        ObservableList<String> availableChoices = FXCollections.observableArrayList();
        for (Object brand : brands){
            availableChoices.add(brand.toString());
        }
        brandChoiceBox.setItems(availableChoices);
    }

    public void addVehicleTypes(JSONArray types){
        ObservableList<String> availableChoices = FXCollections.observableArrayList();
        for (Object type : types){
            availableChoices.add(type.toString());
        }
        typeChoiceBox.setItems(availableChoices);
    }

    public void addLocations(JSONArray locations){
        ObservableList<String> availableChoices = FXCollections.observableArrayList();
        for (Object location : locations){
            availableChoices.add(location.toString());
        }
        locationChoiceBox.setItems(availableChoices);
    }

    @FXML
    void closeWindow(){
        // get a handle to the stage
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void saveVehicle() throws IOException {
        String brandChoice = (String) brandChoiceBox.getValue();
        String typeChoice = (String) typeChoiceBox.getValue();
        String locationChoice = (String) locationChoiceBox.getValue();
        String modelChoice = modelField.getText();
        String colourChoice = colourField.getText();
        String yearChoice = yearField.getText();

        // create a list of problematic fields to present to the user at the end, if applicable
        String result = Validation.checkErrors(brandChoice, typeChoice, locationChoice, modelChoice, colourChoice, yearChoice);
        if (result != null) {
            Alert a = new Alert(Alert.AlertType.INFORMATION, result);
            a.setTitle("Problems with vehicle");
            a.showAndWait();
            return;
        }


        // after this point, all data should be valid
        int parsedYearChoice = Integer.parseInt(yearField.getText());

        JSONProcessing.createVehicle(brandChoice, modelChoice, typeChoice, parsedYearChoice, colourChoice, locationChoice);

        Alert completionDialog = new Alert(Alert.AlertType.INFORMATION, "Vehicle added to database");
        completionDialog.showAndWait();
        closeWindow();
    }

    @FXML
    void initialize() throws IOException {
        populateDropDowns(getVehicleConfig());
    }
}
