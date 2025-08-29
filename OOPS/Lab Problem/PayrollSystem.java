class Employee {
    private String empId;
    private String empName;
    private String department;
    private double baseSalary;
    private String empType;
    private static int totalEmployees = 0;

    public Employee(String empName, String department, double baseSalary, double bonus) {
        this.empId = generateEmpId();
        this.empName = empName;
        this.department = department;
        this.baseSalary = baseSalary + bonus;
        this.empType = "Full-Time";
        totalEmployees++;
    }

    public Employee(String empName, String department, double hourlyRate, int hours) {
        this.empId = generateEmpId();
        this.empName = empName;
        this.department = department;
        this.baseSalary = hourlyRate * hours;
        this.empType = "Part-Time";
        totalEmployees++;
    }

    public Employee(String empName, String department, double contractAmount) {
        this.empId = generateEmpId();
        this.empName = empName;
        this.department = department;
        this.baseSalary = contractAmount;
        this.empType = "Contract";
        totalEmployees++;
    }

    public double calculateSalary(double bonus) {
        if (empType.equals("Full-Time")) {
            return baseSalary + bonus;
        }
        return baseSalary;
    }

    public double calculateSalary(int hours, double hourlyRate) {
        if (empType.equals("Part-Time")) {
            return hours * hourlyRate;
        }
        return baseSalary;
    }

    public double calculateSalary() {
        return baseSalary;
    }

    public double calculateTax(double rate) {
        return baseSalary * rate;
    }

    public double calculateTax() {
        if (empType.equals("Full-Time")) {
            return baseSalary * 0.20;
        } else if (empType.equals("Part-Time")) {
            return baseSalary * 0.10;
        } else {
            return baseSalary * 0.15;
        }
    }

    public void generatePaySlip() {
        double salary = calculateSalary();
        double tax = calculateTax();
        System.out.println("====================================");
        System.out.println("Employee ID   : " + empId);
        System.out.println("Name          : " + empName);
        System.out.println("Department    : " + department);
        System.out.println("Type          : " + empType);
        System.out.println("Salary        : " + salary);
        System.out.println("Tax Deduction : " + tax);
        System.out.println("Net Pay       : " + (salary - tax));
        System.out.println("====================================");
    }

    public void displayEmployeeInfo() {
        System.out.println(empId + " | " + empName + " | " + department + " | " + empType);
    }

    private static String generateEmpId() {
        return String.format("E%03d", totalEmployees + 1);
    }

    public static int getTotalEmployees() {
        return totalEmployees;
    }
}

public class PayrollSystem {
    public static void main(String[] args) {
        Employee e1 = new Employee("Alice", "IT", 30000, 5000);
        Employee e2 = new Employee("Bob", "HR", 200, 40);
        Employee e3 = new Employee("Charlie", "Finance", 40000);

        e1.generatePaySlip();
        e2.generatePaySlip();
        e3.generatePaySlip();

        e1.displayEmployeeInfo();
        e2.displayEmployeeInfo();
        e3.displayEmployeeInfo();

        System.out.println("Total Employees: " + Employee.getTotalEmployees());
    }
}
