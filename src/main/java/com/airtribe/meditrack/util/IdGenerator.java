package com.airtribe.meditrack.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread-safe sequential ID generator (uses AtomicInteger as a nod to
 * the "concurrency" bonus topic from the assignment).
 */
public class IdGenerator {
    private static final AtomicInteger doctorCounter = new AtomicInteger(1);
    private static final AtomicInteger patientCounter = new AtomicInteger(1);
    private static final AtomicInteger appointmentCounter = new AtomicInteger(1);
    private static final AtomicInteger billCounter = new AtomicInteger(1);

    private IdGenerator() {}

    public static String nextDoctorId() { return "DOC" + String.format("%03d", doctorCounter.getAndIncrement()); }
    public static String nextPatientId() { return "PAT" + String.format("%03d", patientCounter.getAndIncrement()); }
    public static String nextAppointmentId() { return "APT" + String.format("%03d", appointmentCounter.getAndIncrement()); }
    public static String nextBillId() { return "BILL" + String.format("%03d", billCounter.getAndIncrement()); }
}
