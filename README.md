This project focuses on backend logic, database design, and clean architecture, without a GUI, making it ideal for demonstrating core Java and SQL skills.

Features
CV Version Management

Add and list different versions of your CV

Track when each CV version was created

Count total CV versions stored

Employer Management

Store employer contact information

Includes company name, email, phone, LinkedIn, and website

List all saved employers

Job Application Tracking

Record job applications linked to:

A specific employer

A specific CV version

Track:

Job title

Location

Date applied

Current application status (Applied, Interview, Rejected, Offer, etc.)

View all job applications with employer and CV details

Update the status of any application

Interview Tracking (Planned / In Progress)

Schedule interviews linked to a job application

Record interview outcomes

Automatically delete interviews if the related job application is removed

Analytics (Planned)

Applications per CV version

Interviews per CV version

Conversion rate from application → interview

Tech Stack
Technology	Purpose
Java	Core application logic (CLI-based)
JDBC	Database connectivity
MySQL 8	Relational database
NetBeans (Ant)	Development environment
Scanner (CLI)	User input handling
Database Design

The database enforces proper relational integrity using foreign keys.

Tables:

cv_versions – Stores CV variations

employer – Stores company contact details

job_applications – Links CVs and employers to job applications

interviews – Tracks interview stages and outcomes

The system only stores metadata, not CV files.

Concepts Demonstrated

This project showcases understanding of:

JDBC workflow: Connection → PreparedStatement → ResultSet

DAO (Data Access Object) design pattern

SQL JOIN operations

Foreign key relationships

Prepared statements and SQL injection prevention

Converting LocalDate ↔ java.sql.Date

CLI menu architecture and input validation

Separation of concerns (UI vs data layer)

How to Run

Set up a MySQL database named tracker_db

Create the required tables (see schema in project files)

Add MySQL Connector/J JAR to the project libraries

Update database credentials in Db.java

Run Main.java

🎯 Project Purpose

This project was built as a portfolio piece to demonstrate practical backend development skills using Java and SQL. It simulates a real-world tracking system while emphasizing clean structure and maintainable code.
