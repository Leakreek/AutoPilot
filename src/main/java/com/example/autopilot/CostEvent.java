package com.example.autopilot;

import java.time.LocalDate;

public class CostEvent {
    private String description;
    private double amount;
    private LocalDate date;

    public CostEvent(String description, double amount, LocalDate date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return date + ": " + description + " - " + amount + " zł";
    }
}
