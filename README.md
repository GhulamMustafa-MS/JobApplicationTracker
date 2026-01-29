Java CLI Job Application Tracker
Overview

This project is a console-based Java application that tracks job applications, CV versions, employers, and interviews using JDBC and MySQL. It is designed as a backend-focused portfolio project to demonstrate database design, clean code structure, and practical Java development without a graphical interface.

The system allows users to record where they have applied, which CV version was used, interview progress, and application outcomes. It also supports analytics to evaluate which CV versions are most effective.

Features

CV Version Management

Add different versions of a CV

Store CV type and creation date

List all CV versions

Count total CV versions

Employer Management

Store employer contact information

Includes company name, email, phone number, LinkedIn, and website

View a list of all employers

Job Application Tracking

Record job applications linked to a specific employer and CV version

Store job title, location, date applied, and application status

Prevent duplicate applications using a database uniqueness constraint

List all job applications with employer and CV details

Update the status of an application (Applied, Interview, Rejected, Offer, etc.)

Interview Tracking

Schedule interviews linked to a job application

Store interview date and outcome

View all interviews with related job and employer details

Update interview outcomes

Interviews are automatically deleted if the related job application is removed (ON DELETE CASCADE)

Analytics

Number of applications per CV version

Number of interviews per CV version

Conversion rate from applications to interviews per CV version

Technology Stack

Java (CLI application)

JDBC for database connectivity

MySQL 8 relational database

NetBeans with Ant build system

Scanner class for command-line input

Database Design

The application uses a relational database with foreign key constraints to maintain data integrity.

Tables:

cv_versions

cv_id (Primary Key)

cv_type

date_created

employer

emp_id (Primary Key)

company

email

phone_no

linkedin

website

job_applications

job_id (Primary Key)

cv_id (Foreign Key → cv_versions)

emp_id (Foreign Key → employer)

title

location

date_applied

application_status

Unique constraint to prevent duplicate applications

interviews

interview_id (Primary Key)

job_id (Foreign Key → job_applications)

date_set

outcome

Linked with ON DELETE CASCADE

The system stores only metadata and tracking information. CV files themselves are not stored.

Architecture

The project follows a simple layered structure:

Main (CLI Layer)
Handles user input, menu navigation, and output display.

DAO Layer
Each DAO (Data Access Object) is responsible for interacting with one database table or domain.

CvVersionDao

EmployerDao

JobApplicationDao

InterviewsDao

Database Layer
Db.java manages the database connection.

This separation ensures that user interface logic and database logic remain independent.

Concepts Demonstrated

JDBC workflow (Connection, PreparedStatement, ResultSet)

DAO design pattern

SQL joins and relational queries

Foreign key relationships and constraints

Prepared statements and SQL injection prevention

Converting LocalDate to java.sql.Date

Command-line menu systems

Input validation and error handling

Separation of concerns in application design

How to Run

Install MySQL and create a database named tracker_db

Create the required tables using the provided schema

Add the MySQL Connector/J JAR file to the project libraries

Update database credentials in Db.java

Run Main.java from your IDE or command line

Project Purpose

This project was built as a portfolio piece to demonstrate practical backend development skills using Java and SQL. It simulates a real-world tracking system while focusing on clean structure, maintainable code, and solid database interaction practices.
