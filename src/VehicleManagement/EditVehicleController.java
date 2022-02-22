package VehicleManagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;

public class EditVehicleController {
    private static Vehicle targetVehicle;

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
    Button updateButton;

    public void populateDropDowns(JSONObject jsonObject) throws IOException {
        // fill in the dropdown bars with information
        addBrands((JSONArray) jsonObject.get("brands"));
        addVehicleTypes((JSONArray) jsonObject.get("types"));
        addLocations((JSONArray) jsonObject.get("locations"));
        brandChoiceBox.setValue(targetVehicle.getVehicleBrand());
        typeChoiceBox.setValue(targetVehicle.getVehicleType());
        locationChoiceBox.setValue(targetVehicle.getVehicleLocation());
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

    public static void showEditMenu(Vehicle vehicle) throws IOException {
        targetVehicle = vehicle;
        openInitialWindow();
    }

    private static void openInitialWindow() throws IOException {
        Parent root = FXMLLoader.load(EditVehicleController.class.getResource("EditVehicle.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Edit Vehicle");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void closeWindow(){
        // get a handle to the stage
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void updateVehicle() throws IOException {
        String brandChoice = (String) brandChoiceBox.getValue();
        String typeChoice = (String) typeChoiceBox.getValue();
        String locationChoice = (String) locationChoiceBox.getValue();
        String modelChoice = modelField.getText();
        String colourChoice = colourField.getText();
        String yearChoice = yearField.getText();

        // create a list of problematic fields to present to the user at the end, if applicable
        String result = Validation.checkErrors(brandChoice, typeChoice, locationChoice, modelChoice, colourChoice, yearChoice);
            if (result != null){
                Alert a = new Alert(Alert.AlertType.INFORMATION, result);
                a.setTitle("Problems with vehicle");
                a.showAndWait();
                return;
            }

        // after this point, all data should be valid
        int parsedYearChoice = Integer.parseInt(yearField.getText());

        JSONProcessing.updateVehicle(targetVehicle.getId(), brandChoice, modelChoice, typeChoice, parsedYearChoice, colourChoice, locationChoice);

        Alert completionDialog = new Alert(Alert.AlertType.INFORMATION, "Vehicle updated in database");
        completionDialog.showAndWait();
        closeWindow();
    }

    private void populateTextFields(){
        yearField.setText(String.valueOf(targetVehicle.getVehicleYear()));
        colourField.setText(targetVehicle.getVehicleColour());
        modelField.setText(targetVehicle.getVehicleModel());
    }

    @FXML
    void initialize() throws IOException {
        populateDropDowns(AddVehicleController.getVehicleConfig());
        populateTextFields();
    }
}
