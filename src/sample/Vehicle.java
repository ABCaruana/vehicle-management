package sample;

public class Vehicle {
    private final int id;
    private String vehicleBrand;
    private String vehicleModel;
    private String vehicleType;
    private String vehicleColour;
    private String vehicleLocation;
    private int vehicleYear;

    public Vehicle(int id, String vehicleBrand, String vehicleModel, String vehicleType, String vehicleColour, String vehicleLocation, int vehicleYear){
        this.id = id;
        this.vehicleBrand = vehicleBrand;
        this.vehicleModel = vehicleModel;
        this.vehicleType = vehicleType;
        this.vehicleColour = vehicleColour;
        this.vehicleLocation = vehicleLocation;
        this.vehicleYear = vehicleYear;
    }

    // getters
    public int getId() { return id; }
    public String getVehicleModel() { return vehicleModel; }
    public String getVehicleType() { return vehicleType; }
    public String getVehicleColour() { return vehicleColour; }
    public String getVehicleLocation() { return vehicleLocation; }
    public int getVehicleYear() { return vehicleYear; }

    public String getVehicleBrand() { return vehicleBrand; }

    // setters
    public void setVehicleModel(String vehicleModel) { this.vehicleModel = vehicleModel; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public void setVehicleColour(String vehicleColour) { this.vehicleColour = vehicleColour; }
    public void setVehicleLocation(String vehicleLocation) { this.vehicleLocation = vehicleLocation; }
    public void setVehicleYear(int vehicleYear) { this.vehicleYear = vehicleYear; }
    public void setVehicleBrand(String vehicleBrand) { this.vehicleBrand = vehicleBrand; }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", brand='" + vehicleBrand + '\'' +
                ", model='" + vehicleModel + '\'' +
                ", type='" + vehicleType + '\'' +
                ", colour='" + vehicleColour + '\'' +
                ", location='" + vehicleLocation + '\'' +
                ", year=" + vehicleYear +
                '}';
    }
}
