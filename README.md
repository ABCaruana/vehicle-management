# Vehicle Management system
## dependencies
These are the versions for software that I personally used. Higher versions may work, too.
- Java openJDK 15.0.1
- JavaFX 11.0.2
- Maven
  - Gson (com.google.code.gson:gson:2.9.0)
  - JSON (org.json:json:20211205)

## Running The application
1. make sure all the above dependencies are installed/linked to the project (should already be zipped here)
2. add VM options in intelliJ if they are not already present.
   - `--module-path <JAVAFX /lib PATH HERE> --add-modules javafx.controls,javafx.fxml`
3. Build and run the `Main` class.
4. follow all [tutorials](#tutorials) below

## Tutorials
### How to add a new Vehicle

1. Select the location of garage that you wish to view (you can change this by closing the current table and checking new location boxes)
2. Press the vehicle with the green plus (![](src/ui/add.png)) to open the "Add new vehicle" menu.
3. fill in all details and press "Add"
   - if some details are empty or invalid, a warning will appear

### How to edit a Vehicle
Note: Only one vehicle can be edited at a time.
1. Select the garage of the vehicle that you wish to edit
2. Press the paper with the pencil (![](src/ui/edit.png)) to open the "Edit vehicle" menu.
3. fill in all details and press "update"
   - if some details are empty or invalid, a warning will appear similar to the "Add new vehicle" menu.

###  How to delete a vehicle
1. Select the garage(s) of the vehicle(s) that you wish to delete
2. Press the vehicle with the red minus (![](src/ui/delete.png)) to delete the selected vehicle(s).
   - You can click on multiple vehicles by holding CTRL while clicking
3. Click "Yes" on the confirmation dialog.
   - Deleting vehicle(s) is a permanent action and cannot be undone.

### How to refresh the databaase
Some data changes (updating vehicles, adding new vehicles) requires the data to be refreshed. To do this:
1. Select the garage(s) of the updated data that you wish to view
2. Press the green arrows (![](src/ui/refresh.png)) to fetch the new data.

### Updating Configuration values
I have programmed the dialogs for adding and updating vehicles to take custom brands, simply:
1. open `vehicleConfig.json`
2. find the category you wish to update (brands, vehicle types, locations)
3. add new strings in the same format as the existing data
4. restart the application.

Now, new data should appear in the drop-down boxes when adding or updating vehicles.
## Example - data structure for vehicles (for manual data entry)

```json
{
    "data": [{
        "brand": "Audi, Volkswagen etc.",
        "model": "RS7, Golf etc.",
        "type": "Car/Motorcycle",
        "year": 1974,
        "colour": "White",
        "location": "Plymouth"
    }]
}
```

## Testing
As the frontend and backend are closely integrated, the tests are heavily focused around manual testing. There are still some unit tests for the main Vehicle class.

### Manual Testing
| test number | expected output | actual output | status |
|-------------|-----------------|---------------|--------|
|1|When i select multiple vehicles, the edit button disables|Same as expected|PASS|
|2|When i select a single vehicle after selecting multiple, the edit button re-enables|Same as expected|PASS|
|3|When i try to delete a vehicle with no selection, a error dialog pops up|Same as expected|PASS|
|4|When i try to enter a year that is not numeric, i will not be able to continue|Same as expected|PASS|
|5|Selecting all bikes selects all "Motorcycle" vehicles in the table.|Same as expected|PASS|
|6|Selecting all cars selects all "Car" vehicles in the table.|Same as expected|PASS|
|7|Selecting all vehicles selects all Motorcycles and Cars.|Same as expected|PASS|
|8|deselecting all vehicles clears the entire selection.|Same as expected|PASS|
|9|deleting a single vehicle removes it from the table and data file.|Same as expected|PASS|
|10|deleting multiple vehicles removes them from the table and data file.|Same as expected|PASS|
|11|Selecting X location(s) only shows vehicles from those locations (repeated for every combination).|Same as expected|PASS|

## retrospective notes

As this task came second to day-to-day work there were some features that i was not able to add during development (that I would have added in the EPA). These include:
- More error dialogs
  - as the software itself is simple there are not many points of error which means that the odds of an error occuring are small. Adding these with more time however would make the software more robust.
- Database connections
  - with more time i would have been able to create a normalised database structure to more efficiently manage my data.
- efficient I/O
  - there are a lot of saves occuring when data is modified (especially batch deleting vehicles), spending tome to optimise this in the future would be valuable for the longevity of storage drives holding the data
- more testable code
  - in the future, abstracting as much as i can away from methods that interact with javaFX stages would make my code more automatable, which will be quicker and require less manual tests.
