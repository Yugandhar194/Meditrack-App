package com.airtribe.meditrack.patterns.billing;

public interface BillingStrategy {
    double calculateTax(double amount);
}
