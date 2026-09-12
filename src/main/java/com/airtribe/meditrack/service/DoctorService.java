package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.interfaces.Searchable;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;

import java.util.List;

public class DoctorService implements Searchable<Doctor> {
    private final DataStore<Doctor> doctorStore = new DataStore<>(Doctor::getId);

    public Doctor addDoctor(String name, int age, String gender, String contact, String specialization) throws InvalidDataException {
        return addDoctor(name, age, gender, contact, specialization, 0);
    }

    public Doctor addDoctor(String name, int age, String gender, String contact, String specialization, double consultationFee) throws InvalidDataException {
        Validator.validateName(name);
        Validator.validateAge(age);
        Validator.validateContact(contact);
        gender = Validator.normalizeGender(gender);
        String id = IdGenerator.nextDoctorId();
        Doctor doctor = new Doctor(id, name, age, gender, contact, specialization);
        doctor.setConsultationFee(consultationFee);
        doctorStore.add(doctor);
        return doctor;
    }

    public boolean removeDoctor(String id) {
        return doctorStore.remove(id);
    }

    public boolean updateDoctor(String id, String name, int age, String gender, String contact, String specialization)
            throws InvalidDataException {
        Doctor doctor = getDoctorById(id);
        if (doctor == null) return false;
        Validator.validateName(name); Validator.validateAge(age); Validator.validateContact(contact);
        gender = Validator.normalizeGender(gender);
        doctor.setName(name); doctor.setAge(age); doctor.setGender(gender);
        doctor.setContactNumber(contact); doctor.setSpecialization(specialization);
        return true;
    }

    public List<Doctor> getAllDoctors() {
        return doctorStore.getAll();
    }

    public Doctor getDoctorById(String id) {
        return doctorStore.getById(id).orElse(null);
    }

    @Override
    public List<Doctor> search(String keyword) {
        String k = keyword.toLowerCase();
        return doctorStore.filter(d -> d.getName().toLowerCase().contains(k)
                || d.getSpecialization().toLowerCase().contains(k));
    }

    public List<Doctor> getBySpecialization(String specialization) {
        return doctorStore.filter(d -> d.getSpecialization().equalsIgnoreCase(specialization));
    }

    public void addLoaded(Doctor doctor) { doctorStore.add(doctor); }

    public List<Doctor> getSortedByName() {
        return doctorStore.getAll().stream()
                .sorted(java.util.Comparator.comparing(Doctor::getName))
                .collect(java.util.stream.Collectors.toList());
    }

    public double averageConsultationFee() {
        return doctorStore.getAll().stream().mapToDouble(Doctor::getConsultationFee).average().orElse(0);
    }
}
