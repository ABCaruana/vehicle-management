import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import VehicleManagement.Vehicle;

public class TestVehicle {
    private Vehicle testVehicle = new Vehicle(
            999999, "TestBrand", "TestModel", "Car",
            "TestColour", "Plymouth", 2004
    );

    // Test Getters
    @Test
    public void testGetId(){
        assertEquals(testVehicle.getId(), 999999);
    }
    @Test
    public void testGetBrand(){
        assertEquals(testVehicle.getVehicleBrand(), "TestBrand");
    }
    @Test
    public void testGetModel(){
        assertEquals(testVehicle.getVehicleModel(), "TestModel");
    }
    @Test
    public void testGetType(){
        assertEquals(testVehicle.getVehicleType(), "Car");
    }
    @Test
    public void testGetColour(){
        assertEquals(testVehicle.getVehicleColour(), "TestColour");
    }
    @Test
    public void testGetLocation(){
        assertEquals(testVehicle.getVehicleLocation(), "Plymouth");
    }
    @Test
    public void testGetYear(){
        assertEquals(testVehicle.getVehicleYear(), 2004);
    }

    // Test Setters
    @Test
    public void testSetBrand(){
        String targetBrand = "TestBrand2";
        testVehicle.setVehicleBrand(targetBrand);
        assertEquals(testVehicle.getVehicleBrand(), targetBrand);
    }
    @Test
    public void testSetModel(){
        String targetModel = "TestModel2";
        testVehicle.setVehicleModel(targetModel);
        assertEquals(testVehicle.getVehicleModel(), targetModel);
    }
    @Test
    public void testSetType(){
        String targetType = "Motorcycle";
        testVehicle.setVehicleType(targetType);
        assertEquals(testVehicle.getVehicleType(), targetType);
    }
    @Test
    public void testSetColour(){
        String targetColour = "TestColour2";
        testVehicle.setVehicleColour(targetColour);
        assertEquals(testVehicle.getVehicleColour(), targetColour);
    }
    @Test
    public void testSetLocation(){
        String targetLocation = "Torquay";
        testVehicle.setVehicleType(targetLocation);
        assertEquals(testVehicle.getVehicleType(), targetLocation);
    }
    @Test
    public void testSetYear(){
        int targetYear = 2022;
        testVehicle.setVehicleYear(targetYear);
        assertEquals(testVehicle.getVehicleYear(), targetYear);
    }

    // Test toString
    @Test
    public void testToString(){
        String output = testVehicle.toString();
        String expected = "Vehicle{id=999999, brand='TestBrand', model='TestModel', type='Car', colour='TestColour', location='Plymouth', year=2004}";
        assertEquals(output, expected);
    }
}
