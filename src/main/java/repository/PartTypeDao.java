package repository;

import models.PartType;

import java.util.List;

public interface PartTypeDao {
    /**
     * findAll
     *
     * Gets all part types.
     *
     * @return List<PartType>
     */
    List<PartType> findAll();

    /**
     * findById
     *
     * Gets a part type by id.
     *
     * @param id Integer part type
     * @return PartType
     */
    PartType findById(Integer id);

    /**
     * findByName
     *
     * Gets a list of part type by name.
     *
     * @param name String name
     * @return List<PartType>
     */
    List<PartType> findByName(String name);

    /**
     * findByCategoryId
     *
     * Gets all part types by category id.
     *
     * @param categoryId Integer category id
     * @return List<PartType>
     */
    List<PartType> findByCategoryId(Integer categoryId);

    /**
     * findByCategoryName
     *
     * Gets all part types by category name.
     *
     * @param category String category name
     * @return List<PartType>
     */
    List<PartType> findByCategory(String category);

    /**
     * updateSurcharge
     *
     * Updates surcharge of a part type.
     *
     * @param id Part Type id
     * @param surcharge Double price
     */
    void updateSurcharge(Integer id, Double surcharge);
}
