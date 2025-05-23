package com.example.autopilot;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:autopilot.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initDatabase() {
        String createVehicles = """
            CREATE TABLE IF NOT EXISTS vehicles (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                typ_pojazdu TEXT,
                marka TEXT NOT NULL,
                model TEXT NOT NULL,
                rok_produkcji INTEGER,
                rejestracja TEXT,
                typ_silnika TEXT,
                pojemnosc REAL,
                moc INTEGER,
                przebieg INTEGER,
                ubezpieczenie TEXT,
                przeglad TEXT
            );
        """;

        String createRefuels = """
            CREATE TABLE IF NOT EXISTS refuels (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                vehicle_id INTEGER NOT NULL,
                liters REAL NOT NULL,
                price REAL NOT NULL,
                date TEXT NOT NULL,
                FOREIGN KEY(vehicle_id) REFERENCES vehicles(id)
            );
        """;

        String createMileages = """
            CREATE TABLE IF NOT EXISTS mileages (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                vehicle_id INTEGER NOT NULL,
                kilometers INTEGER NOT NULL,
                date TEXT NOT NULL,
                FOREIGN KEY(vehicle_id) REFERENCES vehicles(id)
            );
        """;

        String createCosts = """
            CREATE TABLE IF NOT EXISTS costs (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                vehicle_id INTEGER NOT NULL,
                description TEXT,
                amount REAL,
                date TEXT,
                FOREIGN KEY(vehicle_id) REFERENCES vehicles(id)
            );
        """;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createVehicles);
            stmt.execute(createRefuels);
            stmt.execute(createMileages);
            stmt.execute(createCosts);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertVehicle(Vehicle v) {
        String sql = """
            INSERT INTO vehicles(typ_pojazdu, marka, model, rok_produkcji, rejestracja,
                                 typ_silnika, pojemnosc, moc, przebieg, ubezpieczenie, przeglad)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, v.getTypPojazdu());
            pstmt.setString(2, v.getMarka());
            pstmt.setString(3, v.getModel());
            pstmt.setInt(4, v.getRokProdukcji());
            pstmt.setString(5, v.getRejestracja());
            pstmt.setString(6, v.getTypSilnika());
            pstmt.setDouble(7, v.getPojemnosc());
            pstmt.setInt(8, v.getMoc());
            pstmt.setInt(9, v.getPrzebieg());
            pstmt.setString(10, v.getUbezpieczenieDo());
            pstmt.setString(11, v.getPrzegladDo());

            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                v.setId(keys.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertRefuel(int vehicleId, RefuelEvent refuel) {
        String sql = "INSERT INTO refuels(vehicle_id, liters, price, date) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, vehicleId);
            pstmt.setDouble(2, refuel.getLiters());
            pstmt.setDouble(3, refuel.getPrice());
            pstmt.setString(4, refuel.getDate().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<RefuelEvent> getRefuelsByVehicleId(int vehicleId) {
        List<RefuelEvent> list = new ArrayList<>();
        String sql = "SELECT * FROM refuels WHERE vehicle_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, vehicleId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new RefuelEvent(
                        rs.getDouble("liters"),
                        rs.getDouble("price"),
                        LocalDate.parse(rs.getString("date"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void insertMileage(int vehicleId, MileageEvent mileage) {
        String sql = "INSERT INTO mileages(vehicle_id, kilometers, date) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, vehicleId);
            pstmt.setInt(2, mileage.getKilometers());
            pstmt.setString(3, mileage.getDate().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<MileageEvent> getMileagesByVehicleId(int vehicleId) {
        List<MileageEvent> list = new ArrayList<>();
        String sql = "SELECT * FROM mileages WHERE vehicle_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, vehicleId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new MileageEvent(
                        rs.getInt("kilometers"),
                        LocalDate.parse(rs.getString("date"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void insertCost(int vehicleId, CostEvent cost) {
        String sql = "INSERT INTO costs(vehicle_id, description, amount, date) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, vehicleId);
            pstmt.setString(2, cost.getDescription());
            pstmt.setDouble(3, cost.getAmount());
            pstmt.setString(4, cost.getDate().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<CostEvent> getCostsByVehicleId(int vehicleId) {
        List<CostEvent> list = new ArrayList<>();
        String sql = "SELECT * FROM costs WHERE vehicle_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, vehicleId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new CostEvent(
                        rs.getString("description"),
                        rs.getDouble("amount"),
                        LocalDate.parse(rs.getString("date"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Vehicle> getAllVehicles() {
        List<Vehicle> list = new ArrayList<>();
        String sql = "SELECT * FROM vehicles";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vehicle v = new Vehicle(
                        rs.getString("typ_pojazdu"),
                        rs.getString("marka"),
                        rs.getString("model"),
                        rs.getInt("rok_produkcji"),
                        rs.getString("rejestracja"),
                        rs.getString("typ_silnika"),
                        rs.getDouble("pojemnosc"),
                        rs.getInt("moc"),
                        rs.getInt("przebieg"),
                        rs.getString("ubezpieczenie"),
                        rs.getString("przeglad")
                );
                v.setId(rs.getInt("id"));

                getRefuelsByVehicleId(v.getId()).forEach(v::addRefuel);
                getMileagesByVehicleId(v.getId()).forEach(v::addMileage);
                getCostsByVehicleId(v.getId()).forEach(v::addCost);

                list.add(v);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void deleteVehicleById(int id) {
        String sql = "DELETE FROM vehicles WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateVehicle(Vehicle vehicle) {
        String sql = """
        UPDATE vehicles
        SET rejestracja = ?, przebieg = ?, ubezpieczenie = ?, przeglad = ?
        WHERE id = ?
    """;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, vehicle.getRejestracja());
            pstmt.setInt(2, vehicle.getPrzebieg());
            pstmt.setString(3, vehicle.getUbezpieczenieDo());
            pstmt.setString(4, vehicle.getPrzegladDo());
            pstmt.setInt(5, vehicle.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
