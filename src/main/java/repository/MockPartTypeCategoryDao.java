package repository;

import models.PartTypeCategory;

import java.util.List;

public class MockPartTypeCategoryDao implements PartTypeCategoryDao {
    private final Datastore datastore;

    public MockPartTypeCategoryDao() {
        this.datastore = DatastoreFactory.getDatastore(DatastoreFactory.DATASTORE_TYPES.MOCK);
    }

    @Override
    public PartTypeCategory findById(final Integer id) {
        return this.datastore.getPartTypeCategories().stream()
                .filter((category) -> id.equals(category.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public PartTypeCategory findByCategory(final String category) {
        return this.datastore.getPartTypeCategories().stream()
                .filter((partTypeCategory) ->
                        category.toLowerCase().equals(partTypeCategory.getCategory().toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    /**
     * findAll
     * <p>
     * Gets all part type categories.
     *
     * @return List<PartTypeCategory>
     */
    @Override
    public List<PartTypeCategory> findAll() {
        return this.datastore.getPartTypeCategories();
    }
}
