package repository;

import models.PartTypeCategory;

import java.util.List;

public interface PartTypeCategoryDao {
    /**
     * findAll
     *
     * Gets all part type categories.
     *
     * @return List<PartTypeCategory>
     */
    List<PartTypeCategory> findAll();
    /**
     * findById
     *
     * Gets a part type category by id.
     *
     * @param id Integer id
     * @return PartTypeCategory
     */
    PartTypeCategory findById(Integer id);

    /**
     * findByCategory
     *
     * Gets a part type category by category.
     *
     * @param category String category
     * @return PartTypeCategory
     */
    PartTypeCategory findByCategory(String category);
}
