# Hotel Booking Management System

## About the Project
The Hotel Booking Management System is a Java-based console application that allows users to view hotel rooms, check room availability, register customers, book rooms, view bookings, and cancel reservations.

## Features
- View all hotel rooms
- Check room availability for selected dates
- Register new customers
- Book hotel rooms
- View customer bookings
- Cancel bookings
- Store data in a MySQL database

## Technologies Used
- Java 21
- MySQL
- JDBC
- Eclipse IDE
- Git and GitHub

## Database Setup
1. Install MySQL Server.
2. Open MySQL Workbench.
3. Open and execute `database.sql`.
4. Configure the database username and password using the `DB_USERNAME` and `DB_PASSWORD` environment variables.

## How to Run
1. Clone or download this repository.
2. Import the project into Eclipse.
3. Add MySQL Connector/J to the project's classpath.
4. Configure the database environment variables.
5. Run `HotelBookingSystem.java` as a Java Application.

## Project Structure
```text
HotelBookingSystem/
├── src/
│   └── hotelbooking/
│       ├── DBConnection.java
│       ├── RoomService.java
│       ├── CustomerService.java
│       ├── BookingService.java
│       ├── BookingManagementService.java
│       └── HotelBookingSystem.java
├── database.sql
└── README.md
```

## Author
Malgari Gayatri
