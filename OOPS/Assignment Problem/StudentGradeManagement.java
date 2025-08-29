import java.util.*;

class Subject {
    String subjectCode;
    String subjectName;
    int credits;
    String instructor;

    public Subject(String subjectCode, String subjectName, int credits, String instructor) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credits = credits;
        this.instructor = instructor;
    }
}

class Student {
    String studentId;
    String studentName;
    String className;
    String[] subjects;
    double[][] marks;
    double gpa;
    static int totalStudents = 0;
    static String schoolName = "Green Valley High";
    static String[] gradingScale = {"A","B","C","D","F"};
    static double passPercentage = 40.0;

    public Student(String studentName, String className, String[] subjects, int terms) {
        this.studentId = String.format("S%03d", ++totalStudents);
        this.studentName = studentName;
        this.className = className;
        this.subjects = subjects;
        this.marks = new double[subjects.length][terms];
        this.gpa = 0;
    }

    public void addMarks(String subject, double mark) {
        int idx = -1;
        for (int i = 0; i < subjects.length; i++) if (subjects[i].equalsIgnoreCase(subject)) { idx = i; break; }
        if (idx == -1) return;
        for (int t = 0; t < marks[idx].length; t++) if (marks[idx][t] == 0) { marks[idx][t] = mark; break; }
    }

    public void calculateGPA() {
        double total = 0; int count = 0;
        for (int i = 0; i < marks.length; i++) for (int j = 0; j < marks[i].length; j++) if (marks[i][j] > 0) { total += marks[i][j]; count++; }
        double avg = count == 0 ? 0 : total / count;
        gpa = avg / 10.0;
    }

    public void generateReportCard() {
        calculateGPA();
        System.out.println("School: " + schoolName);
        System.out.println("ID: " + studentId + " | " + studentName + " | Class: " + className);
        double total = 0; int n = 0;
        for (int i = 0; i < subjects.length; i++) {
            double ssum = 0; int c = 0;
            for (double m : marks[i]) if (m > 0) { ssum += m; c++; }
            double avg = c == 0 ? 0 : ssum / c;
            total += avg; n++;
            System.out.println(subjects[i] + " : " + avg + " : " + gradeOf(avg));
        }
        double overall = n == 0 ? 0 : total / n;
        System.out.println("Overall %: " + overall + " | GPA: " + gpa + " | " + (overall >= passPercentage ? "Pass" : "Fail"));
        System.out.println("----------------------------");
    }

    public boolean checkPromotionEligibility() {
        double total = 0; int n = 0;
        for (int i = 0; i < subjects.length; i++) {
            double ssum = 0; int c = 0;
            for (double m : marks[i]) if (m > 0) { ssum += m; c++; }
            double avg = c == 0 ? 0 : ssum / c;
            total += avg; n++;
            if (avg < passPercentage) return false;
        }
        return (n == 0 ? 0 : total / n) >= passPercentage;
    }

    static String gradeOf(double percentage) {
        if (percentage >= 85) return gradingScale[0];
        if (percentage >= 70) return gradingScale[1];
        if (percentage >= 55) return gradingScale[2];
        if (percentage >= 40) return gradingScale[3];
        return gradingScale[4];
    }

    public static void setGradingScale(String[] scale, double passPercent) {
        gradingScale = scale;
        passPercentage = passPercent;
    }

    public static double calculateClassAverage(Student[] students) {
        double total = 0; int c = 0;
        for (Student s : students) if (s != null) {
            s.calculateGPA();
            double st = 0; int n = 0;
            for (int i = 0; i < s.subjects.length; i++) {
                double sum = 0; int k = 0;
                for (double m : s.marks[i]) if (m > 0) { sum += m; k++; }
                double avg = k == 0 ? 0 : sum / k;
                st += avg; n++;
            }
            if (n > 0) { total += (st / n); c++; }
        }
        return c == 0 ? 0 : total / c;
    }

    public static Student[] getTopPerformers(Student[] students, int count) {
        Student[] arr = new Student[students.length];
        int k = 0;
        for (Student s : students) if (s != null) { s.calculateGPA(); arr[k++] = s; }
        for (int i = 0; i < k - 1; i++)
            for (int j = 0; j < k - i - 1; j++)
                if (arr[j].gpa < arr[j + 1].gpa) { Student tmp = arr[j]; arr[j] = arr[j + 1]; arr[j + 1] = tmp; }
        int r = Math.min(count, k);
        Student[] top = new Student[r];
        for (int i = 0; i < r; i++) top[i] = arr[i];
        return top;
    }

    public static void generateSchoolReport(Student[] students) {
        System.out.println("School: " + schoolName);
        System.out.println("Total Students: " + totalStudents);
        System.out.println("Class Average: " + calculateClassAverage(students));
        Student[] top = getTopPerformers(students, 3);
        System.out.println("Top Performers:");
        for (Student s : top) System.out.println(s.studentId + " " + s.studentName + " GPA: " + s.gpa);
        System.out.println("----------------------------");
    }
}

public class StudentGradeManagement {
    public static void main(String[] args) {
        String[] subs = {"Math","Physics","Chemistry","English"};
        Student s1 = new Student("Aditi","X-A",subs,3);
        Student s2 = new Student("Ravi","X-A",subs,3);
        Student s3 = new Student("Neha","X-A",subs,3);

        s1.addMarks("Math", 90); s1.addMarks("Physics", 80); s1.addMarks("Chemistry", 88); s1.addMarks("English", 85);
        s1.addMarks("Math", 92); s1.addMarks("Physics", 84);

        s2.addMarks("Math", 70); s2.addMarks("Physics", 65); s2.addMarks("Chemistry", 60); s2.addMarks("English", 75);

        s3.addMarks("Math", 55); s3.addMarks("Physics", 58); s3.addMarks("Chemistry", 62); s3.addMarks("English", 64);

        s1.generateReportCard();
        s2.generateReportCard();
        s3.generateReportCard();

        System.out.println("Promotion Eligibility:");
        System.out.println(s1.studentName + " : " + s1.checkPromotionEligibility());
        System.out.println(s2.studentName + " : " + s2.checkPromotionEligibility());
        System.out.println(s3.studentName + " : " + s3.checkPromotionEligibility());

        Student.generateSchoolReport(new Student[]{s1,s2,s3});
    }
}
