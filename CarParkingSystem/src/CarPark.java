/**
 * 
 * @author 
 * @version 5.2.1
 */
import java.util.HashMap;
import java.util.Map;

/**
 * Manages a car park with multiple parking spots.
 */
public class CarPark {
    // Map to store parking spots by their IDs
    private Map<String, ParkingSpot> spots;

    /**
     * Constructs a CarPark object with an empty list of parking spots.
     */
    public CarPark() {
        spots = new HashMap<>();
    }

    /**
     * Finds and returns a parking spot by its ID.
     *
     * @param id the ID of the parking spot
     * @return the ParkingSpot object if found, null otherwise
     */
    public ParkingSpot findSpot(String id) {
        return spots.get(id);
    }

    /**
     * Parks a car in a specified parking spot if it's not already occupied.
     *
     * @param spotId the ID of the parking spot
     * @param car the Car object to be parked
     * @return true if the car was parked successfully, false otherwise
     */
    public boolean parkCar(String spotId, Car car) {
        if (isCarInPark(car.getRegistrationNumber())) {
            return false; // Car with the same registration number already parked
        }
        ParkingSpot spot = spots.get(spotId);
        if (spot != null && !spot.isOccupied()) {
            spot.parkCar(car); // Park the car in the spot
            return true;
        }
        return false; // Spot is either null or already occupied
    }

    /**
     * Checks if a car with a given registration number is already in the car park.
     *
     * @param regNumber the registration number of the car
     * @return true if the car is already in the park, false otherwise
     */
    public boolean isCarInPark(String regNumber) {
        for (ParkingSpot spot : spots.values()) {
            if (spot.isOccupied() && spot.getCar().getRegistrationNumber().equals(regNumber)) {
                return true; // Car found in the park
            }
        }
        return false; // Car not found in the park
    }

    /**
     * Removes a car from a specified parking spot by its ID.
     *
     * @param spotId the ID of the parking spot
     * @return true if the car was removed successfully, false otherwise
     */
    public boolean removeCarBySpot(String spotId) {
        ParkingSpot spot = spots.get(spotId);
        if (spot != null && spot.isOccupied()) {
            spot.removeCar(); // Remove the car from the spot
            return true;
        }
        return false; // Spot is either null or not occupied
    }

    /**
     * Removes a car from the car park by its registration number.
     *
     * @param regNumber the registration number of the car
     * @return true if the car was removed successfully, false otherwise
     */
    public boolean removeCarByRegNumber(String regNumber) {
        for (ParkingSpot spot : spots.values()) {
            if (spot.isOccupied() && spot.getCar().getRegistrationNumber().equals(regNumber)) {
                spot.removeCar(); // Remove the car from the spot
                return true;
            }
        }
        return false; // Car not found in the park
    }

    /**
     * Finds and returns a parking spot containing a car by the car's registration number.
     *
     * @param regNumber the registration number of the car
     * @return the ParkingSpot object if found, null otherwise
     */
    public ParkingSpot findCarByRegNumber(String regNumber) {
        for (ParkingSpot spot : spots.values()) {
            if (spot.isOccupied() && spot.getCar().getRegistrationNumber().equals(regNumber)) {
                return spot; // Spot containing the car found
            }
        }
        return null; // Car not found in any spot
    }

    /**
     * Finds and returns all parking spots containing cars of a specific make.
     *
     * @param make the make of the car
     * @return a map of parking spots containing cars of the specified make
     */
    public Map<String, ParkingSpot> findCarsByMake(String make) {
        Map<String, ParkingSpot> result = new HashMap<>();
        for (ParkingSpot spot : spots.values()) {
            if (spot.isOccupied() && spot.getCar().getMake().equalsIgnoreCase(make)) {
                result.put(spot.getId(), spot); // Add spot to the result map
            }
        }
        return result; // Return all spots containing cars of the specified make
    }

    /**
     * Adds a new parking spot to the car park.
     *
     * @param spotId the ID of the new parking spot
     * @return true if the spot was added successfully, false otherwise
     */
    public boolean addSpot(String spotId) {
        if (spots.containsKey(spotId)) {
            return false; // Spot with the same ID already exists
        }
        spots.put(spotId, new ParkingSpot(spotId)); // Add new spot to the map
        return true;
    }

    /**
     * Deletes a parking spot from the car park if it's not occupied.
     *
     * @param spotId the ID of the parking spot to delete
     * @return true if the spot was deleted successfully, false otherwise
     */
    public boolean deleteSpot(String spotId) {
        ParkingSpot spot = spots.get(spotId);
        if (spot != null && !spot.isOccupied()) {
            spots.remove(spotId); // Remove the spot from the map
            return true;
        }
        return false; // Spot is either null or occupied
    }

    /**
     * Resets the car park by removing all cars from the spots.
     */
    public void resetCarPark() {
        for (ParkingSpot spot : spots.values()) {
            if (spot.isOccupied()) {
                spot.removeCar(); // Remove the car from each spot
            }
        }
    }

    /**
     * Gets all parking spots in the car park.
     *
     * @return a map of all parking spots
     */
    public Map<String, ParkingSpot> getSpots() {
        return spots;
    }

    /**
     * Gets the total number of parking spots in the car park.
     *
     * @return the total number of spots
     */
    public int getTotalSpots() {
        return spots.size();
    }

    /**
     * Gets the number of occupied parking spots in the car park.
     *
     * @return the number of occupied spots
     */
    public int getOccupiedSpots() {
        int count = 0;
        for (ParkingSpot spot : spots.values()) {
            if (spot.isOccupied()) {
                count++; // Increment count for each occupied spot
            }
        }
        return count;
    }

    /**
     * Gets the number of unoccupied parking spots in the car park.
     *
     * @return the number of unoccupied spots
     */
    public int getUnoccupiedSpots() {
        return getTotalSpots() - getOccupiedSpots();
    }
}

