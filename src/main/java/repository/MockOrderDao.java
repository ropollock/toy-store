package repository;

import models.Order;

import java.util.List;

public class MockOrderDao implements OrderDao {
    private final Datastore datastore;

    public MockOrderDao() {
        this.datastore = DatastoreFactory.getDatastore(DatastoreFactory.DATASTORE_TYPES.MOCK);
    }

    /**
     * findAll
     * <p>
     * Get all orders.
     *
     * @return List<Order>
     */
    @Override
    public List<Order> findAll() {
        return this.datastore.getOrders();
    }

    /**
     * findById
     * <p>
     * Get order by order id.
     * Joins kits by order id
     *
     * @param id Integer order id
     * @return Order
     */
    @Override
    public Order findById(Integer id) {
        return this.datastore.getOrders().stream()
                .filter((order) -> id.equals(order.getId()))
                .findFirst()
                .orElse(null);
    }

    /**
     * insertOrder
     * <p>
     * Adds an order and returns the id of the order.
     *
     * @param order Order to insert
     * @return Order
     */
    @Override
    public Integer insertOrder(Order order) throws NullPointerException {
        // Check for a valid order
        assertValidOrder(order);
        // Get the next available id for an order
        order.setId(this.datastore.nextId(this.datastore.getOrders()));
        // Persist the order
        this.datastore.getOrders().add(order);
        return order.getId();
    }

    private void assertValidOrder(Order order) throws NullPointerException {
        // Check for a customer id
        if(order.getCustomerId() == null) {
            throw new NullPointerException("Cannot create order without a customer id.");
        }

        // Check for a total price
        if(order.getTotalPrice() == null) {
            throw new NullPointerException("Cannot create order without a total price.");
        }

        // Check for a shipping address
        if(order.getShippingAddress() == null) {
            throw new NullPointerException("Cannot create order without a shipping address.");
        }
    }
}
