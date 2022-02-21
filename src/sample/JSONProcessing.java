package sample;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class JSONProcessing {
    private static final String fileName = "data.json";
    private static JSONArray currentJSON;

    static {
        try {
            currentJSON = dataToJSONArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // turn all of our json code into a list of Vehicle objects
    // use this to build a TableView
    private static Vehicle[] dataToObjects(){
        return new Gson().fromJson(currentJSON.toString(), Vehicle[].class);
    }

    public static JSONArray dataToJSONArray() throws IOException {
        // read the contents of data.json
        String content = new String(Files.readAllBytes(Paths.get(fileName)));

        // get the array inside our content string as a JSONArray
        JSONObject jsonObject = new JSONObject(content);

        return (JSONArray) jsonObject.get("data");
    }

    public static void updateVehicle(int id, String brand, String model, String type, int year, String colour, String location) throws IOException {
        System.out.printf("updating vehicle ID: %s%n", id);
        // try to find a vehicle with the given ID
        JSONObject targetVehicle = null;
        for (int i = 0; i < currentJSON.length() ; i++){
            targetVehicle = currentJSON.getJSONObject(i);
            // dont go to the next JSONObject if the current one shares the same ID as the parameters
            if (targetVehicle.get("id").equals(id)) {
                targetVehicle.put("brand", brand);
                targetVehicle.put("model", model);
                targetVehicle.put("type", type);
                targetVehicle.put("year", year);
                targetVehicle.put("colour", colour);
                targetVehicle.put("location", location);

                currentJSON.put(i, targetVehicle);
                break;
            }
        }
        // make sure to update the data.
        saveData();
    }

    public static void deleteVehicle(int id) throws IOException {
        System.out.printf("deleting vehicle ID: %s%n", id);
        JSONObject targetVehicle = null;
        for (int i = 0; i < currentJSON.length() ; i++) {
            targetVehicle = currentJSON.getJSONObject(i);
            int targetVehicleID = (int) targetVehicle.get("id");

            if (targetVehicleID == id) {
                currentJSON.remove(i);
            }
        }
        // make sure to update the data.
        saveData();
    }

    public static void createVehicle(String brand, String model, String type, int year, String colour, String location) throws IOException {
        JSONObject newVehicle = new JSONObject();
        newVehicle.put("id", getBiggestID() + 1);
        newVehicle.put("brand", brand);
        newVehicle.put("model", model);
        newVehicle.put("type", type);
        newVehicle.put("year", year);
        newVehicle.put("colour", colour);
        newVehicle.put("location", location);
        currentJSON.put(newVehicle);
        saveData();
    }

    public static ArrayList<Vehicle> getVehicleByLocation(ArrayList<String> locations){
        Vehicle[] vehicles = dataToObjects();
        ArrayList<Vehicle> output = new ArrayList<>();
        for (Vehicle vehicle : vehicles){
            if (locations.contains(vehicle.getLocation())){
                output.add(vehicle);
            }
        }
        return output;
    }

    private static int getBiggestID(){
        // get the largest ID in the database
        int largestNumber = 0;
        JSONObject targetVehicle = null;

        for (int i = 0; i < currentJSON.length() ; i++) {
            targetVehicle = currentJSON.getJSONObject(i);
            int targetVehicleID = (int) targetVehicle.get("id");

            if (targetVehicleID > largestNumber) {
                largestNumber = targetVehicleID;
            }
        }
        return largestNumber;
    }

    private static void saveData() throws IOException {
        System.out.println("Saving data");
        // write the contents of currentJSON to the data.json file.
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", currentJSON);

        // write data
        FileWriter file = new FileWriter(fileName);
        file.write(jsonObject.toString());

        // close file
        file.close();
    }
}
