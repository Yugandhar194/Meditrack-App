package com.airtribe.meditrack.test;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;
import com.airtribe.meditrack.entity.AppointmentStatus;
import com.airtribe.meditrack.patterns.billing.BillFactory;
import com.airtribe.meditrack.util.AppConfig;
import com.airtribe.meditrack.util.SerializationUtil;

import java.time.LocalDateTime;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Manual test harness - no JUnit, as required by the assignment.
 * Run with: java com.airtribe.meditrack.test.TestRunner
 */
public class TestRunner {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        testAddDoctor();
        testAddPatient();
        testBookAppointment();
        testInvalidAge();
        testCancelAppointment();
        testGenerateBill();
        testAppointmentNotFound();
        testCloneAndEnum();
        testBillingStrategy();
        testStreamsAndSingleton();
        testSerialization();

        System.out.println("\n=== Test Summary ===");
        System.out.println("Passed: " + passed + " | Failed: " + failed);
    }

    private static void assertTrue(String testName, boolean condition) {
        if (condition) {
            System.out.println("[PASS] " + testName);
            passed++;
        } else {
            System.out.println("[FAIL] " + testName);
            failed++;
        }
    }

    private static void testAddDoctor() {
        try {
            DoctorService service = new DoctorService();
            Doctor d = service.addDoctor("Dr. Asha Rao", 40, "Female", "9876543210", "Cardiologist");
            assertTrue("Add valid doctor", d != null && d.getId().startsWith("DOC"));
        } catch (Exception e) {
            assertTrue("Add valid doctor", false);
        }
    }

    private static void testAddPatient() {
        try {
            PatientService service = new PatientService();
            Patient p = service.addPatient("Rahul Verma", 30, "Male", "9123456780", "None");
            assertTrue("Add valid patient", p != null && p.getId().startsWith("PAT"));
        } catch (Exception e) {
            assertTrue("Add valid patient", false);
        }
    }

    private static void testBookAppointment() {
        try {
            DoctorService ds = new DoctorService();
            PatientService ps = new PatientService();
            AppointmentService as = new AppointmentService(ds, ps);
            Doctor d = ds.addDoctor("Dr. Kiran", 45, "Male", "9988776655", "Dermatologist");
            Patient p = ps.addPatient("Sneha Patil", 25, "Female", "9090909090", "None");
            Appointment a = as.bookAppointment(p.getId(), d.getId(), LocalDateTime.now().plusDays(1), "Skin checkup");
            assertTrue("Book valid appointment", a != null && a.getStatus() == Appointment.Status.SCHEDULED);
        } catch (Exception e) {
            assertTrue("Book valid appointment", false);
        }
    }

    private static void testInvalidAge() {
        try {
            DoctorService service = new DoctorService();
            service.addDoctor("Dr. Invalid", -5, "Male", "9876543210", "General");
            assertTrue("Reject invalid age", false);
        } catch (InvalidDataException e) {
            assertTrue("Reject invalid age", true);
        } catch (Exception e) {
            assertTrue("Reject invalid age", false);
        }
    }

    private static void testCancelAppointment() {
        try {
            DoctorService ds = new DoctorService();
            PatientService ps = new PatientService();
            AppointmentService as = new AppointmentService(ds, ps);
            Doctor d = ds.addDoctor("Dr. Mehta", 50, "Male", "9876501234", "Orthopedic");
            Patient p = ps.addPatient("Anil Kumar", 35, "Male", "9012345678", "Fracture history");
            Appointment a = as.bookAppointment(p.getId(), d.getId(), LocalDateTime.now().plusDays(2), "Follow-up");
            as.cancelAppointment(a.getId());
            assertTrue("Cancel appointment", a.getStatus() == Appointment.Status.CANCELLED);
        } catch (Exception e) {
            assertTrue("Cancel appointment", false);
        }
    }

    private static void testGenerateBill() {
        try {
            DoctorService ds = new DoctorService();
            PatientService ps = new PatientService();
            AppointmentService as = new AppointmentService(ds, ps);
            Doctor d = ds.addDoctor("Dr. Nair", 38, "Female", "9871234560", "General Physician");
            Patient p = ps.addPatient("Meena Joshi", 29, "Female", "9823456701", "None");
            Appointment a = as.bookAppointment(p.getId(), d.getId(), LocalDateTime.now().plusDays(3), "Fever");
            Bill bill = as.generateBill(a.getId(), 500.0);
            assertTrue("Generate bill", bill != null && bill.getAmount() == 500.0);
        } catch (Exception e) {
            assertTrue("Generate bill", false);
        }
    }

    private static void testAppointmentNotFound() {
        try {
            DoctorService ds = new DoctorService();
            PatientService ps = new PatientService();
            AppointmentService as = new AppointmentService(ds, ps);
            as.cancelAppointment("APT999");
            assertTrue("Reject cancel of missing appointment", false);
        } catch (AppointmentNotFoundException e) {
            assertTrue("Reject cancel of missing appointment", true);
        } catch (Exception e) {
            assertTrue("Reject cancel of missing appointment", false);
        }
    }

    private static void testCloneAndEnum() {
        Patient patient = new Patient("PAT-CLONE", "Clone Test", 20, "M", "9000000000", "None");
        patient.addAppointmentId("APT-1");
        Patient copy = patient.clone();
        copy.addAppointmentId("APT-2");
        Appointment appointment = new Appointment("APT-CLONE", patient.getId(), "DOC-1", LocalDateTime.now(), "Test");
        assertTrue("Deep clone copies patient list", patient.getAppointmentIds().size() == 1 && copy.getAppointmentIds().size() == 2);
        assertTrue("Appointment enum maps to confirmed", appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED);
    }

    private static void testBillingStrategy() {
        Bill standard = BillFactory.create("BILL-T", "APT-T", 100, BillFactory.standardStrategy());
        Bill insurance = BillFactory.create("BILL-I", "APT-I", 100, BillFactory.insuranceStrategy());
        assertTrue("Standard billing applies tax", standard.getTaxAmount() == 18.0 && standard.getTotalWithTax() == 118.0);
        assertTrue("Insurance billing waives tax", insurance.getTaxAmount() == 0.0);
    }

    private static void testStreamsAndSingleton() {
        DoctorService service = new DoctorService();
        try {
            service.addDoctor("Fee A", 30, "M", "9000000001", "Dentist", 100);
            service.addDoctor("Fee B", 31, "F", "9000000002", "Dentist", 200);
            assertTrue("Stream average fee", service.averageConsultationFee() == 150.0);
            assertTrue("Singleton configuration", AppConfig.getInstance() == AppConfig.getInstance());
        } catch (Exception e) { assertTrue("Stream average fee", false); }
    }

    private static void testSerialization() {
        try {
            Path path = Files.createTempFile("meditrack", ".ser");
            Patient original = new Patient("PAT-SER", "Serialized", 22, "F", "9000000003", "None");
            SerializationUtil.write(original, path);
            Patient restored = (Patient) SerializationUtil.read(path);
            Files.deleteIfExists(path);
            assertTrue("Serialization round trip", original.equals(restored) && restored.getName().equals("Serialized"));
        } catch (Exception e) { assertTrue("Serialization round trip", false); }
    }
}
