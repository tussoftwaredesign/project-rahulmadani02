/**
 *
 * @author 
 * @version 5.2.1
 */
/**
 * Represents a parking spot in a car park.
 */
public class ParkingSpot {
    private String id; // ID of the parking spot
    private Car car; // Car parked in the spot, null if the spot is vacant

    /**
     * Constructs a ParkingSpot object with the specified ID.
     *
     * @param id the ID of the parking spot
     */
    public ParkingSpot(String id) {
        this.id = id;
        this.car = null; // Initially, the spot is vacant
    }

    /**
     * Gets the ID of the parking spot.
     *
     * @return the ID of the parking spot
     */
    public String getId() {
        return id;
    }

    /**
     * Checks if the parking spot is occupied.
     *
     * @return true if the spot is occupied, false otherwise
     */
    public boolean isOccupied() {
        return car != null;
    }

    /**
     * Gets the car parked in the spot.
     *
     * @return the car parked in the spot, null if the spot is vacant
     */
    public Car getCar() {
        return car;
    }

    /**
     * Parks a car in the parking spot.
     *
     * @param car the car to be parked in the spot
     */
    public void parkCar(Car car) {
        this.car = car; // Set the car for the spot
    }

    /**
     * Removes the car from the parking spot, making it vacant.
     */
    public void removeCar() {
        this.car = null; // Set the car to null, indicating the spot is vacant
    }
}

