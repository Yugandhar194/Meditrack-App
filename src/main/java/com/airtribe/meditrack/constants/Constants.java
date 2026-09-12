package com.airtribe.meditrack.constants;

public class Constants {
    public static final String DOCTOR_ID_PREFIX = "DOC";
    public static final String PATIENT_ID_PREFIX = "PAT";
    public static final String APPOINTMENT_ID_PREFIX = "APT";
    public static final String BILL_ID_PREFIX = "BILL";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String DATA_DIR = "data/";
    public static final String DOCTORS_FILE = DATA_DIR + "doctors.csv";
    public static final String PATIENTS_FILE = DATA_DIR + "patients.csv";
    public static final String APPOINTMENTS_FILE = DATA_DIR + "appointments.csv";
    public static final double TAX_RATE = 0.18;

    private Constants() {
    }
}
