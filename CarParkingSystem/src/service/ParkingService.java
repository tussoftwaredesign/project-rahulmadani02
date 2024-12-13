package service;

import domain.Car;
import domain.ParkingSpot;
import domain.Truck;
import exceptions.ParkingFullException;
import exceptions.UnauthorizedAccessException;
import util.ParkingSpotType;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class ParkingService {
    private final ParkingSpot[] parkingSpots;

    public ParkingService(int capacity) {
        parkingSpots = new ParkingSpot[capacity]; // Initialize the array with a fixed capacity
        for (int i = 0; i < capacity; i++) {
            // Assigning ParkingSpotType dynamically
            ParkingSpotType type = (i % 3 == 0) ? ParkingSpotType.HANDICAPPED :
                    (i % 2 == 0) ? ParkingSpotType.LARGE :
                            ParkingSpotType.COMPACT;
            parkingSpots[i] = new ParkingSpot(i + 1, type); // Add ParkingSpot to the array
        }
    }

    // Polymorphism examples
    public void parkVehicle(Object vehicle) {
        System.out.println("Parking a generic vehicle.");
    }

    public void parkVehicle(Car car) {
        System.out.println("Parking a car: " + car.getLicensePlate());
    }

    public void parkVehicle(Truck truck) {
        System.out.println("Parking a truck with capacity: " + truck.getCapacity());
    }

    // Method to park a car, throws a checked exception if no spots are available
    public void parkCar() throws ParkingFullException {
        boolean hasEmptySpot = Stream.of(parkingSpots).anyMatch(spot -> !spot.isOccupied());
        if (!hasEmptySpot) {
            throw new ParkingFullException("No parking spots available!");
        }
        System.out.println("Car parked successfully.");
    }

    // Method using Predicate to filter parking spots
    public Stream<ParkingSpot> filterParkingSpots(Predicate<ParkingSpot> condition) {
        return Stream.of(parkingSpots) // Convert the array to a Stream
                .filter(condition); // Use Predicate to filter based on the condition
    }

    // New Method: Authorize users
    public void authorizeUser(String userRole) {
        if (!"ADMIN".equals(userRole)) {
            throw new UnauthorizedAccessException("Access denied for role: " + userRole);
        }
        System.out.println("Access granted for role: " + userRole);
    }
}
