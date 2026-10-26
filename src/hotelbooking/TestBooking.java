
package hotelbooking;

import java.time.LocalDate;

public class TestBooking {

    public static void main(String[] args) {

        BookingService service = new BookingService();

        // Customer ID 1 is Rahul Kumar.
        // Room ID 1 is Room 101.
        service.bookRoom(
                1,
                1,
                LocalDate.of(2026, 11, 1),
                LocalDate.of(2026, 11, 3)
        );
    }
}
