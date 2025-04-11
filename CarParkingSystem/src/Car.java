/**
 * 
 * @author 
 * @version 5.2.1
 */
import java.time.LocalDateTime;

/**
 * Represents a Car with registration number, make, model, year, and parking time.
 */
public class Car {
    private String registrationNumber; // Car registration number
    private String make; // Car make
    private String model; // Car model
    private int year; // Car year
    private LocalDateTime parkingTime; // Time when the car was parked

    /**
     * Constructs a Car object with the specified details.
     *
     * @param registrationNumber the registration number of the car
     * @param make the make of the car
     * @param model the model of the car
     * @param year the year of the car
     */
    public Car(String registrationNumber, String make, String model, int year) {
        this.registrationNumber = registrationNumber;
        this.make = make;
        this.model = model;
        this.year = year;
        this.parkingTime = LocalDateTime.now(); // Set parking time to the current time
    }

    /**
     * Gets the registration number of the car.
     *
     * @return the registration number of the car
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Gets the make of the car.
     *
     * @return the make of the car
     */
    public String getMake() {
        return make;
    }

    /**
     * Gets the model of the car.
     *
     * @return the model of the car
     */
    public String getModel() {
        return model;
    }

    /**
     * Gets the year of the car.
     *
     * @return the year of the car
     */
    public int getYear() {
        return year;
    }

    /**
     * Gets the time when the car was parked.
     *
     * @return the parking time
     */
    public LocalDateTime getParkingTime() {
        return parkingTime;
    }

    /**
     * Sets the parking time to the current time.
     */
    public void setParkingTime() {
        this.parkingTime = LocalDateTime.now();
    }
}





