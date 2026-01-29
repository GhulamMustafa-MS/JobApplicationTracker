package tracker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import tracker.db.Db;

public class EmployerDao {
        public void addEmployer(String phone_no, String linkedin, String website, String company, String email) {
        String sql = "INSERT INTO employer(phone_no, linkedin, website, company, email) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, phone_no);
            ps.setString(2, linkedin);
            ps.setString(3, website);
            ps.setString(4, company);
            ps.setString(5, email);
            
            ps.executeUpdate();

            System.out.println("Employer added successfully.");

        } catch (Exception e) {
            System.out.println("Error adding Employer information: " + e.getMessage());
        }
    }    
    
    public void listEmployer() {
        String sql = "Select emp_id, phone_no, linkedin, website, company, email From employer ORDER BY emp_id";

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nEmployers:");
            while (rs.next()) {
                int id = rs.getInt("emp_id");
                String phone = rs.getString("phone_no");
                String linkedin = rs.getString("linkedin");
                String website = rs.getString("website");
                String company = rs.getString("company");
                String email = rs.getString("email");
                System.out.println("[ID="+id+ "] " + company + " information:\nPhone_no is: " + phone + "\n linkedin is "+linkedin+"\n ");
            }

        } catch (Exception e) {
            System.out.println("Error reading CV versions: " + e.getMessage());
        }
    }
}
