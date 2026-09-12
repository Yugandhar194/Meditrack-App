package com.airtribe.meditrack.patterns.template;

public class ClinicSummaryReport extends ReportTemplate {
    private final int doctors;
    private final int patients;

    public ClinicSummaryReport(int doctors, int patients) {
        this.doctors = doctors;
        this.patients = patients;
    }

    @Override protected String header() { return "=== MediTrack Summary ==="; }
    @Override protected String body() { return "Doctors: " + doctors + ", Patients: " + patients; }
    @Override protected String footer() { return "=== End Summary ==="; }
}
