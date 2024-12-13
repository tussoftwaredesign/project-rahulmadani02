package domain;

public interface Vehicle {
    String getType();

    default String vehicleInfo() {
        return "Vehicle Information: " + getType();
    }

    static void showStaticInfo() {
        System.out.println("All vehicles are managed by the system.");
    }

    private void logAction(String action) {
        System.out.println("Action logged: " + action);
    }

    default void performAction(String action) {
        logAction(action);
        System.out.println("Performing action: " + action);
    }
}
