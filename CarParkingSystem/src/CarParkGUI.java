/**
 * 
 * @author 
 * @version 5.2.1
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Represents the GUI application for managing a car park.
 */
public class CarParkGUI extends JFrame {
    private JPanel parkingPanel; // Panel for displaying parking spots
    private CarPark carPark; // Car park management system
    private Map<String, JButton> spotButtons; // Map of spot buttons by their IDs

    /**
     * Constructs the CarParkGUI.
     */
    public CarParkGUI() {
        carPark = new CarPark();
        spotButtons = new HashMap<>();

        setTitle("Parking Spot System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.DARK_GRAY);
        JLabel titleLabel = new JLabel("Parking Spot System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Left panel with control buttons
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(9, 1, 10, 10));
        controlPanel.setBackground(Color.DARK_GRAY);

        String[] buttonLabels = {
            "1. Add a parking spot", "2. Delete a parking spot", "3. List all parking spots",
            "4. Park a car", "5. Find car by registration number", "6. Remove car by registration number",
            "7. Find cars by make", "8. Reset car park", "9. Exit"
        };
        JButton[] controlButtons = new JButton[buttonLabels.length];
        for (int i = 0; i < buttonLabels.length; i++) {
            controlButtons[i] = new JButton(buttonLabels[i]);
            controlButtons[i].setBackground(Color.BLUE);
            controlButtons[i].setForeground(Color.WHITE);
            controlButtons[i].setFocusPainted(false);
            controlButtons[i].setFont(new Font("Arial", Font.BOLD, 14));
            controlPanel.add(controlButtons[i]);
        }

        // Attach listeners to buttons
        controlButtons[0].addActionListener(new AddSpotButtonListener());
        controlButtons[1].addActionListener(new DeleteSpotButtonListener());
        controlButtons[2].addActionListener(new ListSpotsButtonListener());
        controlButtons[3].addActionListener(new ParkButtonListener());
        controlButtons[4].addActionListener(new FindCarButtonListener());
        controlButtons[5].addActionListener(new RemoveCarButtonListener());
        controlButtons[6].addActionListener(new FindCarsByMakeButtonListener());
        controlButtons[7].addActionListener(new ResetCarParkButtonListener());
        controlButtons[8].addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Program ends!");
            System.exit(0);
        });

        // Parking panel for displaying parking spots
        parkingPanel = new JPanel();
        parkingPanel.setLayout(new GridLayout(3, 5, 10, 10));
        parkingPanel.setBackground(Color.BLUE);

        for (String spotId : carPark.getSpots().keySet()) {
            JButton spotButton = new JButton("<html>" + spotId + "<br>Vacant</html>");
            spotButton.setName(spotId);
            spotButton.setBackground(Color.LIGHT_GRAY);
            spotButton.addActionListener(new SpotInteractionListener());
            parkingPanel.add(spotButton);
            spotButtons.put(spotId, spotButton);
        }

        add(titlePanel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.WEST);
        add(parkingPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Listener for handling interactions with parking spots.
     */
    private class SpotInteractionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String spotId = source.getName();
            ParkingSpot spot = carPark.findSpot(spotId);
            if (spot.isOccupied()) {
                int option = JOptionPane.showConfirmDialog(null, "Do you want to remove the car from spot " + spotId + "?", "Remove Car", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    carPark.removeCarBySpot(spotId);
                    source.setText("<html>" + spotId + "<br>Vacant</html>");
                    source.setBackground(Color.LIGHT_GRAY);
                }
            } else {
                String regNumber = showInputDialog("Enter car registration number (e.g., A1234):");
                if (regNumber == null || !regNumber.matches("[A-Z]\\d{4}")) {
                    if (regNumber != null) {
                        JOptionPane.showMessageDialog(null, "Invalid registration number format. Must be an uppercase letter followed by 4 digits.");
                    }
                    return;
                }
                if (carPark.isCarInPark(regNumber)) {
                    JOptionPane.showMessageDialog(null, "A car with this registration number is already parked.");
                    return;
                }
                String make = showInputDialog("Enter car make:");
                if (make == null) return;
                String model = showInputDialog("Enter car model:");
                if (model == null) return;
                String yearStr = showInputDialog("Enter car year (2004-2024):");
                if (yearStr == null) return;
                int year;
                try {
                    year = Integer.parseInt(yearStr);
                    if (year < 2004 || year > 2024) {
                        JOptionPane.showMessageDialog(null, "Invalid year. Must be between 2004 and 2024.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid year. Must be a number between 2004 and 2024.");
                    return;
                }

                Car car = new Car(regNumber, make, model, year);

                if (carPark.parkCar(spotId, car)) {
                    source.setBackground(Color.YELLOW);
                    source.setText("<html>" + spotId + "<br>Registration: " + regNumber + "<br>" + make + " " + year + "</html>");
                    JOptionPane.showMessageDialog(null, "Car parked in spot " + spotId);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to park car. Spot might be occupied or does not exist.");
                }
            }
        }
    }

