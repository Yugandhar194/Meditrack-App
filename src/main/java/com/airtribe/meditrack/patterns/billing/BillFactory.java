package com.airtribe.meditrack.patterns.billing;

import com.airtribe.meditrack.entity.Bill;

public final class BillFactory {
    private BillFactory() {}

    public static Bill create(String billId, String appointmentId, double amount, BillingStrategy strategy) {
        Bill bill = new Bill(billId, appointmentId, amount);
        bill.setTaxAmount(strategy.calculateTax(amount));
        return bill;
    }

    public static BillingStrategy standardStrategy() { return new StandardBillingStrategy(); }
    public static BillingStrategy insuranceStrategy() { return new InsuranceBillingStrategy(); }
}
