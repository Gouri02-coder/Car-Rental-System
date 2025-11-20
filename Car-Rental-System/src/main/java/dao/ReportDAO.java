package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import model.Report;

public class ReportDAO {
    private final Connection connection;

    public ReportDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Report> getRecentBookings() throws SQLException {
        List<Report> reports = new ArrayList<>();

        String sql = "SELECT c.brand, c.model " +
                     "FROM bookings b " +
                     "JOIN cars c ON b.vehicle_id = c.id " +
                     "ORDER BY b.booking_date DESC LIMIT 5";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Report r = new Report();
                r.setBrand(rs.getString("brand"));
                r.setModel(rs.getString("model"));
                reports.add(r);
            }
        }
        return reports;
    }

    public List<Report> getVehicleStatusReport() throws SQLException {
        List<Report> reports = new ArrayList<>();
        String sql = "SELECT id, brand, model, year, registration_number, status, daily_rate " +
                     "FROM cars ORDER BY status, brand, model";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Report r = new Report();
                r.setId(rs.getInt("id"));
                r.setBrand(rs.getString("brand"));
                r.setModel(rs.getString("model"));
                r.setYear(rs.getInt("year"));
                r.setRegistrationNumber(rs.getString("registration_number"));
                r.setStatus(rs.getString("status"));
                r.setDailyRate(rs.getBigDecimal("daily_rate"));
                reports.add(r);
            }
        }
        return reports;
    }

    public Report getFleetSummaryReport() throws SQLException {
        String sql = "SELECT " +
                     "COUNT(*) AS total_vehicles, " +
                     "COUNT(CASE WHEN status = 'Available' THEN 1 END) AS available_vehicles, " +
                     "COUNT(CASE WHEN status = 'Rented' THEN 1 END) AS rented_vehicles, " +
                     "COUNT(CASE WHEN status = 'Maintenance' THEN 1 END) AS maintenance_vehicles, " +
                     "AVG(daily_rate) AS avg_daily_rate " +
                     "FROM cars";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                Report r = new Report();
                r.setTotalVehicles(rs.getInt("total_vehicles"));
                r.setAvailableVehicles(rs.getInt("available_vehicles"));
                r.setRentedVehicles(rs.getInt("rented_vehicles"));
                r.setMaintenanceVehicles(rs.getInt("maintenance_vehicles"));
                r.setAverageRate(rs.getBigDecimal("avg_daily_rate"));
                return r;
            }
        }
        return null;
    }

    public List<Report> getVehiclesByTypeReport() throws SQLException {
        List<Report> reports = new ArrayList<>();
        String sql = "SELECT type, COUNT(*) AS count, AVG(daily_rate) AS avg_rate " +
                     "FROM cars GROUP BY type ORDER BY count DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Report r = new Report();
                r.setType(rs.getString("type"));
                r.setCount(rs.getInt("count"));
                r.setAverageRate(rs.getBigDecimal("avg_rate"));
                reports.add(r);
            }
        }
        return reports;
    }

    public List<Report> getVehiclesByFuelTypeReport() throws SQLException {
        List<Report> reports = new ArrayList<>();
        String sql = "SELECT fuel_type, COUNT(*) AS count, AVG(daily_rate) AS avg_rate " +
                     "FROM cars GROUP BY fuel_type ORDER BY count DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Report r = new Report();
                r.setFuelType(rs.getString("fuel_type"));
                r.setCount(rs.getInt("count"));
                r.setAverageRate(rs.getBigDecimal("avg_rate"));
                reports.add(r);
            }
        }
        return reports;
    }

    public List<Report> getHighValueVehiclesReport() throws SQLException {
        List<Report> reports = new ArrayList<>();
        String sql = "SELECT id, brand, model, year, registration_number, status, daily_rate " +
                     "FROM cars WHERE daily_rate > (SELECT AVG(daily_rate) FROM cars) " +
                     "ORDER BY daily_rate DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Report r = new Report();
                r.setId(rs.getInt("id"));
                r.setBrand(rs.getString("brand"));
                r.setModel(rs.getString("model"));
                r.setYear(rs.getInt("year"));
                r.setRegistrationNumber(rs.getString("registration_number"));
                r.setStatus(rs.getString("status"));
                r.setDailyRate(rs.getBigDecimal("daily_rate"));
                reports.add(r);
            }
        }
        return reports;
    }

    public List<Report> getAvailableVehicles() throws SQLException {
        List<Report> reports = new ArrayList<>();
        String sql = "SELECT id, brand, model, year, type, fuel_type, daily_rate, color " +
                     "FROM cars WHERE status = 'Available' ORDER BY daily_rate ASC";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Report r = new Report();
                r.setId(rs.getInt("id"));
                r.setBrand(rs.getString("brand"));
                r.setModel(rs.getString("model"));
                r.setYear(rs.getInt("year"));
                r.setType(rs.getString("type"));
                r.setFuelType(rs.getString("fuel_type"));
                r.setDailyRate(rs.getBigDecimal("daily_rate"));
                r.setColor(rs.getString("color"));
                reports.add(r);
            }
        }
        return reports;
    }
}
