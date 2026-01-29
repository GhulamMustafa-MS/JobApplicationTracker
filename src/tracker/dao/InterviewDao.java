package tracker.dao;

import tracker.db.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class InterviewDao {

    public void addInterview(int jobId, LocalDate dateSet, String outcome) {
        // If user passes empty/null outcome, use the DB default style value
        if (outcome == null || outcome.trim().isEmpty()) {
            outcome = "Pending";
        }

        String sql = "INSERT INTO interviews (job_id, date_set, outcome) VALUES (?, ?, ?)";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobId);
            ps.setDate(2, java.sql.Date.valueOf(dateSet));
            ps.setString(3, outcome);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Interview added successfully.");
            } else {
                System.out.println("No interview added.");
            }

        } catch (SQLException e) {
            System.out.println("Error adding interview: " + e.getMessage());
        }
    }

    public void listInterviews() {
        // Shows interview + related job + company for readable CLI output
        String sql =
            "SELECT i.interview_id, i.job_id, i.date_set, i.outcome, " +
            "       ja.title, ja.application_status, e.company " +
            "FROM interviews i " +
            "JOIN job_applications ja ON i.job_id = ja.job_id " +
            "JOIN employer e ON ja.emp_id = e.emp_id " +
            "ORDER BY i.date_set DESC, i.interview_id DESC";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n=== Interviews ===");

            boolean any = false;
            while (rs.next()) {
                any = true;

                int interviewId = rs.getInt("interview_id");
                int jobId = rs.getInt("job_id");
                java.sql.Date dateSet = rs.getDate("date_set");
                String outcome = rs.getString("outcome");

                String company = rs.getString("company");
                String title = rs.getString("title");
                String appStatus = rs.getString("application_status");

                System.out.println("[Interview ID=" + interviewId + "] " + company);
                System.out.println(" Job ID: " + jobId + " | Title: " + title);
                System.out.println(" Interview Date: " + dateSet + " | Outcome: " + outcome);
                System.out.println(" Application Status: " + appStatus);
                System.out.println("------------------------------------");
            }

            if (!any) {
                System.out.println("No interviews found.");
            }

        } catch (SQLException e) {
            System.out.println("Error listing interviews: " + e.getMessage());
        }
    }

    public void listInterviewsByJob(int jobId) {
        String sql =
            "SELECT i.interview_id, i.job_id, i.date_set, i.outcome, " +
            "       ja.title, e.company " +
            "FROM interviews i " +
            "JOIN job_applications ja ON i.job_id = ja.job_id " +
            "JOIN employer e ON ja.emp_id = e.emp_id " +
            "WHERE i.job_id = ? " +
            "ORDER BY i.date_set DESC, i.interview_id DESC";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobId);

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\n=== Interviews for Job ID " + jobId + " ===");

                boolean any = false;
                while (rs.next()) {
                    any = true;

                    int interviewId = rs.getInt("interview_id");
                    java.sql.Date dateSet = rs.getDate("date_set");
                    String outcome = rs.getString("outcome");

                    String company = rs.getString("company");
                    String title = rs.getString("title");

                    System.out.println("[Interview ID=" + interviewId + "] " + company + " - " + title);
                    System.out.println(" Interview Date: " + dateSet + " | Outcome: " + outcome);
                    System.out.println("------------------------------------");
                }

                if (!any) {
                    System.out.println("No interviews found for that job ID.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error listing interviews by job: " + e.getMessage());
        }
    }

    public boolean updateInterviewOutcome(int interviewId, String newOutcome) {
        String sql = "UPDATE interviews SET outcome = ? WHERE interview_id = ?";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newOutcome);
            ps.setInt(2, interviewId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error updating interview outcome: " + e.getMessage());
            return false;
        }
    }
}
