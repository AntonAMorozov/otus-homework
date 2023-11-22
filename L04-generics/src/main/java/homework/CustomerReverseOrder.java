package homework;

import java.util.LinkedHashSet;

public class CustomerReverseOrder {

    private final LinkedHashSet<Customer> set = new LinkedHashSet<>();

    public void add(Customer customer) {
        set.add(customer);
    }

    public Customer take() {
        return set.removeLast();
    }
}
