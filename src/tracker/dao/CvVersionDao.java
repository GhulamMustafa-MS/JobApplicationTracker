package tracker.dao;

import java.time.LocalDate;
import tracker.db.Db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;


public class CvVersionDao {
    
    public void printAllCvVersions() {
        String sql = "SELECT cv_id, cv_type, date_created FROM cv_versions ORDER BY cv_id";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nCV Versions:");
            while (rs.next()) {
                int id = rs.getInt("cv_id");
                String type = rs.getString("cv_type");
                String date = rs.getString("date_created");
                System.out.println("[ID="+id+ "] " + type + " CV created on " + date + ".");
            }

        } catch (Exception e) {
            System.out.println("Error reading CV versions: " + e.getMessage());
        }
    }
    public void printCvCount() {
        String sql = "SELECT COUNT(*) AS cv_count FROM cv_versions";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nCV count:");
            if (rs.next()) {
                int count = rs.getInt("cv_count");
                System.out.println("You have "+count+" number of CVs.");
            }

        } catch (Exception e) {
            System.out.println("Error reading CV count: " + e.getMessage());
        }
    }

    public void addCvVersion(String cv_type, LocalDate date_created) {
        String sql = "INSERT INTO cv_versions(cv_type, date_created) VALUES (?, ?)";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cv_type);
            ps.setDate(2, Date.valueOf(date_created));

            ps.executeUpdate();

            System.out.println("CV version added successfully.");

        } catch (Exception e) {
            System.out.println("Error adding CV version: " + e.getMessage());
        }
    }
         
}

