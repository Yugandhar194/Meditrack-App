package com.airtribe.meditrack.util;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class DataPersistence {
    private DataPersistence() {}

    public static void save(DoctorService doctors, PatientService patients, AppointmentService appointments) throws IOException {
        List<String[]> doctorRows = new ArrayList<>();
        for (Doctor doctor : doctors.getAllDoctors()) {
            doctorRows.add(new String[]{doctor.getId(), doctor.getName(), String.valueOf(doctor.getAge()),
                    doctor.getGender(), doctor.getContactNumber(), doctor.getSpecialization(),
                    String.valueOf(doctor.getConsultationFee())});
        }
        CSVUtil.writeCSV(Constants.DOCTORS_FILE, doctorRows);

        List<String[]> patientRows = new ArrayList<>();
        for (Patient patient : patients.getAllPatients()) {
            patientRows.add(new String[]{patient.getId(), patient.getName(), String.valueOf(patient.getAge()),
                    patient.getGender(), patient.getContactNumber(), patient.getMedicalHistory()});
        }
        CSVUtil.writeCSV(Constants.PATIENTS_FILE, patientRows);

        List<String[]> appointmentRows = new ArrayList<>();
        for (Appointment appointment : appointments.getAllAppointments()) {
            appointmentRows.add(new String[]{appointment.getId(), appointment.getPatientId(), appointment.getDoctorId(),
                    appointment.getDateTime().toString(), appointment.getStatus().name(), appointment.getReason()});
        }
        CSVUtil.writeCSV(Constants.APPOINTMENTS_FILE, appointmentRows);
    }

    public static void load(DoctorService doctors, PatientService patients, AppointmentService appointments)
            throws IOException, InvalidDataException {
        for (String[] row : CSVUtil.readCSV(Constants.DOCTORS_FILE)) {
            if (row.length >= 6) {
                Doctor doctor = new Doctor(row[0], row[1], Integer.parseInt(row[2]), row[3], row[4], row[5]);
                if (row.length >= 7) doctor.setConsultationFee(Double.parseDouble(row[6]));
                doctors.addLoaded(doctor);
            }
        }
        for (String[] row : CSVUtil.readCSV(Constants.PATIENTS_FILE)) {
            if (row.length >= 6) patients.addLoaded(new Patient(row[0], row[1], Integer.parseInt(row[2]), row[3], row[4], row[5]));
        }
        for (String[] row : CSVUtil.readCSV(Constants.APPOINTMENTS_FILE)) {
            if (row.length >= 6 && patients.getPatientById(row[1]) != null && doctors.getDoctorById(row[2]) != null) {
                Appointment appointment = new Appointment(row[0], row[1], row[2], LocalDateTime.parse(row[3]), row[5]);
                appointment.setStatus(Appointment.Status.valueOf(row[4]));
                appointments.addLoaded(appointment);
                patients.getPatientById(row[1]).addAppointmentId(row[0]);
            }
        }
    }
}
