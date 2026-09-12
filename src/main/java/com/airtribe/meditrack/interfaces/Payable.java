package com.airtribe.meditrack.interfaces;

/**
 * Contract for anything that can be paid (e.g. Bill).
 */
public interface Payable {
    double getAmount();
    void pay(double amount);
    boolean isPaid();
}
