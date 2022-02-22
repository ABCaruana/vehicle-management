package VehicleManagement;

import java.util.ArrayList;

public class Validation {
        public static String checkErrors(String brandChoice, String typeChoice,
                                         String locationChoice, String modelChoice,
                                         String colourChoice, String yearChoice) {
            ArrayList<String> errorFields = new ArrayList<>();

            // make sure all of our data doesnt fall into these criteria
            // add to our list of errors if they do
            if (brandChoice == null) {
                errorFields.add("Vehicle Brand");
            }
            if (typeChoice == null) {
                errorFields.add("Vehicle Type");
            }
            if (locationChoice == null) {
                errorFields.add("Vehicle Location");
            }
            if (modelChoice.equals("")) {
                errorFields.add("Vehicle Model");
            }
            if (colourChoice.equals("")) {
                errorFields.add("Vehicle Colour");
            }
            try {
                Integer.parseInt(yearChoice);
            }
            catch (NumberFormatException numberFormatException){
                errorFields.add("Vehicle Model year");
            }

            if (errorFields.size() >= 1) {
                // create a list of errors
                StringBuilder finalErrorMessage = new StringBuilder("There are problems with some information in your vehicle. Please fix the following and try again:\n\n");
                for (String error : errorFields) {
                    finalErrorMessage.append("- ").append(error).append("\n");
                }
                return finalErrorMessage.toString();
            }
            return null;
        }
}
