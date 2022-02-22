import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import sample.Vehicle;

public class TestVehicle {
    private Vehicle testVehicle = new Vehicle(
            999999, "TestBrand", "TestModel", "Car",
            "TestColour", "Plymouth", 2004
    );

    // Test Getters
    @Test
    void testGetId(){
        assertEquals(testVehicle.getId(), 999999);
    }
    @Test
    void testGetBrand(){
        assertEquals(testVehicle.getBrand(), "TestBrand");
    }
    @Test
    void testGetModel(){
        assertEquals(testVehicle.getModel(), "TestModel");
    }
    @Test
    void testGetType(){
        assertEquals(testVehicle.getType(), "Car");
    }
    @Test
    void testGetColour(){
        assertEquals(testVehicle.getColour(), "TestColour");
    }
    @Test
    void testGetLocation(){
        assertEquals(testVehicle.getLocation(), "Plymouth");
    }
    @Test
    void testGetYear(){
        assertEquals(testVehicle.getYear(), 2004);
    }

    // Test Setters
    @Test
    void testSetBrand(){
        String targetBrand = "TestBrand2";
        testVehicle.setBrand(targetBrand);
        assertEquals(testVehicle.getBrand(), targetBrand);
    }
    @Test
    void testSetModel(){
        String targetModel = "TestModel2";
        testVehicle.setModel(targetModel);
        assertEquals(testVehicle.getModel(), targetModel);
    }
    @Test
    void testSetType(){
        String targetType = "Motorcycle";
        testVehicle.setType(targetType);
        assertEquals(testVehicle.getType(), targetType);
    }
    @Test
    void testSetColour(){
        String targetColour = "TestColour2";
        testVehicle.setColour(targetColour);
        assertEquals(testVehicle.getColour(), targetColour);
    }
    @Test
    void testSetLocation(){
        String targetLocation = "Torquay";
        testVehicle.setType(targetLocation);
        assertEquals(testVehicle.getType(), targetLocation);
    }
    @Test
    void testSetYear(){
        int targetYear = 2022;
        testVehicle.setYear(targetYear);
        assertEquals(testVehicle.getYear(), targetYear);
    }

    // Test toString
    @Test
    void testToString(){
        String output = testVehicle.toString();
        String expected = "Vehicle{id=999999, brand='TestBrand', model='TestModel', type='Car', colour='TestColour', location='Plymouth', year=2004}";
        assertEquals(output, expected);
    }
}
