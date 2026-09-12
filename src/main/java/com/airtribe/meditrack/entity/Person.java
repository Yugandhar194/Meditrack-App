package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.Validator;

import java.io.Serializable;

/**
 * Abstract base class for all people in the system (Doctor, Patient).
 * Demonstrates abstraction + inheritance.
 */
public abstract class Person extends MedicalEntity {
    private static final long serialVersionUID = 1L;

    protected String id;
    protected String name;
    protected int age;
    protected String gender;
    protected String contactNumber;

    public Person(String id, String name, int age, String gender, String contactNumber) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = normalizeGender(gender);
        this.contactNumber = contactNumber;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = normalizeGender(gender); }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    /** Each subclass identifies its role - forces subclasses to specialize. */
    public abstract String getRole();

    private static String normalizeGender(String gender) {
        try {
            return Validator.normalizeGender(gender);
        } catch (InvalidDataException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Person)) return false;
        return id != null && id.equals(((Person) other).id);
    }

    @Override
    public int hashCode() { return id == null ? 0 : id.hashCode(); }

    @Override
    public String toString() {
        return String.format("[%s] %s | Age: %d | Gender: %s | Contact: %s",
                id, name, age, gender, contactNumber);
    }
}
