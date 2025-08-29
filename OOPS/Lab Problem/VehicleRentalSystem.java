class Vehicle {
    private String vehicleId;
    private String brand;
    private String model;
    private double rentPerDay;
    private boolean isAvailable;
    private int totalDaysRented;

    private static int totalVehicles = 0;
    private static double totalRevenue = 0;
    private static int totalRentalDays = 0;
    private static String companyName = "Default Rentals";

    public Vehicle(String brand, String model, double rentPerDay) {
        this.vehicleId = generateVehicleId();
        this.brand = brand;
        this.model = model;
        this.rentPerDay = rentPerDay;
        this.isAvailable = true;
        this.totalDaysRented = 0;
        totalVehicles++;
    }

    public double rentVehicle(int days) {
        if (isAvailable) {
            double rent = calculateRent(days);
            isAvailable = false;
            totalDaysRented += days;
            System.out.println("Vehicle " + vehicleId + " rented for " + days + " days. Rent = " + rent);
            return rent;
        } else {
            System.out.println("Vehicle " + vehicleId + " is not available.");
            return 0;
        }
    }

    public void returnVehicle() {
        if (!isAvailable) {
            isAvailable = true;
            System.out.println("Vehicle " + vehicleId + " returned successfully.");
        } else {
            System.out.println("Vehicle " + vehicleId + " was not rented.");
        }
    }

    private double calculateRent(int days) {
        double amount = rentPerDay * days;
        totalRevenue += amount;
        totalRentalDays += days;
        return amount;
    }

    public void displayVehicleInfo() {
        System.out.println("------------------------------------------------");
        System.out.println("Vehicle ID   : " + vehicleId);
        System.out.println("Brand        : " + brand);
        System.out.println("Model        : " + model);
        System.out.println("Rent/Day     : " + rentPerDay);
        System.out.println("Available    : " + (isAvailable ? "Yes" : "No"));
        System.out.println("Total Days Rented : " + totalDaysRented);
        System.out.println("------------------------------------------------");
    }

    private static String generateVehicleId() {
        return String.format("V%03d", totalVehicles + 1);
    }

    public static void setCompanyName(String name) {
        companyName = name;
    }

    public static double getTotalRevenue() {
        return totalRevenue;
    }

    public static double getAverageRentPerDay() {
        if (totalRentalDays == 0) return 0;
        return totalRevenue / totalRentalDays;
    }

    public static void displayCompanyStats() {
        System.out.println("================ Company Stats ================");
        System.out.println("Company Name     : " + companyName);
        System.out.println("Total Vehicles   : " + totalVehicles);
        System.out.println("Total Revenue    : " + totalRevenue);
        System.out.println("Total Rental Days: " + totalRentalDays);
        System.out.println("Avg Rent/Day     : " + getAverageRentPerDay());
        System.out.println("================================================");
    }
}

public class VehicleRentalSystem {
    public static void main(String[] args) {
        Vehicle.setCompanyName("ZoomCars Pvt Ltd");

        Vehicle v1 = new Vehicle("Toyota", "Corolla", 1500);
        Vehicle v2 = new Vehicle("Honda", "City", 1200);
        Vehicle v3 = new Vehicle("Ford", "Ecosport", 1800);

        v1.displayVehicleInfo();
        v2.displayVehicleInfo();
        v3.displayVehicleInfo();

        v1.rentVehicle(5);
        v2.rentVehicle(3);

        v1.displayVehicleInfo();
        v2.displayVehicleInfo();

        v1.returnVehicle();
        v2.returnVehicle();

        Vehicle.displayCompanyStats();

        System.out.println("Revenue via static: " + Vehicle.getTotalRevenue());
        System.out.println("Unique rent/day for v1: " + v1.rentVehicle(2));
        System.out.println("Unique rent/day for v2: " + v2.rentVehicle(1));
    }
}
