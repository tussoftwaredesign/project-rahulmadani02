package main;

import domain.Car;
import domain.Owner;
import domain.Truck;
import domain.ParkingSpot;
import service.ParkingService;
import util.ParkingSpotType;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // 1. Encapsulation
        var car = new Car("ABC-123", "Red"); // Using var
        var truck = new Truck(5000); // Using var

        // 2. Record Example
        var owner = new Owner("John Doe", "1234567890"); // Using var
        System.out.println("Owner: " + owner);

        // 3. Polymorphism and ParkingService
        var service = new ParkingService(6); // Using var

        // Role-Based Access Control: UnauthorizedAccessException demonstration
        System.out.println("\nAttempting to access the system as a GUEST:");
        try {
            service.authorizeUser("GUEST"); // Throws UnauthorizedAccessException
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.out.println("\nAttempting to access the system as an ADMIN:");
        try {
            service.authorizeUser("ADMIN"); // Access granted
            System.out.println("Authorized user. Proceeding with operations...");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // Vehicle Parking Operations
        service.parkVehicle(car); // Polymorphism
        service.parkVehicle(truck);

        // 4. Display all parking spots and their types (using Arrays)
        System.out.println("\nAll parking spots with their types:");
        var allSpots = service.filterParkingSpots(spot -> true).toArray(ParkingSpot[]::new); // Convert to array
        Arrays.stream(allSpots).forEach(spot -> System.out.println("Spot " + spot.getSpotNumber() + " is of type " + spot.getType()));

        // 5. Filter and display available HANDICAPPED spots (using Arrays)
        System.out.println("\nAvailable HANDICAPPED spots:");
        var handicappedSpots = service.filterParkingSpots(spot ->
                spot.getType() == ParkingSpotType.HANDICAPPED && !spot.isOccupied()).toArray(ParkingSpot[]::new); // Convert to array
        Arrays.stream(handicappedSpots).forEach(spot -> System.out.println("Spot " + spot.getSpotNumber()));

        // 6. Park a car in a HANDICAPPED spot and log the time with formatted timestamp
        if (handicappedSpots.length > 0) {
            var handicappedSpot = handicappedSpots[0]; // Using array indexing
            try {
                handicappedSpot.parkVehicle(car); // Correct method call
                System.out.println("Car parked at Spot " + handicappedSpot.getSpotNumber() +
                        " (" + handicappedSpot.getType() + ") at " + handicappedSpot.getFormattedParkedAt());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        // 7. Checked Exception Handling
        try {
            service.parkCar(); // Attempt to park a car
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // 8. Filtering available parking spots with Predicate (using Arrays)
        System.out.println("\nFiltering available parking spots:");
        var availableSpots = service.filterParkingSpots(spotObj -> !spotObj.isOccupied()).toArray(ParkingSpot[]::new); // Convert to array
        Arrays.stream(availableSpots).forEach(spot -> System.out.println("Available Spot: " + spot.getSpotNumber()));

        // 9. Filtering spots with Spot Number > 2 (using Arrays)
        System.out.println("\nFiltering spots with Spot Number > 2:");
        var filteredSpots = service.filterParkingSpots(spotObj -> spotObj.getSpotNumber() > 2).toArray(ParkingSpot[]::new); // Convert to array
        Arrays.stream(filteredSpots).forEach(System.out::println); // Using Method Reference
    }
}
