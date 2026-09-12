package com.airtribe.meditrack.util;

import com.airtribe.meditrack.exception.InvalidDataException;

public class Validator {

    private Validator() {}

    public static void validateName(String name) throws InvalidDataException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Name cannot be empty.");
        }
    }

    public static void validateAge(int age) throws InvalidDataException {
        if (age <= 0 || age > 120) {
            throw new InvalidDataException("Age must be between 1 and 120.");
        }
    }

    public static void validateContact(String contact) throws InvalidDataException {
        if (contact == null || !contact.matches("\\d{10}")) {
            throw new InvalidDataException("Contact number must be exactly 10 digits.");
        }
    }

    public static String normalizeGender(String gender) throws InvalidDataException {
        if (gender == null) throw new InvalidDataException("Gender must be Male, Female, M, or F.");
        String value = gender.trim();
        if (value.equalsIgnoreCase("m") || value.equalsIgnoreCase("male")) return "Male";
        if (value.equalsIgnoreCase("f") || value.equalsIgnoreCase("female")) return "Female";
        throw new InvalidDataException("Gender must be Male, Female, M, or F.");
    }
}
