# Java CLI Job Application Tracker
A Java console application for tracking job applications, CV versions, and interview performance using JDBC and MySQL.

## Overview
This is a console-based Java application that tracks job applications, CV versions, employers, and interviews using JDBC and MySQL. It is designed as a backend-focused portfolio project to demonstrate database design, clean code structure, and practical Java development without a graphical interface.

The system allows users to record where they have applied, which CV version was used, interview progress, and application outcomes. It also supports analytics to evaluate which CV versions are most effective.

## Features

### CV Version Management
- Add different versions of a CV
- Store CV type and creation date
- List all CV versions
- Count total CV versions

### Employer Management
- Store employer contact information
- Includes company name, email, phone number, LinkedIn, and website
- View a list of all employers

### Job Application Tracking
- Record job applications linked to a specific employer and CV version
- Store job title, location, date applied, and application status
- Prevent duplicate applications using a database uniqueness constraint
- List all job applications with employer and CV details
- Update the status of an application

### Interview Tracking
- Schedule interviews linked to a job application
- Store interview date and outcome
- View all interviews with related job and employer details
- Update interview outcomes
- Interviews are automatically deleted if the related job application is removed

### Analytics
- Number of applications per CV version
- Number of interviews per CV version
- Conversion rate from applications to interviews per CV version

## Technology Stack
- Java (CLI application)
- JDBC for database connectivity
- MySQL 8 relational database
- NetBeans with Ant build system
- Scanner for command-line input

## Database Design

### cv_versions
- cv_id (Primary Key)
- cv_type
- date_created

### employer
- emp_id (Primary Key)
- company
- email
- phone_no
- linkedin
- website

### job_applications
- job_id (Primary Key)
- cv_id (Foreign Key → cv_versions)
- emp_id (Foreign Key → employer)
- title
- location
- date_applied
- application_status
- Unique constraint prevents duplicate applications

### interviews
- interview_id (Primary Key)
- job_id (Foreign Key → job_applications)
- date_set
- outcome

The system stores only metadata and tracking information. CV files themselves are not stored.

## Architecture

### Main (CLI Layer)
Handles user input, menu navigation, and output display.

### DAO Layer
- CvVersionDao
- EmployerDao
- JobApplicationDao
- InterviewsDao

### Database Layer
Db.java manages the database connection.

This separation ensures that user interface logic and database logic remain independent.

## Concepts Demonstrated
- JDBC workflow (Connection, PreparedStatement, ResultSet)
- DAO design pattern
- SQL joins and relational queries
- Foreign key relationships and constraints
- Prepared statements and SQL injection prevention
- Converting LocalDate to java.sql.Date
- Command-line menu systems
- Input validation and error handling
- Separation of concerns in application design

## How to Run
1. Install MySQL and create a database named `tracker_db`
2. Create the required tables using the provided schema
3. Add the MySQL Connector/J JAR file to the project libraries
4. Update database credentials in `Db.java`
5. Run `Main.java`

## Project Purpose
This project was built as a portfolio piece to demonstrate practical backend development skills using Java and SQL. It simulates a real-world tracking system while focusing on clean structure, maintainable code, and solid database interaction practices.
