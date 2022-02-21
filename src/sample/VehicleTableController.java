package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

public class VehicleTableController {
    private static ArrayList<String> vehicleLocations = null;

    @FXML
    TableView vehicleTable;
    @FXML
    TableColumn idColumn;
    @FXML
    TableColumn brandColumn;
    @FXML
    TableColumn modelColumn;
    @FXML
    TableColumn colourColumn;
    @FXML
    TableColumn yearColumn;
    @FXML
    TableColumn locationColumn;
    @FXML
    Button editVehicleButton;
    @FXML
    Button deleteVehicleButton;

    public static void showVehicles(ArrayList<String> locations) throws IOException {
        vehicleLocations = locations;
        openInitialWindow();
    }

    private static void openInitialWindow() throws IOException {
        Parent root = FXMLLoader.load(VehicleTableController.class.getResource("VehicleTable.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Applicant viewer");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    private void setDetails(ArrayList<Vehicle> vehicles){
        for (Vehicle vehicle : vehicles){
            vehicleTable.getItems().add(vehicle);
        }
    }

    public ObservableList getSelectedRows() {
        // check the table's selected item and get selected item
        ObservableList selectedItems = vehicleTable.getSelectionModel().getSelectedItems();
        return selectedItems;
    }

    public ArrayList<Integer> getSelectedIDs(){
        // get the IDs of all selected vehicles
        ArrayList<Integer> output = new ArrayList<Integer>();
        ObservableList rows = getSelectedRows();
        for (Object row : rows) {
            int id = ((Vehicle) row).getId();
            output.add(id);
        }
        return output;
    }

    public void getSelectedRowsListener(){
        // same as getSelectedRows, but is called each time the table is clicked.
        ObservableList selectedItems = getSelectedRows();
        // if more than one row is selected, disable the edit button
        if (selectedItems.size() > 1){
            editVehicleButton.setDisable(true);
        }
        else if (selectedItems.size() == 1){
            deleteVehicleButton.setDisable(false);
            editVehicleButton.setDisable(false);
        }
        else {
            editVehicleButton.setDisable(false);
            deleteVehicleButton.setDisable(true);
        }
    }

    public void deleteSelectedVehicles() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to delete the selected vehicles? This action is irreversible.");
        alert.setTitle("Delete vehicle confirmation");

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == yesButton) {
            ArrayList<Integer> ids = getSelectedIDs();
            for (int id : ids) {
                JSONProcessing.deleteVehicle(id);
            }
            refreshTable();
        }
    }

    private void refreshTable(){
        System.out.println("refreshing table");
        vehicleTable.getItems().clear();

        // get new data
        ArrayList<Vehicle> vehicles = JSONProcessing.getVehicleByLocation(vehicleLocations);
        setDetails(vehicles);
    }

    @FXML
    void initialize(){
        // link our data to table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        colourColumn.setCellValueFactory(new PropertyValueFactory<>("colour"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        // allow user to select multiple items
        vehicleTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // pass all of our applicant object details into the window
        setDetails(JSONProcessing.getVehicleByLocation(vehicleLocations));
    }
}
