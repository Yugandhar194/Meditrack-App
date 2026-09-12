package com.airtribe.meditrack.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Appointment implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    public enum Status { SCHEDULED, COMPLETED, CANCELLED }

    private String id;
    private String patientId;
    private String doctorId;
    private LocalDateTime dateTime;
    private Status status;
    private String reason;

    public Appointment(String id, String patientId, String doctorId, LocalDateTime dateTime, String reason) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateTime = dateTime;
        this.reason = reason;
        this.status = Status.SCHEDULED;
    }

    public String getId() { return id; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public String getReason() { return reason; }

    public AppointmentStatus getAppointmentStatus() {
        return status == Status.SCHEDULED ? AppointmentStatus.CONFIRMED
                : AppointmentStatus.valueOf(status.name());
    }

    @Override
    public Appointment clone() {
        try {
            return (Appointment) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public String toString() {
        return String.format("Appointment[%s] Patient:%s Doctor:%s At:%s Status:%s Reason:%s",
                id, patientId, doctorId, dateTime, status, reason);
    }
}
