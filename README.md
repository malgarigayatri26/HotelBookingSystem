
# Hotel Booking Management System

## About the Project
A console-based hotel booking application developed using Java,
MySQL, and JDBC. It allows users to view rooms, register customers,
check room availability, book rooms, view bookings, and cancel reservations.

## Technologies Used
- Java 21
- MySQL
- JDBC
- Eclipse IDE
- MySQL Connector/J

## Features
1. View all hotel rooms
2. Check room availability for selected dates
3. Register customers
4. Book hotel rooms
5. Calculate booking price based on the number of nights
6. View customer bookings
7. Cancel confirmed bookings
8. Store booking information in MySQL

## Database Tables
- `rooms` - Stores room numbers, types, prices, and capacities
- `customers` - Stores customer details
- `bookings` - Stores reservation dates, prices, and statuses

## Database Setup
1. Install MySQL Server and MySQL Workbench.
2. Create a database named `hotel_booking`.
3. Create the `rooms`, `customers`, and `bookings` tables.
4. Insert sample room records into the `rooms` table.
5. Configure the database credentials in `DBConnection.java`.

## JDBC Configuration
Update the connection URL, username, and password in `DBConnection.java`
to match your local MySQL setup.

Add MySQL Connector/J to the project's Java Build Path.

## How to Run
1. Open the project in Eclipse.
2. Ensure MySQL Server is running.
3. Verify the JDBC driver and database connection settings.
4. Run `HotelBookingSystem.java` as a Java Application.
5. Select an option from the console menu.

## Future Enhancements
- Graphical user interface
- Customer login and authentication
- Online payment integration
- Booking receipts
- Admin dashboard
