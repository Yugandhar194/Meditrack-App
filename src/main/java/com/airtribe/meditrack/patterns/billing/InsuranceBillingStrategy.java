package com.airtribe.meditrack.patterns.billing;

public class InsuranceBillingStrategy implements BillingStrategy {
    @Override
    public double calculateTax(double amount) { return 0; }
}
