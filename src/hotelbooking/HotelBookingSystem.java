
package hotelbooking;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class HotelBookingSystem {

    private static final Scanner sc = new Scanner(System.in);

    private static final RoomService roomService = new RoomService();
    private static final CustomerService customerService = new CustomerService();
    private static final BookingService bookingService = new BookingService();
    private static final BookingManagementService managementService =
            new BookingManagementService();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== HOTEL BOOKING SYSTEM =====");
            System.out.println("1. View All Rooms");
            System.out.println("2. Check Available Rooms");
            System.out.println("3. Register Customer");
            System.out.println("4. Book a Room");
            System.out.println("5. View My Bookings");
            System.out.println("6. Cancel Booking");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                sc.nextLine();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {

                    case 1:
                        roomService.viewRooms();
                        break;

                    case 2:
                        System.out.print("Enter check-in date (YYYY-MM-DD): ");
                        LocalDate checkIn =
                                LocalDate.parse(sc.nextLine().trim());

                        System.out.print("Enter check-out date (YYYY-MM-DD): ");
                        LocalDate checkOut =
                                LocalDate.parse(sc.nextLine().trim());

                        roomService.viewAvailableRooms(checkIn, checkOut);
                        break;

                    case 3:
                        registerCustomer();
                        break;

                    case 4:
                        bookRoom();
                        break;

                    case 5:
                        System.out.print("Enter customer ID: ");
                        int viewCustomerId =
                                Integer.parseInt(sc.nextLine().trim());

                        managementService.viewBookings(viewCustomerId);
                        break;

                    case 6:
                        System.out.print("Enter customer ID: ");
                        int cancelCustomerId =
                                Integer.parseInt(sc.nextLine().trim());

                        System.out.print("Enter booking ID to cancel: ");
                        int cancelBookingId =
                                Integer.parseInt(sc.nextLine().trim());

                        managementService.cancelBooking(
                                cancelCustomerId, cancelBookingId);
                        break;

                    case 7:
                        System.out.println("Thank you for using our system!");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid numeric ID.");
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Use YYYY-MM-DD.");
            }
        }
    }

    private static void registerCustomer() {
        System.out.print("Enter customer name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter phone number: ");
        String phone = sc.nextLine().trim();

        System.out.print("Enter email: ");
        String email = sc.nextLine().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            System.out.println("Name and phone are required.");
            return;
        }

        customerService.registerCustomer(name, phone, email);
    }

    private static void bookRoom() {
        System.out.print("Enter customer ID: ");
        int customerId = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Enter room ID: ");
        int roomId = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Enter check-in date (YYYY-MM-DD): ");
        LocalDate checkIn = LocalDate.parse(sc.nextLine().trim());

        System.out.print("Enter check-out date (YYYY-MM-DD): ");
        LocalDate checkOut = LocalDate.parse(sc.nextLine().trim());

        bookingService.bookRoom(
                customerId, roomId, checkIn, checkOut);
    }
}
