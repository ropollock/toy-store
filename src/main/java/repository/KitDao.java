package repository;

import models.Kit;

import java.util.List;

public interface KitDao {
    /**
     * insertKit
     *
     * Adds a kit and returns an id.
     *
     * @param kit Kit
     * @return Integer id inserted
     */
    Integer insertKit(Kit kit);

    /**
     * findAll
     *
     * Get all kits.
     *
     * @return List<Kit>
     */
    List<Kit> findAll();

    /**
     * findByOrderId
     *
     * Gets all kits by order id.
     *
     * @param orderId Integer order id
     * @return List<Kit>
     */
    List<Kit> findByOrderId(Integer orderId);

    /**
     * findById
     *
     * Get kit by id.
     *
     * @param id Integer kit id
     * @return Kit
     */
    Kit findById(Integer id);

    /**
     * findByPartHash
     *
     * Get all kits by part hash.
     *
     * @param partHash String sha1
     * @return List<Kit>
     */
    List<Kit> findByPartHash(String partHash);
}
