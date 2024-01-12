import java.util.ArrayList;
import java.util.List;


 public class CustomerSuccessBalancing {

    private final List<CustomerSuccess> customerSuccess;
    private final List<Customer> customers;
    private final List<Integer> awayCustomerSuccess;

    public CustomerSuccessBalancing(List<CustomerSuccess> customerSuccess, List<Customer> customers, List<Integer> awayCustomerSuccess) {
        this.customerSuccess = customerSuccess;
        this.customers = customers;
        this.awayCustomerSuccess = awayCustomerSuccess;
    }

    public int execute() {
        removeUnavailableCustomerSuccess();
        sortCustomerSuccess();
        List<CustomerSuccessWithCustomersQty> csWithCustomersQty = groupCustomersWithCustomerSuccess();
        sortByCustomersQtyReverse(csWithCustomersQty);
        List<CustomerSuccessWithCustomersQty> csWithMoreCustomers = getCustomerSuccessWithMoreCustomers(csWithCustomersQty);
        return getCustomerSuccessIdWithMoreCustomers(csWithMoreCustomers);
    }

    private void removeUnavailableCustomerSuccess() {
        customerSuccess.removeIf(cs -> awayCustomerSuccess.contains(cs.getId()));
    }

    private void sortCustomerSuccess() {
        customerSuccess.sort((cs1, cs2) -> cs1.getScore() - cs2.getScore());
    }

    private int groupAndCountCustomers(int score) {
        List<Customer> selected = new ArrayList<>();
        customers.removeIf(customer -> {
            if (customer.getScore() <= score) {
                selected.add(customer);
                return true;
            }
            return false;
        });
        return selected.size();
    }

    private List<CustomerSuccessWithCustomersQty> groupCustomersWithCustomerSuccess() {
        List<CustomerSuccessWithCustomersQty> groupedCustomerSuccess = new ArrayList<>();
        for (CustomerSuccess cs : customerSuccess) {
            int customersGrouped = groupAndCountCustomers(cs.getScore());
            groupedCustomerSuccess.add(new CustomerSuccessWithCustomersQty(cs.getId(), customersGrouped));
        }
        return groupedCustomerSuccess;
    }

    private void sortByCustomersQtyReverse(List<CustomerSuccessWithCustomersQty> csWithCustomersQty) {
        csWithCustomersQty.sort((cs1, cs2) -> cs2.getCustomersQty() - cs1.getCustomersQty());
    }

    private List<CustomerSuccessWithCustomersQty> getCustomerSuccessWithMoreCustomers(List<CustomerSuccessWithCustomersQty> csWithCustomersQty) {
        if (csWithCustomersQty.isEmpty()) {
            return new ArrayList<>();
        }
        if (csWithCustomersQty.size() == 1) {
            return List.of(csWithCustomersQty.get(0));
        }
        if (csWithCustomersQty.get(0).getCustomersQty() == csWithCustomersQty.get(1).getCustomersQty()) {
            return List.of(csWithCustomersQty.get(0), csWithCustomersQty.get(1));
        } else {
            return List.of(csWithCustomersQty.get(0));
        }
    }

    private int getCustomerSuccessIdWithMoreCustomers(List<CustomerSuccessWithCustomersQty> csWithCustomersQty) {
        if (csWithCustomersQty.size() == 1) {
            return csWithCustomersQty.get(0).getCsId();
        } else {
            return 0;
        }
    }

    private static class CustomerSuccess {
        private final int id;
        private final int score;

        public CustomerSuccess(final int id, int score) {
            this.id = id;
            this.score = score;
        }

        public int getId() {
            return id;
        }

        public int getScore() {
            return score;
        }
    }

    private static class Customer {
        private final int score;

        /**
         * @param score
         * @param newParam2
         */
        public Customer(int score, Object newParam2) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }
    }

    private static class CustomerSuccessWithCustomersQty {
        private final int csId;
        private final int customersQty;

        public CustomerSuccessWithCustomersQty(int csId, int customersQty) {
            this.csId = csId;
            this.customersQty = customersQty;
        }

        public int getCsId() {
            return csId;
        }

        public int getCustomersQty() {
            return customersQty;
        }
    }

     public static void main(String[] args) {

     }
 }


