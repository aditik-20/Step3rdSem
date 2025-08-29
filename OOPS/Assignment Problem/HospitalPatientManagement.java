import java.util.*;

class Patient {
    String patientId;
    String patientName;
    int age;
    String gender;
    String contactInfo;
    String[] medicalHistory;
    String[] currentTreatments;
    static int totalPatients = 0;

    public Patient(String patientName, int age, String gender, String contactInfo) {
        this.patientId = String.format("PT%04d", ++totalPatients);
        this.patientName = patientName;
        this.age = age;
        this.gender = gender;
        this.contactInfo = contactInfo;
        this.medicalHistory = new String[20];
        this.currentTreatments = new String[10];
    }

    public void updateTreatment(String treatment) {
        for (int i = 0; i < currentTreatments.length; i++) if (currentTreatments[i] == null) { currentTreatments[i] = treatment; break; }
    }

    public void dischargePatient() {
        for (int i = 0; i < currentTreatments.length; i++) currentTreatments[i] = null;
    }
}

class Doctor {
    String doctorId;
    String doctorName;
    String specialization;
    String[] availableSlots;
    int patientsHandled;
    double consultationFee;

    public Doctor(String doctorName, String specialization, double fee, String[] slots) {
        this.doctorId = UUID.randomUUID().toString().substring(0,6).toUpperCase();
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.consultationFee = fee;
        this.availableSlots = slots;
        this.patientsHandled = 0;
    }
}

class Appointment {
    String appointmentId;
    Patient patient;
    Doctor doctor;
    String appointmentDate;
    String appointmentTime;
    String status;
    static int totalAppointments = 0;
    static String hospitalName = "CarePlus Hospital";
    static double totalRevenue = 0;

    public Appointment(Patient p, Doctor d, String date, String time) {
        this.appointmentId = String.format("AP%05d", ++totalAppointments);
        this.patient = p;
        this.doctor = d;
        this.appointmentDate = date;
        this.appointmentTime = time;
        this.status = "Scheduled";
    }

    public static Appointment scheduleAppointment(Patient p, Doctor d, String date, String time) {
        boolean slotOk = false;
        for (String s : d.availableSlots) if (s.equals(time)) { slotOk = true; break; }
        if (!slotOk) { System.out.println("Slot unavailable"); return null; }
        Appointment a = new Appointment(p, d, date, time);
        d.patientsHandled++;
        System.out.println("Appointment " + a.appointmentId + " scheduled with Dr. " + d.doctorName);
        return a;
    }

    public static void cancelAppointment(Appointment a) {
        if (a == null) return;
        a.status = "Cancelled";
        System.out.println("Cancelled " + a.appointmentId);
    }

    public static double generateBill(Appointment a, String type) {
        if (a == null) return 0;
        double mult = type.equalsIgnoreCase("Emergency") ? 2.0 : type.equalsIgnoreCase("Follow-up") ? 0.5 : 1.0;
        double amt = a.doctor.consultationFee * mult;
        totalRevenue += amt;
        System.out.println("Bill for " + a.appointmentId + ": " + amt);
        return amt;
    }

    public static void updateTreatment(Patient p, String treatment) {
        p.updateTreatment(treatment);
    }

    public static void dischargePatient(Patient p) {
        p.dischargePatient();
        System.out.println("Discharged " + p.patientName);
    }

    public static void generateHospitalReport(Doctor[] doctors) {
        int totalPatientsHandled = 0;
        for (Doctor d : doctors) if (d != null) totalPatientsHandled += d.patientsHandled;
        System.out.println("Hospital: " + hospitalName);
        System.out.println("Total Appointments: " + totalAppointments);
        System.out.println("Total Revenue: " + totalRevenue);
        System.out.println("Doctor Utilization:");
        for (Doctor d : doctors) if (d != null) System.out.println(d.doctorName + " | Patients: " + d.patientsHandled);
    }

    public static void getDoctorUtilization(Doctor[] doctors) {
        for (Doctor d : doctors) if (d != null) System.out.println(d.doctorName + " | Utilization: " + (d.patientsHandled));
    }

    public static void getPatientStatistics() {
        System.out.println("Total Patients: " + Patient.totalPatients);
        System.out.println("Total Appointments: " + totalAppointments);
    }
}

public class HospitalPatientManagement {
    public static void main(String[] args) {
        Patient p1 = new Patient("Aditi", 21, "F", "9999999999");
        Patient p2 = new Patient("Rohan", 30, "M", "8888888888");

        Doctor d1 = new Doctor("Dr. Mehta","Cardiology",1000,new String[]{"10:00","11:00","12:00"});
        Doctor d2 = new Doctor("Dr. Singh","Dermatology",700,new String[]{"09:30","10:30","11:30"});

        Appointment a1 = Appointment.scheduleAppointment(p1,d1,"2025-08-30","10:00");
        Appointment a2 = Appointment.scheduleAppointment(p2,d2,"2025-08-30","10:30");

        Appointment.updateTreatment(p1,"ECG");
        Appointment.updateTreatment(p2,"Skin Therapy");

        Appointment.generateBill(a1,"Consultation");
        Appointment.generateBill(a2,"Emergency");

        Appointment.cancelAppointment(a2);
        Appointment.dischargePatient(p1);

        Appointment.generateHospitalReport(new Doctor[]{d1,d2});
        Appointment.getDoctorUtilization(new Doctor[]{d1,d2});
        Appointment.getPatientStatistics();
    }
}
