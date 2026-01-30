package tracker.dao;

import tracker.db.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnalyticsDao {

    // 1) Applications per CV
    public void applicationsPerCv() {
        String sql =
            "SELECT cv.cv_id, cv.cv_type, COUNT(ja.job_id) AS total_applications " +
            "FROM cv_versions cv " +
            "LEFT JOIN job_applications ja ON cv.cv_id = ja.cv_id " +
            "GROUP BY cv.cv_id, cv.cv_type " +
            "ORDER BY total_applications DESC";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n=== Applications per CV ===");
            while (rs.next()) {
                int cvId = rs.getInt("cv_id");
                String cvType = rs.getString("cv_type");
                int total = rs.getInt("total_applications");
                System.out.println("CV ID: " + cvId + " (" + cvType + ") -> " + total);
            }

        } catch (SQLException e) {
            System.out.println("Error (Applications per CV): " + e.getMessage());
        }
    }

    // 2) Interviews per CV
    public void interviewsPerCv() {
        String sql =
            "SELECT cv.cv_id, cv.cv_type, COUNT(i.interview_id) AS total_interviews " +
            "FROM cv_versions cv " +
            "LEFT JOIN job_applications ja ON cv.cv_id = ja.cv_id " +
            "LEFT JOIN interviews i ON ja.job_id = i.job_id " +
            "GROUP BY cv.cv_id, cv.cv_type " +
            "ORDER BY total_interviews DESC";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n=== Interviews per CV ===");
            while (rs.next()) {
                int cvId = rs.getInt("cv_id");
                String cvType = rs.getString("cv_type");
                int total = rs.getInt("total_interviews");
                System.out.println("CV ID: " + cvId + " (" + cvType + ") -> " + total);
            }

        } catch (SQLException e) {
            System.out.println("Error (Interviews per CV): " + e.getMessage());
        }
    }

    // 3) Conversion rate per CV (interviews / applications)
    public void conversionRatePerCv() {
        String sql =
            "SELECT cv.cv_id, cv.cv_type, " +
            "       COUNT(DISTINCT ja.job_id) AS applications, " +
            "       COUNT(DISTINCT i.interview_id) AS interviews " +
            "FROM cv_versions cv " +
            "LEFT JOIN job_applications ja ON cv.cv_id = ja.cv_id " +
            "LEFT JOIN interviews i ON ja.job_id = i.job_id " +
            "GROUP BY cv.cv_id, cv.cv_type " +
            "ORDER BY applications DESC";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n=== Conversion Rate per CV ===");
            while (rs.next()) {
                int cvId = rs.getInt("cv_id");
                String cvType = rs.getString("cv_type");
                int apps = rs.getInt("applications");
                int interviews = rs.getInt("interviews");

                double rate = (apps == 0) ? 0.0 : (interviews * 100.0 / apps);

                System.out.printf("CV ID: %d (%s) -> %d apps, %d interviews, %.2f%%%n",
                        cvId, cvType, apps, interviews, rate);
            }

        } catch (SQLException e) {
            System.out.println("Error (Conversion rate per CV): " + e.getMessage());
        }
    }
}
