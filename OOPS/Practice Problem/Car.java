
import java.time.Year;

public class Car {
    private String brand;
    private String model;
    private int year;
    private String color;
    private boolean isRunning;

    public Car(String brand, String model, int year, String color) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.isRunning = false;
    }

    public void startEngine() {
        if (!isRunning) {
            isRunning = true;
            System.out.println(brand + " " + model + " engine started!");
        } else {
            System.out.println(brand + " " + model + " engine is already running!");
        }
    }

    public void stopEngine() {
        if (isRunning) {
            isRunning = false;
            System.out.println(brand + " " + model + " engine stopped!");
        } else {
            System.out.println(brand + " " + model + " engine is already off!");
        }
    }

    public void displayInfo() {
        System.out.println("Car Information:");
        System.out.println(" Brand: " + brand);
        System.out.println(" Model: " + model);
        System.out.println(" Year: " + year);
        System.out.println(" Color: " + color);
        System.out.println(" Is Running: " + (isRunning ? "Yes" : "No"));
        System.out.println("-----------------------------------");
    }

    public int getAge() {
        int currentYear = Year.now().getValue();
        return currentYear - year;
    }

    public static void main(String[] args) {
        Car car1 = new Car("Toyota", "Camry", 2018, "Black");
        Car car2 = new Car("Tesla", "Model 3", 2022, "White");
        Car car3 = new Car("Ford", "Mustang", 2015, "Red");

        car1.startEngine();
        car1.displayInfo();
        System.out.println(car1.getAge() + " years old.\n");

        car2.startEngine();
        car2.stopEngine();
        car2.displayInfo();
        System.out.println(car2.getAge() + " years old.\n");

        car3.displayInfo();
        car3.startEngine();
        System.out.println(car3.getAge() + " years old.\n");
    }
}
