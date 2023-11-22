package homework;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> map = new TreeMap<>();

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> customerStringEntry = map.firstEntry();
        return getNewCustomer(customerStringEntry);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> customerStringEntry = map.higherEntry(customer);
        if (Objects.isNull(customerStringEntry)) {
            return null;
        } else {
            return getNewCustomer(customerStringEntry);
        }
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    private Map.Entry<Customer, String> getNewCustomer(Map.Entry<Customer, String> customerStringEntry) {
        Customer customer = customerStringEntry.getKey();
        Customer newCustomer = new Customer(customer.getId(), customer.getName(), customer.getScores());
        return Map.entry(newCustomer, customerStringEntry.getValue());
    }
}
