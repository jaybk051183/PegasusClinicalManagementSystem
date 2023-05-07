# PEGASUS Clinical Management System - REST API application -  Layered Architecture, CRUD  operations and Tests
# About
The application was developed with Spring Boot and uses a RESTful API to manage appointments, invoices, medical records and users. The application uses a relational database for data storage through CRUD operations and is secured using JWT token authentication.

Link to GitHib repository:  https://github.com/jaybk051183/PegasusClinicalManagementSystem

### Tech Stack ⚙
1. Language – Java open JDK version 17
2. Framework – Springboot version 3.0.5.
3. IDE - IntelliJ
4. Database – PostGreSQL
2. Unittests - Junit
3. Mocking - Mockito
4. Projectmanagement - Maven
5. Webservice -   Spring Web / Spring Data JPA / PostgreSQL Driver / Spring Security
6. Object Relational Mapping - Hibernate
7. Architechture - Multi Layered  System (Controller, Service & Repository)
8. Authentication handler – JWT auth process

### Entity Classes

* Authentication
* User
* Appointments
* Doctors
* Patients
* Invoices
* MedicalRecords

### Features

* User Management
* Authentication
* Appointment Management
* Medical record Management
* Patient information storage
* Employee information storage
* Invoice Management
* Report Management
* Statistics
* Upload/Download documents

## Datasource settings in application.properties

#### Important: Set to your own local settings!!

Current settings:
* postgresql database on //localhost:5432
* database: springboot
* user/owner: postgres
* password: password0583

#### Seeded users

* admin - password
* user - password

# Endpoints

### API Root Endpoint
* http://localhost:8088/

### API Module Endpoints

#### appointments
* GET /api/v1/appointments
* GET /api/v1/appointments/{id}
* POST /api/v1/appointments
* DELETE /api/v1/appointments/{id}
* PUT /api/v1/appointments/{id}
* GET /api/v1/appointments/search

#### doctors
* GET /api/v1/doctors
* GET /api/v1/doctors/{id}
* POST /api/v1/doctors
* DELETE /api/v1/doctors/{id}
* PUT/api/v1/doctors/{id}
* PUT/api/v1/doctors/{id}/availability

#### patients
* GET /api/v1/patients
* GET /api/v1/patients/{id}
* POST /api/v1/patients
* PUT /api/v1/patients/{id}
* DELETE /api/v1/patients/{id}

#### invoices
* GET /api/v1/invoices
* DELETE /api/v1/invoices/{id}
* POST /api/v1/invoices
* PUT /api/v1/invoices/{id}
* DELETE /api/v1/invoices/{id}
* PUT /api/v1/invoices/{id}/mark-paid
* GET /api/v1/invoices/{id}/download

#### medical-records
* GET /api/v1/medical-records
* GET/api/v1/medical-records/{patientId}
* POST /api/v1/medical-records/{patientId}
* PUT /api/v1/medical-records/{patientId}
* DELETE /api/v1/medical-records/{patientId}
* POST /api/v1/{id}/file

#### reports
* GET /api/reports/appointments
* GET/api/reports/invoices

#### statistics
* GET /api/statistics/appointments
* GET/api/statistics/cancellations
* GET/api/statistics/hours-per-doctor

#### upload/download files
* POST /upload
* GET/download/{fileName}

#### authority
* GET /authenticated
* POST /authenticate

#### users
* GET /users
* POST /users
* DELETE /users/{username}
* GET /users/{username}
* PUT /users/{username}
* GET /users/{username}/authorities
* POST /users/{username}/authorities
* DELETE /users/{username}/authorities/{authority}

#### A Postman export has been included in the documentation directory.

#### Contributor

This project is developed by Kailash Bhaggoe (Fullstack Developer) during the backend studyprogramme of the fullstack developemt bootcamp at NOVI University of Applied Sciences in Utrecht, the Netherlands.
For any feedback, report, suggestions, you can contact me on kailashbaggoe@outlook.com.

#### Thank You
