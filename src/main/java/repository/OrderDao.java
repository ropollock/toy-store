package repository;

import models.Order;

import java.util.List;

public interface OrderDao {
    /**
     * findAll
     * <p>
     * Get all orders.
     *
     * @return List<Order>
     */
    List<Order> findAll();

    /**
     * findByid
     * <p>
     * Get order by order id.
     *
     * @param id Integer order id
     * @return Order
     */
    Order findById(Integer id);

    /**
     * insertOrder
     * <p>
     * Adds an order and returns the id of the order.
     *
     * @param order Order to insert
     * @return Order
     */
    Integer insertOrder(Order order);
}
