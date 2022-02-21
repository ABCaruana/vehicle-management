package sample;

public class Vehicle {
    private final int id;
    private String brand;
    private String model;
    private String type;
    private String colour;
    private String location;
    private int year;

    public Vehicle(int id, String brand, String model, String type, String colour, String location, int year){
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.colour = colour;
        this.location = location;
        this.year = year;
    }

    // getters
    public int getId() { return id; }
    public String getModel() { return model; }
    public String getType() { return type; }
    public String getColour() { return colour; }
    public String getLocation() { return location; }
    public int getYear() { return year; }

    public String getBrand() { return brand; }

    // setters
    public void setModel(String model) { this.model = model; }
    public void setType(String type) { this.type = type; }
    public void setColour(String colour) { this.colour = colour; }
    public void setLocation(String location) { this.location = location; }
    public void setYear(int year) { this.year = year; }
    public void setBrand(String brand) { this.brand = brand; }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", colour='" + colour + '\'' +
                ", location='" + location + '\'' +
                ", year=" + year +
                '}';
    }
}
