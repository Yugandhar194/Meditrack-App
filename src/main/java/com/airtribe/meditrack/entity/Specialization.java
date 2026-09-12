package com.airtribe.meditrack.entity;

public enum Specialization {
    GENERAL_PHYSICIAN("General Physician"),
    CARDIOLOGIST("Cardiologist"),
    DERMATOLOGIST("Dermatologist"),
    DENTIST("Dentist"),
    ORTHOPEDIC("Orthopedic"),
    OPHTHALMOLOGIST("Ophthalmologist");

    private final String displayName;

    Specialization(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Specialization fromText(String text) {
        if (text == null) return GENERAL_PHYSICIAN;
        for (Specialization specialization : values()) {
            if (specialization.displayName.equalsIgnoreCase(text)
                    || specialization.name().equalsIgnoreCase(text.replace(' ', '_'))) {
                return specialization;
            }
        }
        return GENERAL_PHYSICIAN;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
