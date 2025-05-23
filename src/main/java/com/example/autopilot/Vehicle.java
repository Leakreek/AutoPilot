package com.example.autopilot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private int id;
    private String typPojazdu;
    private String marka;
    private String model;
    private int rokProdukcji;
    private String rejestracja;
    private String typSilnika;
    private double pojemnosc;
    private int moc;
    private int przebieg;
    private String ubezpieczenieDo;
    private String przegladDo;

    private final List<RefuelEvent> refuels = new ArrayList<>();
    private final List<MileageEvent> mileage = new ArrayList<>();
    private final List<CostEvent> costs = new ArrayList<>();

    public Vehicle(String typPojazdu, String marka, String model, int rokProdukcji,
                   String rejestracja, String typSilnika, double pojemnosc, int moc,
                   int przebieg, String ubezpieczenieDo, String przegladDo) {
        this.typPojazdu = typPojazdu;
        this.marka = marka;
        this.model = model;
        this.rokProdukcji = rokProdukcji;
        this.rejestracja = rejestracja;
        this.typSilnika = typSilnika;
        this.pojemnosc = pojemnosc;
        this.moc = moc;
        this.przebieg = przebieg;
        this.ubezpieczenieDo = ubezpieczenieDo;
        this.przegladDo = przegladDo;
    }

    // Gettery i settery
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTypPojazdu() { return typPojazdu; }
    public void setTypPojazdu(String typPojazdu) { this.typPojazdu = typPojazdu; }

    public String getMarka() { return marka; }
    public void setMarka(String marka) { this.marka = marka; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getRokProdukcji() { return rokProdukcji; }
    public void setRokProdukcji(int rokProdukcji) { this.rokProdukcji = rokProdukcji; }

    public String getRejestracja() { return rejestracja; }
    public void setRejestracja(String rejestracja) { this.rejestracja = rejestracja; }

    public String getTypSilnika() { return typSilnika; }
    public void setTypSilnika(String typSilnika) { this.typSilnika = typSilnika; }

    public double getPojemnosc() { return pojemnosc; }
    public void setPojemnosc(double pojemnosc) { this.pojemnosc = pojemnosc; }

    public int getMoc() { return moc; }
    public void setMoc(int moc) { this.moc = moc; }

    public int getPrzebieg() { return przebieg; }
    public void setPrzebieg(int przebieg) { this.przebieg = przebieg; }

    public String getUbezpieczenieDo() { return ubezpieczenieDo; }
    public void setUbezpieczenieDo(String ubezpieczenieDo) { this.ubezpieczenieDo = ubezpieczenieDo; }

    public String getPrzegladDo() { return przegladDo; }
    public void setPrzegladDo(String przegladDo) { this.przegladDo = przegladDo; }

    // Tankowania
    public void addRefuel(RefuelEvent refuel) {
        refuels.add(refuel);
    }
    public List<RefuelEvent> getRefuels() {
        return refuels;
    }

    // Przebieg
    public void addMileage(MileageEvent mileageEvent) {
        mileage.add(mileageEvent);
        this.przebieg += mileageEvent.getKilometers(); // aktualizacja przebiegu
    }
    public List<MileageEvent> getMileageEvents() {
        return mileage;
    }

    // Koszty
    public void addCost(CostEvent cost) {
        costs.add(cost);
    }
    public List<CostEvent> getCosts() {
        return costs;
    }

    // Statystyki
    public double getTotalFuelUsed() {
        return refuels.stream().mapToDouble(RefuelEvent::getLiters).sum();
    }

    public double getTotalDistance() {
        return mileage.stream().mapToInt(MileageEvent::getKilometers).sum();
    }

    public double getAverageFuelConsumption() {
        double km = getTotalDistance();
        double liters = getTotalFuelUsed();
        return km == 0 ? 0.0 : (liters / km) * 100.0;
    }

    @Override
    public String toString() {
        return typPojazdu + " | " + marka + " " + model + " (" + rokProdukcji + ") - " + rejestracja;
    }
}
