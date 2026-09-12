package com.airtribe.meditrack.util;

/**
 * Optional helper - a simple rule-based placeholder for a future
 * AI-assisted "suggest a specialization based on symptom" feature.
 * Not required for core functionality; included to satisfy the
 * optional AIHelper.java slot in the assignment structure.
 */
public class AIHelper {

    private AIHelper() {}

    public static String suggestSpecialization(String symptom) {
        if (symptom == null || symptom.trim().isEmpty()) return "General Physician";
        String s = symptom.toLowerCase();
        if (s.contains("heart") || s.contains("chest pain")) return "Cardiologist";
        if (s.contains("skin") || s.contains("rash")) return "Dermatologist";
        if (s.contains("tooth") || s.contains("teeth")) return "Dentist";
        if (s.contains("bone") || s.contains("fracture")) return "Orthopedic";
        if (s.contains("eye")) return "Ophthalmologist";
        return "General Physician";
    }

    public static java.util.List<String> suggestAppointmentSlots(java.time.LocalDate date) {
        java.util.List<String> slots = new java.util.ArrayList<>();
        for (int hour = 9; hour <= 16; hour++) slots.add(date + " " + String.format("%02d:00", hour));
        return slots;
    }
}
