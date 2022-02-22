import org.junit.Test;
import sample.JSONProcessing;
import sample.Vehicle;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class TestJSONProcessing {

    @Test
    public void testCreateVehicle() {

        Integer id = JSONProcessing.createVehicle("TestBrand", "TestModel", "Car",
                2004, "green", "Plymouth");

        // if an id is returned, this means the vehicle was created successfully
        assertNotNull(id);

        // destroy test data
        JSONProcessing.deleteVehicle(id);
    }

    @Test
    public void testUpdateVehicle(){
        Integer id = JSONProcessing.createVehicle("TestBrand", "TestModel", "Car",
                2004, "green", "Plymouth");

        // if an id is returned, this means the vehicle was created successfully
        assertNotNull(id);

        JSONProcessing.updateVehicle(id, "TestBrand2", "TestModel2", "Motorcycle",
                2021, "blue", "Torquay");

        Vehicle output = JSONProcessing.getVehicleByID(id);

        assertEquals(output.getId(), id);
        assertEquals(output.getYear(), 2021);
        assertEquals(output.getColour(), "blue");
        assertEquals(output.getType(), "Motorcycle");
        assertEquals(output.getLocation(), "Torquay");
        assertEquals(output.getBrand(), "TestBrand2");
        assertEquals(output.getModel(), "TestModel2");

        // destroy test data
        JSONProcessing.deleteVehicle(id);

    }

    @Test
    public void testGetVehicleByLocationSingle(){
        // loop through return value and assert all are the correct location
        // we will use Plymouth for this example
        Integer id = JSONProcessing.createVehicle("TestBrand", "TestModel", "Car",
                2004, "green", "Plymouth");

        // if an id is returned, this means the vehicle was created successfully
        assertNotNull(id);

        ArrayList<String> locations = new ArrayList<>();
        locations.add("Plymouth");
        ArrayList<Vehicle> vehicles = JSONProcessing.getVehicleByLocation(locations);

        for (Vehicle vehicle : vehicles){
            assertEquals(vehicle.getLocation(), "Plymouth");
        }

        JSONProcessing.deleteVehicle(id);
    }

    @Test
    public void testGetVehicleByLocationMultiple(){
        // loop through return value and assert all are the correct location
        // we will use Plymouth and Torquay for this example
        Integer id = JSONProcessing.createVehicle("TestBrand", "TestModel", "Car",
                2004, "green", "Plymouth");

        Integer id2 = JSONProcessing.createVehicle("TestBrand2", "TestModel2", "Motorcycle",
                2022, "blue", "Torquay");

        // if an id is returned, this means the vehicle was created successfully
        assertNotNull(id);
        assertNotNull(id2);

        ArrayList<String> locations = new ArrayList<>();
        locations.add("Plymouth");
        locations.add("Torquay");
        ArrayList<Vehicle> vehicles = JSONProcessing.getVehicleByLocation(locations);

        for (Vehicle vehicle : vehicles){
            if(vehicle.getLocation().equals("Plymouth") || vehicle.getLocation().equals("Torquay")){
                // pass
                assertTrue(true);
            }
            else {
                fail();
            }
        }

        JSONProcessing.deleteVehicle(id);
        JSONProcessing.deleteVehicle(id2);
    }

    @Test
    public void testGetVehicleByID(){
        Integer id = JSONProcessing.createVehicle("TestBrand", "TestModel", "Car",
                2004, "green", "Plymouth");

        Vehicle output = JSONProcessing.getVehicleByID(id);
        assertEquals(output.getId(), id);
        assertEquals(output.getYear(), 2004);
        assertEquals(output.getColour(), "green");
        assertEquals(output.getType(), "Car");
        assertEquals(output.getLocation(), "Plymouth");
        assertEquals(output.getBrand(), "TestBrand");
        assertEquals(output.getModel(), "TestModel");

        JSONProcessing.deleteVehicle(id);
    }
}
