CREATE DATABASE IF NOT EXISTS tracker_db;
use tracker_db;

-- Creating the tables for the whole database:
CREATE TABLE cv_versions
(cv_id INT AUTO_INCREMENT PRIMARY KEY,
cv_type VARCHAR(50) NOT NULL,
date_created DATE NOT NULL);

CREATE TABLE employer
(
emp_id INT AUTO_INCREMENT PRIMARY KEY,
phone_no VARCHAR(16),
linkedin VARCHAR(150),
website VARCHAR(150),
email VARCHAR(150),
company VARCHAR(150) NOT NULL
);

CREATE TABLE job_applications
(
job_id INT AUTO_INCREMENT PRIMARY KEY,
cv_id INT NOT NULL,
emp_id INT NOT NULL,
title VARCHAR (150) NOT NULL,
location VARCHAR(100),
date_applied DATE NOT NULL,
application_status varchar(30) NOT NULL DEFAULT 'Applied',
CONSTRAINT fk_job_cv FOREIGN KEY (cv_id) REFERENCES cv_versions(cv_id),
CONSTRAINT fk_job_emp FOREIGN KEY (emp_id) REFERENCES employer(emp_id),
CONSTRAINT uq_job UNIQUE(emp_id, title, date_applied)
);

CREATE TABLE interviews
(
interview_id INT AUTO_INCREMENT PRIMARY KEY,
job_id INT NOT NULL,
date_set DATE NOT NULL,
outcome VARCHAR (20) NOT NULL DEFAULT 'Pending',
CONSTRAINT fk_int_job FOREIGN KEY (job_id) REFERENCES job_applications(job_id) ON DELETE CASCADE
);

SHOW TABLES;