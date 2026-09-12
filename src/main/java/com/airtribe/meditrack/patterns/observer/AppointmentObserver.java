package com.airtribe.meditrack.patterns.observer;

import com.airtribe.meditrack.entity.Appointment;

public interface AppointmentObserver {
    void onAppointmentCreated(Appointment appointment);
}
