package com.airtribe.meditrack.entity;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends Person {
    private static final long serialVersionUID = 1L;

    private Specialization specialization;
    private double consultationFee;
    private List<String> availableSlots;

    public Doctor(String id, String name, int age, String gender, String contactNumber, String specialization) {
        super(id, name, age, gender, contactNumber);
        this.specialization = Specialization.fromText(specialization);
        this.consultationFee = 0;
        this.availableSlots = new ArrayList<>();
    }

    public String getSpecialization() { return specialization.getDisplayName(); }
    public Specialization getSpecializationType() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = Specialization.fromText(specialization); }
    public double getConsultationFee() { return consultationFee; }
    public void setConsultationFee(double consultationFee) { this.consultationFee = consultationFee; }
    public List<String> getAvailableSlots() { return availableSlots; }
    public void addSlot(String slot) { availableSlots.add(slot); }
    public boolean removeSlot(String slot) { return availableSlots.remove(slot); }

    @Override
    public String getRole() { return "Doctor"; }

    @Override
    public String toString() {
        return super.toString() + " | Specialization: " + specialization + " | Slots: " + availableSlots;
    }
}
