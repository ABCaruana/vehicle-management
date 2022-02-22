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

    public static void updateVehicle(int id, String brand, String model, String type, int year, String colour, String location) {
        try{
            System.out.printf("updating vehicle ID: %s%n", id);
            // try to find a vehicle with the given ID
            JSONObject targetVehicle = null;
            for (int i = 0; i < currentJSON.length() ; i++){
                targetVehicle = currentJSON.getJSONObject(i);
                // dont go to the next JSONObject if the current one shares the same ID as the parameters
                if (targetVehicle.get("id").equals(id)) {
                    targetVehicle.put("vehicleBrand", brand);
                    targetVehicle.put("vehicleModel", model);
                    targetVehicle.put("vehicleType", type);
                    targetVehicle.put("vehicleYear", year);
                    targetVehicle.put("vehicleColour", colour);
                    targetVehicle.put("vehicleLocation", location);

                    currentJSON.put(i, targetVehicle);
                    break;
                }
            }
            // make sure to update the data.
            saveData();
        }
        catch (Exception ex){
            System.out.println("error updating vehicle");
            ex.printStackTrace();
        }
    }

    public static void deleteVehicle(int id) {
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
        try{
            saveData();
        }
        catch(IOException ex){
            System.out.println("unable to save data.");
        }
    }

    public static Integer createVehicle(String brand, String model, String type, int year, String colour, String location){
        try{
            JSONObject newVehicle = new JSONObject();
            int newID = getBiggestID() + 1;
            newVehicle.put("id", newID);
            newVehicle.put("vehicleBrand", brand);
            newVehicle.put("vehicleModel", model);
            newVehicle.put("vehicleType", type);
            newVehicle.put("vehicleYear", year);
            newVehicle.put("vehicleColour", colour);
            newVehicle.put("vehicleLocation", location);
            currentJSON.put(newVehicle);
            saveData();
            return newID;
        }
        catch (Exception e){
            System.out.println("Error creating vehicle");
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Vehicle> getVehicleByLocation(ArrayList<String> locations){
        Vehicle[] vehicles = dataToObjects();
        ArrayList<Vehicle> output = new ArrayList<>();
        for (Vehicle vehicle : vehicles){
            if (locations.contains(vehicle.getVehicleLocation())){
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

    public static Vehicle getVehicleByID(Integer id){
        Vehicle[] vehicles = dataToObjects();
        for(Vehicle vehicle : vehicles){
            if (vehicle.getId() == id){
                return vehicle;
            }
        }
        return null;
    }

    private static void saveData() throws IOException {
        try{
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
        catch (Exception ex){
            System.out.println("error saving data");
            ex.printStackTrace();
            throw ex;
        }
    }
}
