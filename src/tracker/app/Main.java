package tracker.app;

import tracker.dao.InterviewDao;
import tracker.dao.EmployerDao;
import tracker.dao.CvVersionDao;
import java.time.LocalDate;
import tracker.db.Db;
import java.sql.Connection;
import java.sql.Date;
import tracker.dao.JobApplicationDao;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CvVersionDao cvDao = new CvVersionDao();
        EmployerDao empDao = new EmployerDao();
        JobApplicationDao JobDao = new JobApplicationDao();
        InterviewDao InterviewDao = new InterviewDao();


        while (true) {
            System.out.println("\n--- Job Tracker ---");
            System.out.println("1) Test DB connection");
            System.out.println("2) List CV versions");
            System.out.println("3) List CV count");
            System.out.println("4) Add CV version");
            System.out.println("5) List Employers");
            System.out.println("6) Add Employers");
            System.out.println("7) List Job Applications");
            System.out.println("8) Add Job Application");
            System.out.println("9) Update Job Status");
            System.out.println("10) List Interviews");
            System.out.println("11) Add Interview");
            System.out.println("12) List Interview by Job ID");
            System.out.println("13) Update Interview Status");
            System.out.println("0) Exit");
            System.out.print("Choose: ");

            String choice = sc.nextLine();

            if (choice.equals("1")) {
                try (Connection con = Db.getConnection()) {
                    System.out.println("Connected to DB successfully!");
                } catch (Exception e) {
                    System.out.println("Connection failed: " + e.getMessage());
                }
            }
            else if (choice.equals("0")) {
                System.out.println("Bye!");
                break;
            } 
            else if (choice.equals("2")) {
            cvDao.printAllCvVersions();
            } 
            else if (choice.equals("3")) {
              cvDao.printCvCount();  
            } 
            else if (choice.equals("4")) {
            System.out.print("Enter CV type: ");
            String cv_type = sc.nextLine();

            System.out.print("Enter date created (YYYY-MM-DD): ");
            LocalDate date_created = LocalDate.parse(sc.nextLine());
            cvDao.addCvVersion(cv_type, date_created);
            }
            else if (choice.equals("5")) {
                empDao.listEmployer();
                
            }
            else if (choice.equals("6")) {
                
            System.out.print("Enter phone number (or leave blank): ");
            String phone_no = sc.nextLine();
            
            System.out.print("Enter LinkedIn URL (or leave blank): ");
            String linkedin = sc.nextLine();
            
            System.out.print("Enter website (or leave blank): ");
            String website = sc.nextLine();
            
            System.out.print("Enter company name: ");
            String company = sc.nextLine();

            System.out.print("Enter email (or leave blank): ");
            String email = sc.nextLine();
            
            empDao.addEmployer(phone_no, linkedin, website, company, email);
            }
            else if (choice.equals("7")){
                JobDao.listJobApplications();
            }
            else if(choice.equals("8")) {
            try {
                System.out.print("Enter CV ID: ");
                int cvId = Integer.parseInt(sc.nextLine().trim());

                System.out.print("Enter Employer ID: ");
                int empId = Integer.parseInt(sc.nextLine().trim());

                System.out.print("Enter Job Title: ");
                String title = sc.nextLine().trim();

                System.out.print("Enter Location: ");
                String location = sc.nextLine().trim();

                System.out.print("Enter Date Applied (YYYY-MM-DD): ");
                LocalDate dateApplied = LocalDate.parse(sc.nextLine().trim());

                System.out.print("Enter Application Status (or press Enter for default 'Applied'): ");
                String status = sc.nextLine().trim();
                if (status.isEmpty()) {
                    status = "Applied";
                }

                JobDao.addJob(cvId, empId, title, location, dateApplied, status);
                System.out.println("Job application added successfully.");

            } catch (Exception e) {
                System.out.println("Error adding job application: " + e.getMessage());
            }                    
            }
            else if(choice.equals("9")){
            try {
                System.out.print("Enter Job ID to update: ");
                int jobId = Integer.parseInt(sc.nextLine().trim());

                System.out.print("Enter new status: ");
                String status = sc.nextLine().trim();

                boolean updated = JobDao.updateJobStatus(jobId, status);

                if (updated) {
                    System.out.println("Application status updated successfully.");
                } else {
                    System.out.println("No job application found with that ID.");
                }

            } catch (Exception e) {
                System.out.println("Invalid input. Please enter correct values.");
            }
            }
            else if (choice.equals("10")) {
                InterviewDao.listInterviews();
            }
            else if(choice.equals("11")) {
                try {
                    System.out.print("Enter Job ID for this interview: ");
                    int jobId = Integer.parseInt(sc.nextLine().trim());

                    System.out.print("Enter Interview Date (YYYY-MM-DD): ");
                    LocalDate dateSet = LocalDate.parse(sc.nextLine().trim());

                    System.out.print("Enter Outcome (press Enter for Pending): ");
                    String outcome = sc.nextLine().trim();

                    InterviewDao.addInterview(jobId, dateSet, outcome);

                } catch (Exception e) {
                    System.out.println("Invalid input. Please try again.");
                }
            }
            else if(choice.equals("12")) {
                try {
                System.out.print("Enter Job ID: ");
                int jobId = Integer.parseInt(sc.nextLine().trim());

                InterviewDao.listInterviewsByJob(jobId);

            } catch (Exception e) {
                System.out.println("Invalid Job ID.");
            }
            }
            else if(choice.equals("13")){
                try {
                    System.out.print("Enter Interview ID: ");
                    int interviewId = Integer.parseInt(sc.nextLine().trim());

                    System.out.print("Enter New Outcome: ");
                    String outcome = sc.nextLine().trim();

                    boolean updated = InterviewDao.updateInterviewOutcome(interviewId, outcome);

                    if (updated) {
                        System.out.println("Interview outcome updated.");
                    } else {
                        System.out.println("Interview not found.");
                    }

                } catch (Exception e) {
                    System.out.println("Invalid input. Please try again.");
                }
            }
            else {
                System.out.println("Invalid option.");
            }
        }
    }
}