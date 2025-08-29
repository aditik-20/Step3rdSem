import java.util.Scanner;

class Employee {
    private static String companyName;
    private static int totalEmployees = 0;

    private String empId;
    private String name;
    private String department;
    private double salary;

    public Employee() {
        totalEmployees++;
    }

    public Employee(String empId, String name, String department, double salary) {
        this.empId = empId;
        this.name = name;
        this.department = department;
        this.salary = salary;
        totalEmployees++;
    }

    public static void setCompanyName(String name) {
        companyName = name;
    }

    public static int getTotalEmployees() {
        return totalEmployees;
    }

    public double calculateAnnualSalary() {
        return salary * 12;
    }

    public void updateSalary(double newSalary) {
        this.salary = newSalary;
    }

    public void displayEmployee() {
        System.out.println("[" + empId + "] " + name + " | Dept: " + department + " | Salary: " + salary);
    }

    public String getEmpId() { return empId; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
}

class Department {
    private String deptName;
    private Employee[] employees;
    private int employeeCount;

    public Department(String deptName, int capacity) {
        this.deptName = deptName;
        this.employees = new Employee[capacity];
        this.employeeCount = 0;
    }

    public void addEmployee(Employee e) {
        if (employeeCount < employees.length) {
            employees[employeeCount++] = e;
            System.out.println("Employee added to " + deptName);
        }
    }

    public void displayEmployees() {
        System.out.println("=== Department: " + deptName + " ===");
        for (int i = 0; i < employeeCount; i++) {
            employees[i].displayEmployee();
        }
    }

    public Employee highestPaidEmployee() {
        if (employeeCount == 0) return null;
        Employee highest = employees[0];
        for (int i = 1; i < employeeCount; i++) {
            if (employees[i].getSalary() > highest.getSalary()) {
                highest = employees[i];
            }
        }
        return highest;
    }

    public double totalPayroll() {
        double total = 0;
        for (int i = 0; i < employeeCount; i++) {
            total += employees[i].getSalary();
        }
        return total;
    }

    public Employee[] getEmployees() {
        return employees;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }
}

public class EmployeeManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Employee.setCompanyName("TechCorp");

        Department devDept = new Department("Development", 50);
        Department hrDept = new Department("HR", 20);

        while (true) {
            System.out.println("\n=== EMPLOYEE MANAGEMENT SYSTEM ===");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Department Statistics");
            System.out.println("5. Company Statistics");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Employee ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Department (Development/HR): ");
                    String dept = scanner.nextLine();
                    System.out.print("Enter Salary: ");
                    double salary = scanner.nextDouble();
                    scanner.nextLine();

                    Employee e = new Employee(id, name, dept, salary);
                    if (dept.equalsIgnoreCase("Development")) {
                        devDept.addEmployee(e);
                    } else {
                        hrDept.addEmployee(e);
                    }
                    break;

                case 2:
                    devDept.displayEmployees();
                    hrDept.displayEmployees();
                    break;

                case 3:
                    System.out.print("Enter Employee ID to search: ");
                    String searchId = scanner.nextLine();
                    boolean found = false;
                    for (Employee emp : devDept.getEmployees()) {
                        if (emp != null && emp.getEmpId().equals(searchId)) {
                            emp.displayEmployee();
                            found = true;
                        }
                    }
                    for (Employee emp : hrDept.getEmployees()) {
                        if (emp != null && emp.getEmpId().equals(searchId)) {
                            emp.displayEmployee();
                            found = true;
                        }
                    }
                    if (!found) System.out.println("Employee not found!");
                    break;

                case 4:
                    System.out.println("Development Department Payroll: " + devDept.totalPayroll());
                    System.out.println("HR Department Payroll: " + hrDept.totalPayroll());
                    System.out.println("Highest Paid in Development: ");
                    if (devDept.highestPaidEmployee() != null)
                        devDept.highestPaidEmployee().displayEmployee();
                    System.out.println("Highest Paid in HR: ");
                    if (hrDept.highestPaidEmployee() != null)
                        hrDept.highestPaidEmployee().displayEmployee();
                    break;

                case 5:
                    double totalPayroll = devDept.totalPayroll() + hrDept.totalPayroll();
                    System.out.println("Total Employees in Company: " + Employee.getTotalEmployees());
                    System.out.println("Company-wide Payroll: " + totalPayroll);
                    break;

                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
