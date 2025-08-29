import java.util.*;

class VehicleV {
    String vehicleId;
    String brand;
    String model;
    int year;
    double mileage;
    String fuelType;
    String currentStatus;
    static int totalVehicles = 0;
    static double fleetValue = 0;
    static String companyName = "Swift Logistics";
    static double totalFuelConsumption = 0;

    public VehicleV(String brand, String model, int year, double mileage, String fuelType, String status) {
        this.vehicleId = String.format("VH%04d", ++totalVehicles);
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.fuelType = fuelType;
        this.currentStatus = status;
    }

    public void updateMileage(double km) {
        mileage += km;
    }

    public boolean checkServiceDue(double intervalKm) {
        return mileage % intervalKm < 100;
    }

    public double calculateRunningCost(double km, double fuelPricePerL, double avgKmPerL) {
        double fuel = km / avgKmPerL;
        totalFuelConsumption += fuel;
        return fuel * fuelPricePerL;
    }

    public void scheduleMaintenance() {
        currentStatus = "Maintenance";
    }

    public void assignDriver(String driverId) {
        currentStatus = "Assigned-" + driverId;
    }
}

class Car extends VehicleV {
    int seats;
    public Car(String brand, String model, int year, double mileage, String fuelType, int seats) {
        super(brand, model, year, mileage, fuelType, "Available");
        this.seats = seats;
    }
}

class Bus extends VehicleV {
    int seatingCapacity;
    public Bus(String brand, String model, int year, double mileage, String fuelType, int seatingCapacity) {
        super(brand, model, year, mileage, fuelType, "Available");
        this.seatingCapacity = seatingCapacity;
    }
}

class Truck extends VehicleV {
    double loadCapacity;
    public Truck(String brand, String model, int year, double mileage, String fuelType, double loadCapacity) {
        super(brand, model, year, mileage, fuelType, "Available");
        this.loadCapacity = loadCapacity;
    }
}

class Driver {
    String driverId;
    String driverName;
    String licenseType;
    String assignedVehicle;
    int totalTrips;

    public Driver(String driverName, String licenseType) {
        this.driverId = UUID.randomUUID().toString().substring(0,6).toUpperCase();
        this.driverName = driverName;
        this.licenseType = licenseType;
        this.assignedVehicle = null;
        this.totalTrips = 0;
    }

    public void assign(VehicleV v) {
        this.assignedVehicle = v.vehicleId;
        v.assignDriver(driverId);
    }

    public void completeTrip() {
        totalTrips++;
    }
}

public class VehicleFleetManagement {
    public static void main(String[] args) {
        VehicleV[] fleet = new VehicleV[5];
        fleet[0] = new Car("Toyota","Corolla",2022,20000,"Petrol",5);
        fleet[1] = new Bus("Tata","Starbus",2021,50000,"Diesel",40);
        fleet[2] = new Truck("Ashok","HaulPro",2020,80000,"Diesel",12.5);
        fleet[3] = new Car("Hyundai","i20",2023,12000,"Petrol",5);
        fleet[4] = new Truck("BharatBenz","914R",2019,110000,"Diesel",9.0);

        Driver d1 = new Driver("Amit","Heavy");
        Driver d2 = new Driver("Riya","LMV");

        d1.assign(fleet[2]);
        d2.assign(fleet[0]);

        double cost1 = fleet[2].calculateRunningCost(350, 95, 6);
        fleet[2].updateMileage(350);
        d1.completeTrip();

        double cost2 = fleet[0].calculateRunningCost(120, 105, 15);
        fleet[0].updateMileage(120);
        d2.completeTrip();

        System.out.println("Total Vehicles: " + VehicleV.totalVehicles);
        System.out.println("Total Fuel Used Approx: " + VehicleV.totalFuelConsumption);
        System.out.println("Service Due Truck: " + fleet[2].checkServiceDue(10000));
        System.out.println("Vehicle " + fleet[2].vehicleId + " Status: " + fleet[2].currentStatus);

        System.out.println("Fleet Utilization: " + getFleetUtilization(fleet) + "%");
        System.out.println("Total Maintenance Cost: " + calculateTotalMaintenanceCost(fleet));
        VehicleV[] trucks = getVehiclesByType(fleet, "Truck");
        System.out.println("Trucks Count: " + trucks.length);
    }

    public static double getFleetUtilization(VehicleV[] fleet) {
        int assigned = 0;
        for (VehicleV v : fleet) if (v != null && v.currentStatus.startsWith("Assigned")) assigned++;
        return fleet.length == 0 ? 0 : assigned * 100.0 / fleet.length;
    }

    public static double calculateTotalMaintenanceCost(VehicleV[] fleet) {
        double cost = 0;
        for (VehicleV v : fleet) if (v != null && v.checkServiceDue(10000)) cost += 5000;
        return cost;
    }

    public static VehicleV[] getVehiclesByType(VehicleV[] fleet, String type) {
        VehicleV[] tmp = new VehicleV[fleet.length];
        int k = 0;
        for (VehicleV v : fleet) if (v != null) {
            if (type.equalsIgnoreCase("Car") && v instanceof Car) tmp[k++] = v;
            if (type.equalsIgnoreCase("Bus") && v instanceof Bus) tmp[k++] = v;
            if (type.equalsIgnoreCase("Truck") && v instanceof Truck) tmp[k++] = v;
        }
        VehicleV[] res = new VehicleV[k];
        for (int i = 0; i < k; i++) res[i] = tmp[i];
        return res;
    }
}
