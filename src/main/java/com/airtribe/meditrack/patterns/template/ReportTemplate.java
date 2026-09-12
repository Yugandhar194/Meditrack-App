package com.airtribe.meditrack.patterns.template;

public abstract class ReportTemplate {
    public final String generate() {
        StringBuilder report = new StringBuilder();
        report.append(header()).append(System.lineSeparator());
        report.append(body()).append(System.lineSeparator());
        report.append(footer());
        return report.toString();
    }

    protected abstract String header();
    protected abstract String body();
    protected abstract String footer();
}
