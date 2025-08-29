public class Vehicle {
    protected String make;
    protected String model;
    protected int year;
    protected double fuelLevel;

    public Vehicle(String make, String model, int year, double fuelLevel) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.fuelLevel = fuelLevel;
    }

    public void startVehicle() {
        System.out.println(year + " " + make + " " + model + " started.");
    }

    public void stopVehicle() {
        System.out.println(year + " " + make + " " + model + " stopped.");
    }

    public void refuel(double amount) {
        fuelLevel += amount;
        System.out.println(make + " " + model + " refueled. Current fuel: " + fuelLevel);
    }

    public void displayVehicleInfo() {
        System.out.println("Vehicle Info: " + year + " " + make + " " + model + " | Fuel: " + fuelLevel);
    }

    public static void main(String[] args) {
        Vehicle car = new Car("Toyota", "Camry", 2020, 50, 4);
        Vehicle truck = new Truck("Volvo", "FH16", 2018, 70, 10000);
        Vehicle motorcycle = new Motorcycle("Yamaha", "R15", 2021, 20, "Sport");

        Vehicle[] vehicles = {car, truck, motorcycle};

        for (Vehicle v : vehicles) {
            v.startVehicle();
            v.displayVehicleInfo();
            v.refuel(10);
            v.stopVehicle();
            System.out.println("---------------------------");
        }

        // Reusability: Vehicle class provides base functionality for all vehicles.
        // Extensibility: New vehicle types can be added (Bus, Van, etc.) without rewriting everything.
        // Benefit: Reduces code duplication compared to writing separate classes.
    }
}

class Car extends Vehicle {
    private int doors;

    public Car(String make, String model, int year, double fuelLevel, int doors) {
        super(make, model, year, fuelLevel);
        this.doors = doors;
    }

    @Override
    public void displayVehicleInfo() {
        super.displayVehicleInfo();
        System.out.println("Doors: " + doors);
    }
}

class Truck extends Vehicle {
    private double loadCapacity;

    public Truck(String make, String model, int year, double fuelLevel, double loadCapacity) {
        super(make, model, year, fuelLevel);
        this.loadCapacity = loadCapacity;
    }

    @Override
    public void displayVehicleInfo() {
        super.displayVehicleInfo();
        System.out.println("Load Capacity: " + loadCapacity + " kg");
    }
}

class Motorcycle extends Vehicle {
    private String type;

    public Motorcycle(String make, String model, int year, double fuelLevel, String type) {
        super(make, model, year, fuelLevel);
        this.type = type;
    }

    @Override
    public void displayVehicleInfo() {
        super.displayVehicleInfo();
        System.out.println("Motorcycle Type: " + type);
    }
}
