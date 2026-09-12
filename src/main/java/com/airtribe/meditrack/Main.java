package com.airtribe.meditrack;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;
import com.airtribe.meditrack.util.DateUtil;
import com.airtribe.meditrack.util.DataPersistence;
import com.airtribe.meditrack.patterns.observer.ConsoleAppointmentObserver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DoctorService doctorService = new DoctorService();
    private static final PatientService patientService = new PatientService();
    private static final AppointmentService appointmentService = new AppointmentService(doctorService, patientService);

    public static void main(String[] args) {
        appointmentService.addObserver(new ConsoleAppointmentObserver());
        if (java.util.Arrays.asList(args).contains("--loadData")) {
            try {
                DataPersistence.load(doctorService, patientService, appointmentService);
                System.out.println("Saved data loaded.");
            } catch (Exception e) {
                System.out.println("Could not load saved data: " + e.getMessage());
            }
        }
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1": addDoctor(); break;
                    case "2": addPatient(); break;
                    case "3": bookAppointment(); break;
                    case "4": cancelAppointment(); break;
                    case "5": generateBill(); break;
                    case "6": listDoctors(); break;
                    case "7": listPatients(); break;
                    case "8": listAppointments(); break;
                    case "9": searchDoctors(); break;
                    case "10": searchPatients(); break;
                    case "11": updateDoctor(); break;
                    case "12": deleteDoctor(); break;
                    case "13": updatePatient(); break;
                    case "14": deletePatient(); break;
                    case "0":
                        running = false;
                        System.out.println("Exiting MediTrack. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice, try again.");
                }
            } catch (InvalidDataException | AppointmentNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Error: please enter a valid number.");
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
        try { DataPersistence.save(doctorService, patientService, appointmentService); }
        catch (java.io.IOException e) { System.out.println("Could not save data: " + e.getMessage()); }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n===== MediTrack Menu =====");
        System.out.println("1. Add Doctor");
        System.out.println("2. Add Patient");
        System.out.println("3. Book Appointment");
        System.out.println("4. Cancel Appointment");
        System.out.println("5. Generate Bill");
        System.out.println("6. List Doctors");
        System.out.println("7. List Patients");
        System.out.println("8. List Appointments");
        System.out.println("9. Search Doctors");
        System.out.println("10. Search Patients");
        System.out.println("11. Update Doctor");
        System.out.println("12. Delete Doctor");
        System.out.println("13. Update Patient");
        System.out.println("14. Delete Patient");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
    }

    private static void addDoctor() throws InvalidDataException {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Contact (10 digits): ");
        String contact = scanner.nextLine();
        System.out.print("Specialization: ");
        String specialization = scanner.nextLine();
        Doctor doctor = doctorService.addDoctor(name, age, gender, contact, specialization);
        System.out.println("Doctor added: " + doctor);
    }

    private static void addPatient() throws InvalidDataException {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Contact (10 digits): ");
        String contact = scanner.nextLine();
        System.out.print("Medical History: ");
        String history = scanner.nextLine();
        Patient patient = patientService.addPatient(name, age, gender, contact, history);
        System.out.println("Patient added: " + patient);
    }

    private static void bookAppointment() throws InvalidDataException {
        System.out.print("Patient ID: ");
        String patientId = scanner.nextLine();
        System.out.print("Doctor ID: ");
        String doctorId = scanner.nextLine();
        System.out.print("Date & Time (yyyy-MM-dd HH:mm): ");
        String dt = scanner.nextLine();
        System.out.print("Reason: ");
        String reason = scanner.nextLine();
        LocalDateTime dateTime = DateUtil.parse(dt);
        Appointment appointment = appointmentService.bookAppointment(patientId, doctorId, dateTime, reason);
        System.out.println("Appointment booked: " + appointment);
    }

    private static void cancelAppointment() throws AppointmentNotFoundException {
        System.out.print("Appointment ID: ");
        String id = scanner.nextLine();
        appointmentService.cancelAppointment(id);
        System.out.println("Appointment cancelled: " + id);
    }

    private static void generateBill() throws AppointmentNotFoundException {
        System.out.print("Appointment ID: ");
        String id = scanner.nextLine();
        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        Bill bill = appointmentService.generateBill(id, amount);
        System.out.println("Bill generated: " + bill);
    }

    private static void listDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        if (doctors.isEmpty()) { System.out.println("No doctors found."); return; }
        doctors.forEach(System.out::println);
    }

    private static void listPatients() {
        List<Patient> patients = patientService.getAllPatients();
        if (patients.isEmpty()) { System.out.println("No patients found."); return; }
        patients.forEach(System.out::println);
    }

    private static void listAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        if (appointments.isEmpty()) { System.out.println("No appointments found."); return; }
        appointments.forEach(System.out::println);
    }

    private static void searchDoctors() {
        System.out.print("Search keyword: ");
        String keyword = scanner.nextLine();
        List<Doctor> results = doctorService.search(keyword);
        if (results.isEmpty()) { System.out.println("No matches."); return; }
        results.forEach(System.out::println);
    }

    private static void searchPatients() {
        System.out.print("Search patient by ID, name, or age: ");
        String query = scanner.nextLine().trim();
        List<Patient> results;
        if (query.matches("\\d+")) results = patientService.searchPatientByAge(Integer.parseInt(query));
        else {
            Patient patient = patientService.searchPatientById(query);
            results = patient == null ? patientService.searchPatientByName(query) : java.util.Collections.singletonList(patient);
        }
        if (results.isEmpty()) { System.out.println("No matches."); return; }
        results.forEach(System.out::println);
    }

    private static void updateDoctor() throws InvalidDataException {
        System.out.print("Doctor ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Age: "); int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Gender: "); String gender = scanner.nextLine();
        System.out.print("Contact (10 digits): "); String contact = scanner.nextLine();
        System.out.print("Specialization: "); String specialization = scanner.nextLine();
        if (doctorService.updateDoctor(id, name, age, gender, contact, specialization)) {
            System.out.println("Doctor updated: " + doctorService.getDoctorById(id));
        } else {
            System.out.println("Doctor not found: " + id);
        }
    }

    private static void deleteDoctor() {
        System.out.print("Doctor ID: ");
        String id = scanner.nextLine().trim();
        System.out.println(doctorService.removeDoctor(id) ? "Doctor deleted: " + id : "Doctor not found: " + id);
    }

    private static void updatePatient() throws InvalidDataException {
        System.out.print("Patient ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Age: "); int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Gender: "); String gender = scanner.nextLine();
        System.out.print("Contact (10 digits): "); String contact = scanner.nextLine();
        System.out.print("Medical History: "); String history = scanner.nextLine();
        if (patientService.updatePatient(id, name, age, gender, contact, history)) {
            System.out.println("Patient updated: " + patientService.getPatientById(id));
        } else {
            System.out.println("Patient not found: " + id);
        }
    }

    private static void deletePatient() {
        System.out.print("Patient ID: ");
        String id = scanner.nextLine().trim();
        System.out.println(patientService.removePatient(id) ? "Patient deleted: " + id : "Patient not found: " + id);
    }
}
