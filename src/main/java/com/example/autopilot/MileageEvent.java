package com.example.autopilot;

import java.time.LocalDate;

public class MileageEvent {
    private final int kilometers;
    private final LocalDate date;

    public MileageEvent(int kilometers, LocalDate date) {
        this.kilometers = kilometers;
        this.date = date;
    }

    public int getKilometers() {
        return kilometers;
    }

    public LocalDate getDate() {
        return date;
    }
}
