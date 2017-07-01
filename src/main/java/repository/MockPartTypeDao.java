package repository;

import models.PartType;

import java.util.List;
import java.util.stream.Collectors;

public class MockPartTypeDao implements PartTypeDao {
    private final Datastore datastore;

    public MockPartTypeDao() {
        this.datastore = DatastoreFactory.getDatastore(DatastoreFactory.DATASTORE_TYPES.MOCK);
    }

    /**
     * findById
     *
     * Gets a part type by id.
     *
     * @param id Integer part type
     * @return PartType
     */
    @Override
    public PartType findById(final Integer id) {
        return this.datastore.getPartTypes().stream()
                .filter((partType) -> id.equals(partType.getId()))
                .findFirst()
                .orElse(null);
    }

    /**
     * findAll
     *
     * Gets all part types.
     *
     * @return List<PartType>
     */
    @Override
    public List<PartType> findAll() {
        return this.datastore.getPartTypes();
    }

    /**
     * findByName
     *
     * Get all part types by name.
     *
     * @param name String name
     * @return List<PartType>
     */
    @Override
    public List<PartType> findByName(final String name) {
        return this.datastore.getPartTypes().stream()
                .filter((partType) -> name.equals(partType.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<PartType> findByCategoryId(final Integer categoryId) {
        return this.datastore.getPartTypes().stream()
                .filter((partType) -> categoryId.equals(partType.getCategoryId()))
                .collect(Collectors.toList());
    }

    /**
     * findByCategory
     *
     * Gets all part types by category name.
     *
     * @param category String category name
     * @return List<PartType>
     */
    @Override
    public List<PartType> findByCategory(final String category) {
        return this.findByCategoryId(
                new MockPartTypeCategoryDao().findByCategory(category).getId());
    }

    /**
     * updateSurcharge
     * <p>
     * Updates surcharge of a part type.
     *
     * @param id        Part Type id
     * @param surcharge Double price
     */
    @Override
    public void updateSurcharge(Integer id, Double surcharge) {
        this.datastore.getPartTypes().stream()
                .filter((partType) -> id.equals(partType.getId()))
                .findFirst()
                .get().setSurcharge(surcharge);
    }
}
