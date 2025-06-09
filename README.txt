#  AutoPilot — Organizer Pojazdów

**AutoPilot** to aplikacja desktopowa do zarządzania pojazdami — tankowania, przebiegi, przeglądy, ubezpieczenia i koszty eksploatacyjne.

##  Jak uruchomić projekt

1. Wejdź na GitHub i pobierz repozytorium (`Clone` lub `Download ZIP`)
2. Otwórz folder projektu w **IntelliJ IDEA** jako projekt Maven/Java
3. Uruchom `MainApp.java`

---

##  Struktura projektu

### `src/main/java/com.example.autopilot`

- **MainApp.java** – główny punkt startowy aplikacji
- **MainController.java** – zarządza głównym widokiem listy pojazdów
- **VehicleDetailsController.java** – widok szczegółów pojazdu, koszty, spalanie itp.
- **AddVehicleController.java** – dodawanie nowego pojazdu
- **EditVehicleController.java** – edytowanie tablicy rejestracyjnej i przebiegu

- **AddRefuelController.java** – okno dodawania tankowania
- **AddMileageController.java** – okno dodawania przebiegu
- **AddCostController.java** – okno dodawania kosztów ogólnych
- **AddInspectionController.java** – okno dodawania przeglądu technicznego
- **AddInsuranceController.java** – okno dodawania ubezpieczenia

- **Database.java** – cała logika połączenia z SQLite i operacje CRUD
- **Vehicle.java** – klasa reprezentująca pojazd
- **RefuelEvent.java**, **MileageEvent.java**, **CostEvent.java** – klasy pomocnicze

### `src/main/resources/com.example.autopilot`

- **add_vehicle.fxml** – GUI do dodania nowego pojazdu
- **edit_vehicle.fxml** – GUI do edycji pojazdu
- **add_refuel.fxml**, **add_mileage.fxml**, **add_cost.fxml** – GUI do tankowania, przebiegu, kosztów
- **add_inspection.fxml**, **add_insurance.fxml** – GUI do przeglądu i ubezpieczenia
- **vehicle_details.fxml** – szczegóły pojazdu + historia
- **main-view.fxml** – główne okno z listą pojazdów

---

