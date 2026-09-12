package com.airtribe.meditrack.entity;

import java.util.ArrayList;
import java.util.List;

public class Patient extends Person implements Cloneable {
    private static final long serialVersionUID = 1L;

    private String medicalHistory;
    private List<String> appointmentIds;

    public Patient(String id, String name, int age, String gender, String contactNumber, String medicalHistory) {
        super(id, name, age, gender, contactNumber);
        this.medicalHistory = medicalHistory;
        this.appointmentIds = new ArrayList<>();
    }

    public String getMedicalHistory() { return medicalHistory; }
    public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }
    public List<String> getAppointmentIds() { return appointmentIds; }
    public void addAppointmentId(String id) { appointmentIds.add(id); }

    @Override
    public Patient clone() {
        try {
            Patient copy = (Patient) super.clone();
            copy.appointmentIds = new ArrayList<>(appointmentIds);
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public String getRole() { return "Patient"; }

    @Override
    public String toString() {
        return super.toString() + " | History: " + medicalHistory + " | Appointments: " + appointmentIds.size();
    }
}
