package repository;

import models.Customer;

public class MockCustomerDao implements CustomerDao {
    private final Datastore datastore;

    public MockCustomerDao() {
        this.datastore = DatastoreFactory.getDatastore(DatastoreFactory.DATASTORE_TYPES.MOCK);
    }

    /**
     * findById
     *
     * Gets a
     * @param id String id
     * @return
     */
    @Override
    public Customer findById(final Integer id) {
        return this.datastore.getCustomers().stream()
                .filter((customer) -> id.equals(customer.getId()))
                .findFirst()
                .orElse(null);
    }
}
