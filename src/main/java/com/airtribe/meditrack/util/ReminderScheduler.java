package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.Appointment;

import java.util.Timer;
import java.util.TimerTask;

public class ReminderScheduler {
    private final Timer timer = new Timer("meditrack-reminders", true);

    public void schedule(Appointment appointment, long delayMillis) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Reminder: appointment " + appointment.getId() + " at " + appointment.getDateTime());
            }
        }, delayMillis);
    }

    public void shutdown() { timer.cancel(); }
}
