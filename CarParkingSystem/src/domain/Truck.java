package domain;

public class Truck extends VehicleBase implements Vehicle {
    private final int capacity;

    public Truck(int capacity) {
        super("Truck"); // Call to the parent class constructor
        this.capacity = capacity;
    }

    @Override
    public String getType() {
        return super.getType() + " (Capacity: " + capacity + "kg)"; // Use of super.
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "capacity=" + capacity +
                ", type='" + super.getType() + '\'' + // Use of super.getType()
                '}';
    }
}
