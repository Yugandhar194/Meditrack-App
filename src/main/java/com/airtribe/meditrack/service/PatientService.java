package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.interfaces.Searchable;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;

import java.util.List;

public class PatientService implements Searchable<Patient> {
    private final DataStore<Patient> patientStore = new DataStore<>(Patient::getId);

    public Patient addPatient(String name, int age, String gender, String contact, String medicalHistory) throws InvalidDataException {
        Validator.validateName(name);
        Validator.validateAge(age);
        Validator.validateContact(contact);
        gender = Validator.normalizeGender(gender);
        String id = IdGenerator.nextPatientId();
        Patient patient = new Patient(id, name, age, gender, contact, medicalHistory);
        patientStore.add(patient);
        return patient;
    }

    public boolean removePatient(String id) {
        return patientStore.remove(id);
    }

    public boolean updatePatient(String id, String name, int age, String gender, String contact, String history)
            throws InvalidDataException {
        Patient patient = getPatientById(id);
        if (patient == null) return false;
        Validator.validateName(name); Validator.validateAge(age); Validator.validateContact(contact);
        gender = Validator.normalizeGender(gender);
        patient.setName(name); patient.setAge(age); patient.setGender(gender);
        patient.setContactNumber(contact); patient.setMedicalHistory(history);
        return true;
    }

    public List<Patient> getAllPatients() {
        return patientStore.getAll();
    }

    public Patient getPatientById(String id) {
        return patientStore.getById(id).orElse(null);
    }

    @Override
    public List<Patient> search(String keyword) {
        if (keyword == null) return new java.util.ArrayList<>();
        String k = keyword.toLowerCase();
        return patientStore.filter(p -> p.getName().toLowerCase().contains(k));
    }

    public Patient searchPatientById(String id) { return getPatientById(id); }
    public List<Patient> searchPatientByName(String name) { return search(name); }
    public List<Patient> searchPatientByAge(int age) { return patientStore.filter(p -> p.getAge() == age); }

    public void addLoaded(Patient patient) { patientStore.add(patient); }
}
