package tracker.dao;

import java.time.LocalDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import tracker.db.Db;
import java.sql.Date;
import java.sql.ResultSet;

public class JobApplicationDao {
    public void addJob(int cv_id, int emp_id, String title, String location, LocalDate date_applied, String application_status) {
    String sql = "INSERT INTO job_applications(cv_id, emp_id, title, location, date_applied, application_status) VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection con = Db.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, cv_id);
        ps.setInt(2, emp_id);
        ps.setString(3, title);
        ps.setString(4, location);
        ps.setDate(5, Date.valueOf(date_applied));
        ps.setString(6, application_status);
            
        ps.executeUpdate();

        System.out.println("Job Application added successfully.");

    } catch (Exception e) {
        System.out.println("Error adding Job Application: " + e.getMessage());
   }
}

public void listJobApplications() {
    String sql =
        "SELECT ja.job_id, e.company, cv.cv_type, " +
        "       ja.title, ja.location, ja.date_applied, ja.application_status, " +
        "       ja.emp_id, ja.cv_id " +
        "FROM job_applications ja " +
        "JOIN employer e ON ja.emp_id = e.emp_id " +
        "JOIN cv_versions cv ON ja.cv_id = cv.cv_id " +
        "ORDER BY ja.job_id";

    try (Connection con = Db.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        System.out.println("\nApplications:");
        while (rs.next()) {
            int jobId = rs.getInt("job_id");
            String company = rs.getString("company");
            String cvType = rs.getString("cv_type");
            String title = rs.getString("title");
            String location = rs.getString("location");
            java.sql.Date dateApplied = rs.getDate("date_applied");
            String status = rs.getString("application_status");
            int empId = rs.getInt("emp_id");
            int cvId = rs.getInt("cv_id");

            System.out.println("[Job ID=" + jobId + "] " + company + " (CV: " + cvType + ")");
            System.out.println(" Title: " + title);
            System.out.println(" Location: " + location);
            System.out.println(" Applied: " + dateApplied + " | Status: " + status);
            System.out.println(" Employer ID: " + empId + " | CV ID: " + cvId);
            System.out.println("------------------------------------");
        }

    } catch (Exception e) {
        System.out.println("Error reading applications: " + e.getMessage());
    }
}

    public boolean updateJobStatus(int jobId, String newStatus) {
    String sql = "UPDATE job_applications SET application_status = ? WHERE job_id = ?";

    try (Connection con = Db.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, newStatus);
        ps.setInt(2, jobId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0;

    } catch (Exception e) {
        System.out.println("Error updating job status: " + e.getMessage());
        return false;
    }
}

}