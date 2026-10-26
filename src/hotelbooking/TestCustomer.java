
package hotelbooking;

public class TestCustomer {

    public static void main(String[] args) {
        CustomerService service = new CustomerService();

        service.registerCustomer(
            "Rahul Kumar",
            "9876543210",
            "rahul@example.com"
        );
    }
}
