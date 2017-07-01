package repository;

import models.Customer;

public interface CustomerDao {
    /**
     * findById
     *
     * Gets a customer by id.
     *
     * @param id Integer id
     * @return
     */
    Customer findById(Integer id);
}
