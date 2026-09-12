package com.airtribe.meditrack.patterns.billing;

import com.airtribe.meditrack.constants.Constants;

public class StandardBillingStrategy implements BillingStrategy {
    @Override
    public double calculateTax(double amount) { return amount * Constants.TAX_RATE; }
}