    /**
     * Listener for adding a new parking spot.
     */
    private class AddSpotButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String spotId = showInputDialog("Enter new spot ID (e.g., A001):");
            if (spotId == null || !spotId.matches("[A-Z]\\d{3}")) {
                if (spotId != null) {
                    JOptionPane.showMessageDialog(null, "Invalid spot ID format. Must be an uppercase letter followed by 3 digits.");
                }
                return;
            }
            if (carPark.addSpot(spotId)) {
                JButton spotButton = new JButton("<html>" + spotId + "<br>Vacant</html>");
                spotButton.setName(spotId);
                spotButton.setBackground(Color.LIGHT_GRAY);
                spotButton.addActionListener(new SpotInteractionListener());
                parkingPanel.add(spotButton);
                spotButtons.put(spotId, spotButton);
                parkingPanel.revalidate();
                parkingPanel.repaint();
                JOptionPane.showMessageDialog(null, "New spot " + spotId + " added.");
            } else {
                JOptionPane.showMessageDialog(null, "Spot " + spotId + " already exists.");
            }
        }
    }

    /**
     * Listener for deleting a parking spot.
     */
    private class DeleteSpotButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String spotId = showInputDialog("Enter spot ID to delete:");
            if (spotId == null || !spotId.matches("[A-Z]\\d{3}")) {
                if (spotId != null) {
                    JOptionPane.showMessageDialog(null, "Invalid spot ID format. Must be an uppercase letter followed by 3 digits.");
                }
                return;
            }
            if (carPark.deleteSpot(spotId)) {
                JButton spotButton = spotButtons.remove(spotId);
                parkingPanel.remove(spotButton);
                parkingPanel.revalidate();
                parkingPanel.repaint();
                JOptionPane.showMessageDialog(null, "Spot " + spotId + " deleted.");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete spot. Spot might be occupied or does not exist.");
            }
        }
    }

    /**
     * Listener for listing all parking spots.
     */
    private class ListSpotsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringBuilder sb = new StringBuilder("List of Parking Spots\n--------------------\n");
            for (ParkingSpot spot : carPark.getSpots().values()) {
                sb.append("Spot ").append(spot.getId())
                        .append(": ").append(spot.isOccupied() ? "Occupied by " + spot.getCar().getRegistrationNumber() + " (" + spot.getCar().getMake() + ")" : "Vacant")
                        .append("\n");
                if (spot.isOccupied()) {
                    Duration duration = Duration.between(spot.getCar().getParkingTime(), LocalDateTime.now());
                    long hours = duration.toHours();
                    long minutes = duration.toMinutes() % 60;
                    long seconds = duration.getSeconds() % 60;
                    sb.append("  Parking time: ").append(hours).append(" hours ")
                            .append(minutes).append(" minutes ").append(seconds).append(" seconds\n");
                }
            }
            sb.append("\nSummary\n--------------------\n")
                    .append("Total spots: ").append(carPark.getTotalSpots()).append("\n")
                    .append("Occupied spots: ").append(carPark.getOccupiedSpots()).append("\n")
                    .append("Available spots: ").append(carPark.getUnoccupiedSpots()).append("\n");
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }

    /**
     * Listener for parking a car in a spot.
     */
    private class ParkButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String spotId = showInputDialog("Enter spot ID:");
            if (spotId == null || !spotId.matches("[A-Z]\\d{3}")) {
                if (spotId != null) {
                    JOptionPane.showMessageDialog(null, "Invalid spot ID format. Must be an uppercase letter followed by 3 digits.");
                }
                return;
            }
            String regNumber = showInputDialog("Enter car registration number (e.g., A1234):");
            if (regNumber == null || !regNumber.matches("[A-Z]\\d{4}")) {
                if (regNumber != null) {
                    JOptionPane.showMessageDialog(null, "Invalid registration number format. Must be an uppercase letter followed by 4 digits.");
                }
                return;
            }
            if (carPark.isCarInPark(regNumber)) {
                JOptionPane.showMessageDialog(null, "A car with this registration number is already parked.");
                return;
            }
            String make = showInputDialog("Enter car make:");
            if (make == null) return;
            String model = showInputDialog("Enter car model:");
            if (model == null) return;
            String yearStr = showInputDialog("Enter car year (2004-2024):");
            if (yearStr == null) return;
            int year;
            try {
                year = Integer.parseInt(yearStr);
                if (year < 2004 || year > 2024) {
                    JOptionPane.showMessageDialog(null, "Invalid year. Must be between 2004 and 2024.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid year. Must be a number between 2004 and 2024.");
                return;
            }

            Car car = new Car(regNumber, make, model, year);

            if (carPark.parkCar(spotId, car)) {
                JButton spotButton = spotButtons.get(spotId);
                spotButton.setBackground(Color.YELLOW);
                spotButton.setText("<html>" + spotId + "<br>Registration: " + regNumber + "<br>" + make + " " + year + "</html>");
                JOptionPane.showMessageDialog(null, "Car parked in spot " + spotId);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to park car. Spot might be occupied or does not exist.");
            }
        }
    }

    /**
     * Listener for finding a car by its registration number.
     */
    private class FindCarButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String regNumber = showInputDialog("Enter car registration number to find:");
            if (regNumber == null || !regNumber.matches("[A-Z]\\d{4}")) {
                if (regNumber != null) {
                    JOptionPane.showMessageDialog(null, "Invalid registration number format. Must be an uppercase letter followed by 4 digits.");
                }
                return;
            }
            ParkingSpot spot = carPark.findCarByRegNumber(regNumber);
            if (spot != null) {
                Duration duration = Duration.between(spot.getCar().getParkingTime(), LocalDateTime.now());
                long hours = duration.toHours();
                long minutes = duration.toMinutes() % 60;
                long seconds = duration.getSeconds() % 60;
                JOptionPane.showMessageDialog(null, "Car with registration number " + regNumber + " found in spot: " + spot.getId() + "\nParking time: " + hours + " hours " + minutes + " minutes " + seconds + " seconds");
            } else {
                JOptionPane.showMessageDialog(null, "Car with registration number " + regNumber + " not found.");
            }
        }
    }

    /**
     * Listener for removing a car by its registration number.
     */
    private class RemoveCarButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String regNumber = showInputDialog("Enter car registration number to remove:");
            if (regNumber == null || !regNumber.matches("[A-Z]\\d{4}")) {
                if (regNumber != null) {
                    JOptionPane.showMessageDialog(null, "Invalid registration number format. Must be an uppercase letter followed by 4 digits.");
                }
                return;
            }
            if (carPark.removeCarByRegNumber(regNumber)) {
                for (JButton button : spotButtons.values()) {
                    if (button.getText().contains(regNumber)) {
                        button.setBackground(Color.LIGHT_GRAY);
                        button.setText("<html>" + button.getName() + "<br>Vacant</html>");
                    }
                }
                JOptionPane.showMessageDialog(null, "Car with registration number " + regNumber + " removed.");
            } else {
                JOptionPane.showMessageDialog(null, "Car with registration number " + regNumber + " not found.");
            }
        }
    }

    /**
     * Listener for finding cars by their make.
     */
    private class FindCarsByMakeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String make = showInputDialog("Enter car make to find:");
            if (make == null) return;
            Map<String, ParkingSpot> foundCars = carPark.findCarsByMake(make);
            if (!foundCars.isEmpty()) {
                StringBuilder sb = new StringBuilder("Cars with make " + make + ":\n");
                for (Map.Entry<String, ParkingSpot> entry : foundCars.entrySet()) {
                    ParkingSpot spot = entry.getValue();
                    Car car = spot.getCar();
                    Duration duration = Duration.between(car.getParkingTime(), LocalDateTime.now());
                    long hours = duration.toHours();
                    long minutes = duration.toMinutes() % 60;
                    long seconds = duration.getSeconds() % 60;
                    sb.append("Spot ").append(spot.getId()).append(":\n")
                            .append("  Registration number: ").append(car.getRegistrationNumber()).append("\n")
                            .append("  Make: ").append(car.getMake()).append("\n")
                            .append("  Model: ").append(car.getModel()).append("\n")
                            .append("  Year: ").append(car.getYear()).append("\n")
                            .append("  Parking time: ").append(hours).append(" hours ")
                            .append(minutes).append(" minutes ").append(seconds).append(" seconds\n\n");
                }
                JOptionPane.showMessageDialog(null, sb.toString());
            } else {
                JOptionPane.showMessageDialog(null, "No cars with make " + make + " found.");
            }
        }
    }

    /**
     * Listener for resetting the car park.
     */
    private class ResetCarParkButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            carPark.resetCarPark();
            for (JButton button : spotButtons.values()) {
                button.setBackground(Color.LIGHT_GRAY);
                button.setText("<html>" + button.getName() + "<br>Vacant</html>");
            }
            JOptionPane.showMessageDialog(null, "Car park has been reset. All cars removed.");
        }
    }

    /**
     * Shows an input dialog with the specified message.
     *
     * @param message the message to display
     * @return the input entered by the user, or null if the dialog was canceled
     */
    private String showInputDialog(String message) {
        return JOptionPane.showInputDialog(null, message);
    }

    /**
     * Main method to launch the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CarParkGUI::new);
    }
}


