package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.interfaces.Searchable;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.patterns.billing.BillFactory;
import com.airtribe.meditrack.patterns.billing.BillingStrategy;
import com.airtribe.meditrack.patterns.observer.AppointmentObserver;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentService implements Searchable<Appointment> {
    private final DataStore<Appointment> appointmentStore = new DataStore<>(Appointment::getId);
    private final DataStore<Bill> billStore = new DataStore<>(Bill::getBillId);

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final java.util.List<AppointmentObserver> observers = new java.util.ArrayList<>();

    public AppointmentService(DoctorService doctorService, PatientService patientService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    public Appointment bookAppointment(String patientId, String doctorId, LocalDateTime dateTime, String reason) throws InvalidDataException {
        if (doctorService.getDoctorById(doctorId) == null) {
            throw new InvalidDataException("Doctor not found: " + doctorId);
        }
        if (patientService.getPatientById(patientId) == null) {
            throw new InvalidDataException("Patient not found: " + patientId);
        }

        boolean clash = !appointmentStore.filter(a -> a.getDoctorId().equals(doctorId)
                && a.getDateTime().equals(dateTime)
                && a.getStatus() == Appointment.Status.SCHEDULED)
                .isEmpty();
        if (clash) {
            throw new InvalidDataException("Doctor already has an appointment at this time.");
        }

        String id = IdGenerator.nextAppointmentId();
        Appointment appointment = new Appointment(id, patientId, doctorId, dateTime, reason);
        appointmentStore.add(appointment);
        patientService.getPatientById(patientId).addAppointmentId(id);
        observers.forEach(observer -> observer.onAppointmentCreated(appointment));
        return appointment;
    }

    public void cancelAppointment(String id) throws AppointmentNotFoundException {
        Appointment appointment = appointmentStore.getById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found: " + id));
        appointment.setStatus(Appointment.Status.CANCELLED);
    }

    public Bill generateBill(String appointmentId, double amount) throws AppointmentNotFoundException {
        return generateBill(appointmentId, amount, BillFactory.standardStrategy());
    }

    public Bill generateBill(String appointmentId, double amount, BillingStrategy strategy) throws AppointmentNotFoundException {
        Appointment appointment = appointmentStore.getById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found: " + appointmentId));
        appointment.setStatus(Appointment.Status.COMPLETED);
        String billId = IdGenerator.nextBillId();
        Bill bill = BillFactory.create(billId, appointmentId, amount, strategy);
        billStore.add(bill);
        return bill;
    }

    public List<Appointment> getAllAppointments() { return appointmentStore.getAll(); }
    public List<Bill> getAllBills() { return billStore.getAll(); }

    public List<Appointment> getAppointmentsForDoctor(String doctorId) {
        return appointmentStore.filter(a -> a.getDoctorId().equals(doctorId));
    }

    public List<Appointment> getAppointmentsForPatient(String patientId) {
        return appointmentStore.filter(a -> a.getPatientId().equals(patientId));
    }

    public void addLoaded(Appointment appointment) { appointmentStore.add(appointment); }
    public void addObserver(AppointmentObserver observer) { observers.add(observer); }

    public java.util.Map<String, Long> appointmentsPerDoctor() {
        return appointmentStore.getAll().stream().collect(java.util.stream.Collectors.groupingBy(
                Appointment::getDoctorId, java.util.stream.Collectors.counting()));
    }

    @Override
    public List<Appointment> search(String keyword) {
        String k = keyword.toLowerCase();
        return appointmentStore.filter(a -> a.getReason().toLowerCase().contains(k));
    }
}
