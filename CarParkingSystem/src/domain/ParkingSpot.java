package domain;

import util.ParkingSpotType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ParkingSpot {
    private final int spotNumber;
    private final ParkingSpotType type;
    private boolean isOccupied;
    private LocalDateTime parkedAt;
    private Vehicle currentVehicle;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ParkingSpot(int spotNumber, ParkingSpotType type) {
        this.spotNumber = spotNumber;
        this.type = type;
        this.isOccupied = false;
        this.parkedAt = null;
        this.currentVehicle = null;
    }

    public void parkVehicle(Vehicle vehicle) {
        if (isOccupied) {
            throw new IllegalStateException("Spot " + spotNumber + " is already occupied by a " + currentVehicle.getType());
        }
        this.currentVehicle = vehicle;
        this.isOccupied = true;
        this.parkedAt = LocalDateTime.now();
        System.out.println(vehicle.getType() + " has been parked at spot " + spotNumber + " (" + type + ") at " + getFormattedParkedAt());
    }

    public void vacateSpot() {
        if (!isOccupied) {
            throw new IllegalStateException("Spot " + spotNumber + " is already vacant!");
        }
        System.out.println("Vacating spot " + spotNumber + " which was occupied by a " + currentVehicle.getType());
        this.currentVehicle = null;
        this.isOccupied = false;
        this.parkedAt = null;
    }

    public String getFormattedParkedAt() {
        return parkedAt != null ? parkedAt.format(FORMATTER) : "N/A";
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public ParkingSpotType getType() {
        return type;
    }

    public Vehicle getCurrentVehicle() {
        return currentVehicle;
    }

    @Override
    public String toString() {
        return "ParkingSpot{" +
                "spotNumber=" + spotNumber +
                ", type=" + type +
                ", isOccupied=" + isOccupied +
                ", parkedAt=" + getFormattedParkedAt() +
                ", currentVehicle=" + (currentVehicle != null ? currentVehicle.getType() : "None") +
                '}';
    }
}
