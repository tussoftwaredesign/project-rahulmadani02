package domain;

public class Car extends VehicleBase implements Vehicle {
    private final String licensePlate;
    private final String color;

    public Car(String licensePlate) {
        this(licensePlate, "Unknown"); // Calls another constructor
    }

    public Car(String licensePlate, String color) {
        super("Car"); // Call to the parent class constructor
        this.licensePlate = licensePlate; // Refers to the instance variable
        this.color = color;
    }

    @Override
    public String getType() {
        return super.getType() + " (License Plate: " + licensePlate + ")"; // Use of super.
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Car{" +
                "licensePlate='" + licensePlate + '\'' +
                ", color='" + color + '\'' +
                ", type='" + super.getType() + '\'' + // Use of super.getType()
                '}';
    }
}
