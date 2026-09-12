package com.airtribe.meditrack.patterns.observer;

import com.airtribe.meditrack.entity.Appointment;

public class ConsoleAppointmentObserver implements AppointmentObserver {
    @Override
    public void onAppointmentCreated(Appointment appointment) {
        System.out.println("Notification: appointment " + appointment.getId() + " created for " + appointment.getDateTime());
    }
}
