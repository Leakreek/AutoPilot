package com.example.autopilot;

import java.time.LocalDate;

public class RefuelEvent {
    private double liters;
    private double price;
    private LocalDate date;

    public RefuelEvent(double liters, double price, LocalDate date) {
        this.liters = liters;
        this.price = price;
        this.date = date;
    }

    public double getLiters() {
        return liters;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return date + ": " + liters + "L za " + price + " zł";
    }
}
