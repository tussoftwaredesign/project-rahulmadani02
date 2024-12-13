package domain;

public class VehicleBase {
    private final String type;

    public VehicleBase(String type) {
        this.type = type; // Initialize type using super()
    }

    public String getType() {
        return type; // Shared method for all vehicles
    }

    @Override
    public String toString() {
        return "VehicleBase{" +
                "type='" + type + '\'' +
                '}';
    }
}
