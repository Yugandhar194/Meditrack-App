package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.interfaces.Payable;

import java.io.Serializable;

public class Bill implements Payable, Serializable {
    private static final long serialVersionUID = 1L;

    private String billId;
    private String appointmentId;
    private double totalAmount;
    private double taxAmount;
    private double amountPaid;
    private boolean paid;

    public Bill(String billId, String appointmentId, double totalAmount) {
        this.billId = billId;
        this.appointmentId = appointmentId;
        this.totalAmount = totalAmount;
        this.taxAmount = 0;
        this.amountPaid = 0;
        this.paid = false;
    }

    public String getBillId() { return billId; }
    public String getAppointmentId() { return appointmentId; }

    @Override
    public double getAmount() { return totalAmount; }
    public double getTaxAmount() { return taxAmount; }
    public double getTotalWithTax() { return totalAmount + taxAmount; }
    public void setTaxAmount(double taxAmount) { this.taxAmount = taxAmount; }

    @Override
    public void pay(double amount) {
        amountPaid += amount;
        if (amountPaid >= totalAmount) {
            paid = true;
        }
    }

    @Override
    public boolean isPaid() { return paid; }

    public double getBalance() { return getTotalWithTax() - amountPaid; }

    @Override
    public String toString() {
        return String.format("Bill[%s] Appointment:%s Base:%.2f Tax:%.2f Total:%.2f Paid:%.2f Balance:%.2f Status:%s",
            billId, appointmentId, totalAmount, taxAmount, getTotalWithTax(), amountPaid, getBalance(), paid ? "PAID" : "PENDING");
    }
}
