package com.airtribe.meditrack.entity;

/**
 * Immutable read-only snapshot of a bill - safe to pass around without
 * risk of accidental mutation. All fields are final, no setters exist,
 * and the class itself is final so it cannot be subclassed to add mutability.
 */
public final class BillSummary {
    private final String billId;
    private final String patientName;
    private final double totalAmount;
    private final boolean paid;

    public BillSummary(String billId, String patientName, double totalAmount, boolean paid) {
        this.billId = billId;
        this.patientName = patientName;
        this.totalAmount = totalAmount;
        this.paid = paid;
    }

    public String getBillId() { return billId; }
    public String getPatientName() { return patientName; }
    public double getTotalAmount() { return totalAmount; }
    public boolean isPaid() { return paid; }

    @Override
    public String toString() {
        return String.format("BillSummary[%s] Patient:%s Amount:%.2f Status:%s",
                billId, patientName, totalAmount, paid ? "PAID" : "PENDING");
    }
}
