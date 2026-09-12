package com.airtribe.meditrack.util;

public final class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();
    private static final String PROFILE;
    private final String applicationName;

    static {
        PROFILE = "default";
    }

    private AppConfig() {
        applicationName = "MediTrack";
    }

    public static AppConfig getInstance() { return INSTANCE; }
    public String getApplicationName() { return applicationName; }
    public String getProfile() { return PROFILE; }
}
