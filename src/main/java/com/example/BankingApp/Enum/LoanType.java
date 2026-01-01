package com.example.BankingApp.Enum;

public enum LoanType {
    PERSONAL(500000),
    HOME(5000000),
    EDUCATION(1000000),
    VEHICLE(800000);

    private final double maxAmount;

    LoanType(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public double getMaxAmount() {
        return maxAmount;
    }
}
