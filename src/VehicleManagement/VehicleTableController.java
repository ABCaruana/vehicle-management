package VehicleManagement;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class VehicleTableController {
    private static ArrayList<String> vehicleLocations = null;
    private Thread refreshThread;

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
    TableColumn typeColumn;
    @FXML
    Button editVehicleButton;
    @FXML
    Button deleteVehicleButton;
    @FXML
    Button addVehicleButton;
    @FXML
    Button refreshButton;
    @FXML
    Button selectBikesButton;
    @FXML
    Button selectAllButton;
    @FXML
    Button deselectAllButton;
    @FXML
    Button selectCarsButton;

    public static void showVehicles(ArrayList<String> locations) throws IOException {
        vehicleLocations = locations;
        openInitialWindow();
    }

    private static void openInitialWindow() throws IOException {
        Parent root = FXMLLoader.load(VehicleTableController.class.getResource("VehicleTable.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Vehicle Viewer");
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
        else {
            editVehicleButton.setDisable(false);
        }
    }

    @FXML
    private void editVehicle() throws IOException {
        // get selected vehicle
        Object selectedVehicle = vehicleTable.getSelectionModel().getSelectedItem();
        // open window for it
        EditVehicleController.showEditMenu((Vehicle)selectedVehicle);
    }

    public void deleteSelectedVehicles() throws IOException {
        // same as getSelectedRows, but is called each time the table is clicked.
        ObservableList selectedItems = getSelectedRows();
        // if more than one row is selected, disable the edit button
        if (selectedItems.size() < 1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a vehicle to delete");
            alert.setTitle("Delete vehicle error");
            alert.show();
            return;
        }
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

    public void refreshTable(){
        System.out.println("refreshing table");
        vehicleTable.getItems().clear();

        // get new data
        ArrayList<Vehicle> vehicles = JSONProcessing.getVehicleByLocation(vehicleLocations);
        setDetails(vehicles);
    }

    @FXML
    private void openAddMenu() throws IOException {
        Parent root = FXMLLoader.load(VehicleTableController.class.getResource("AddVehicle.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add new Vehicle");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    private void initButtons(){

        // refresh icon
        Image refreshImg = new Image("ui/refresh.png");
        ImageView refreshIcon = new ImageView(refreshImg);
        refreshIcon.setPreserveRatio(true);
        refreshButton.setGraphic(refreshIcon);
        refreshButton.setTooltip(new Tooltip("Refresh Data"));

        // add vehicle icon
        Image addImg = new  Image("ui/add.png");
        ImageView addIcon = new ImageView(addImg);
        addIcon.setPreserveRatio(true);
        addVehicleButton.setGraphic(addIcon);
        addVehicleButton.setTooltip(new Tooltip("Add new Vehicle"));

        // delete vehicle icon
        Image delImg = new Image("ui/delete.png");
        ImageView deleteIcon = new ImageView(delImg);
        deleteIcon.setPreserveRatio(true);
        deleteVehicleButton.setGraphic(deleteIcon);
        deleteVehicleButton.setTooltip(new Tooltip("Delete Vehicle(s)"));

        // edit vehicle icon
        Image editImg = new Image("ui/edit.png");
        ImageView editIcon = new ImageView(editImg);
        editIcon.setPreserveRatio(true);
        editVehicleButton.setGraphic(editIcon);
        editVehicleButton.setTooltip(new Tooltip("Edit Vehicle"));
    }

    @FXML
    private void selectAll(){
        int i = 0;
        for (Object vehicle : vehicleTable.getItems()) {
            // make sure everything is deselected
            vehicleTable.getSelectionModel().select(i);
            i++;
        }
    }

    @FXML
    private void deselectAll(){
        int i = 0;
        for (Object vehicle : vehicleTable.getItems()) {
            // make sure everything is deselected
            vehicleTable.getSelectionModel().clearSelection(i);
            i++;
        }
    }

    @FXML
    private void selectBikes(){
        int i = 0;
        deselectAll();
        for (Object vehicle : vehicleTable.getItems()){
            if ( ((Vehicle)vehicle).getVehicleType().equals("Motorcycle") ){
                vehicleTable.getSelectionModel().select(i);
            }
            i++;
        }
        // for some reason JFX doesnt edit the number of selected items, so manually disable specific buttons here
        editVehicleButton.setDisable(true);
    }

    @FXML
    private void selectCars(){
        int i = 0;
        deselectAll();
        for (Object vehicle : vehicleTable.getItems()){
            if ( ((Vehicle)vehicle).getVehicleType().equals("Car") ){
                vehicleTable.getSelectionModel().select(i);
            }
            i++;
        }
        // for some reason JFX doesnt edit the number of selected items, so manually disable specific buttons here
        editVehicleButton.setDisable(true);
    }

    @FXML
    void initialize(){

        initButtons();

        // link our data to table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleBrand"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleModel"));
        colourColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleColour"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleYear"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleLocation"));

        // allow user to select multiple items
        vehicleTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // pass all of our applicant object details into the window
        setDetails(JSONProcessing.getVehicleByLocation(vehicleLocations));
    }

}
