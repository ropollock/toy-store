package repository;

import models.KitType;

import java.util.List;

public interface KitTypeDao {
    /**
     * findAll
     *
     * Get all kit types.
     *
     * @return List<KitType>
     */
    List<KitType> findAll();

    /**
     * findById
     *
     * Get kit type by id.
     *
     * @param id Integer kit type id
     * @return KitType
     */
    KitType findById(Integer id);

    /**
     * updateBasePrice
     *
     * Updates base price of a kit type.
     *
     * @param id Kit Type id
     * @param basePrice Double price
     */
    void updateBasePrice(Integer id, Double basePrice);
}
