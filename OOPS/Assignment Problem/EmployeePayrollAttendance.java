import java.util.*;

class EmployeeE {
    String empId;
    String empName;
    String department;
    String designation;
    double baseSalary;
    String joinDate;
    boolean[] attendanceRecord;
    String type;
    static int totalEmployees = 0;
    static String companyName = "TechNova Pvt Ltd";
    static double totalSalaryExpense = 0;
    static int workingDaysPerMonth = 30;

    public EmployeeE(String empName, String department, String designation, double baseSalary, String joinDate, String type) {
        this.empId = String.format("EMP%04d", ++totalEmployees);
        this.empName = empName;
        this.department = department;
        this.designation = designation;
        this.baseSalary = baseSalary;
        this.joinDate = joinDate;
        this.attendanceRecord = new boolean[workingDaysPerMonth];
        this.type = type;
    }

    public void markAttendance(int day, boolean present) {
        if (day < 1 || day > workingDaysPerMonth) return;
        attendanceRecord[day - 1] = present;
    }

    public double calculateSalary() {
        int presentDays = 0;
        for (boolean b : attendanceRecord) if (b) presentDays++;
        double salary = 0;
        if (type.equalsIgnoreCase("Full-time")) salary = baseSalary + calculateBonus();
        else if (type.equalsIgnoreCase("Part-time")) salary = (baseSalary / workingDaysPerMonth) * presentDays;
        else salary = baseSalary;
        totalSalaryExpense += salary;
        return salary;
    }

    public double calculateBonus() {
        int presentDays = 0;
        for (boolean b : attendanceRecord) if (b) presentDays++;
        return presentDays >= 26 ? baseSalary * 0.1 : 0;
    }

    public void generatePaySlip() {
        double salary = calculateSalary();
        System.out.println(empId + " | " + empName + " | " + department + " | " + designation + " | " + type + " | Salary: " + salary);
    }

    public boolean requestLeave(int day) {
        if (day < 1 || day > workingDaysPerMonth) return false;
        attendanceRecord[day - 1] = false;
        return true;
    }

    public static double calculateCompanyPayroll(EmployeeE[] employees) {
        totalSalaryExpense = 0;
        for (EmployeeE e : employees) if (e != null) e.calculateSalary();
        return totalSalaryExpense;
    }

    public static double[] getDepartmentWiseExpenses(EmployeeE[] employees, String[] depts) {
        double[] sums = new double[depts.length];
        for (int i = 0; i < depts.length; i++) {
            double s = 0;
            for (EmployeeE e : employees) if (e != null && e.department.equals(depts[i])) {
                s += e.baseSalary;
            }
            sums[i] = s;
        }
        return sums;
    }

    public static String getAttendanceReport(EmployeeE e) {
        int p = 0;
        for (boolean b : e.attendanceRecord) if (b) p++;
        return e.empName + " Present: " + p + "/" + workingDaysPerMonth;
    }
}

class DepartmentE {
    String deptId;
    String deptName;
    EmployeeE manager;
    EmployeeE[] employees;
    double budget;

    public DepartmentE(String deptName, EmployeeE manager, int cap, double budget) {
        this.deptId = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        this.deptName = deptName;
        this.manager = manager;
        this.employees = new EmployeeE[cap];
        this.budget = budget;
    }

    public void addEmployee(EmployeeE e) {
        for (int i = 0; i < employees.length; i++) if (employees[i] == null) { employees[i] = e; return; }
    }
}

public class EmployeePayrollAttendance {
    public static void main(String[] args) {
        EmployeeE e1 = new EmployeeE("Aditi","IT","Engineer",60000,"2024-06-01","Full-time");
        EmployeeE e2 = new EmployeeE("Rohit","HR","Executive",30000,"2024-03-01","Part-time");
        EmployeeE e3 = new EmployeeE("Neha","IT","Contractor",45000,"2025-01-10","Contract");

        for (int d = 1; d <= 30; d++) { e1.markAttendance(d, d % 7 != 0); e2.markAttendance(d, d % 2 == 0); e3.markAttendance(d, d % 3 != 0); }

        DepartmentE dIT = new DepartmentE("IT", e1, 10, 1000000);
        dIT.addEmployee(e1); dIT.addEmployee(e3);
        DepartmentE dHR = new DepartmentE("HR", e2, 10, 500000);
        dHR.addEmployee(e2);

        e1.generatePaySlip();
        e2.generatePaySlip();
        e3.generatePaySlip();

        double total = EmployeeE.calculateCompanyPayroll(new EmployeeE[]{e1,e2,e3});
        System.out.println("Company Payroll: " + total);

        String[] depts = {"IT","HR"};
        double[] exp = EmployeeE.getDepartmentWiseExpenses(new EmployeeE[]{e1,e2,e3}, depts);
        for (int i = 0; i < depts.length; i++) System.out.println(depts[i] + " Expense: " + exp[i]);

        System.out.println(EmployeeE.getAttendanceReport(e1));
        System.out.println(EmployeeE.getAttendanceReport(e2));
        System.out.println(EmployeeE.getAttendanceReport(e3));
    }
}
