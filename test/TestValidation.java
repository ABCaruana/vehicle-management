import org.junit.Test;
import VehicleManagement.Validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestValidation {
    @Test
    public void testCheckErrorsValid(){
        // make sure null (no errors) are returned.

        String brandChoice = "Volkswagen";
        String typeChoice = "Car";
        String locationChoice = "Plymouth";
        String modelChoice = "Golf";
        String colourChoice = "Grey";
        String yearChoice = "2004";

        assertNull(Validation.checkErrors(
                brandChoice, typeChoice, locationChoice,
                modelChoice, colourChoice, yearChoice
        ));
    }

    @Test
    public void testCheckErrorsInvalidAllEmpty(){
        // make sure all error lines are built

        String brandChoice = null;
        String typeChoice = null;
        String locationChoice = null;
        String modelChoice = "";
        String colourChoice = "";
        String yearChoice = "";

        String expectedError = """
                There are problems with some information in your vehicle. Please fix the following and try again:
                
                - Vehicle Brand
                - Vehicle Type
                - Vehicle Location
                - Vehicle Model
                - Vehicle Colour
                - Vehicle Model year
                """;

        assertEquals(Validation.checkErrors(
                brandChoice, typeChoice, locationChoice,
                modelChoice, colourChoice, yearChoice), expectedError);
    }

    @Test
    public void testCheckErrorsInvalidTextYear(){
        // make sure parseInt fails when text is entered

        String brandChoice = "Brand";
        String typeChoice = "Car";
        String locationChoice = "Plymouth";
        String modelChoice = "Model";
        String colourChoice = "Colour";
        String yearChoice = "normally a number";

        String expectedError = """
                There are problems with some information in your vehicle. Please fix the following and try again:
                
                - Vehicle Model year
                """;

        assertEquals(Validation.checkErrors(
                brandChoice, typeChoice, locationChoice,
                modelChoice, colourChoice, yearChoice), expectedError);
    }
}
