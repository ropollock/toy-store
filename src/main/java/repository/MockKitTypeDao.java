package repository;

import models.KitType;

import java.util.List;

public class MockKitTypeDao implements KitTypeDao {
    private final Datastore datastore;

    public MockKitTypeDao() {
        this.datastore = DatastoreFactory.getDatastore(DatastoreFactory.DATASTORE_TYPES.MOCK);
    }

    /**
     * findAll
     * <p>
     * Get all kit types.
     *
     * @return List<KitType>
     */
    @Override
    public List<KitType> findAll() {
        return this.datastore.getKitTypes();
    }

    /**
     * findById
     * <p>
     * Get kit type by id.
     *
     * @param id Integer kit type id
     * @return KitType
     */
    @Override
    public KitType findById(Integer id) {
        return this.datastore.getKitTypes().stream()
                .filter((kitType) -> id.equals(kitType.getId()))
                .findFirst()
                .orElse(null);
    }

    /**
     * updateBasePrice
     * <p>
     * Updates base price of a kit type.
     *
     * @param id        Kit Type id
     * @param basePrice Double price
     */
    @Override
    public void updateBasePrice(Integer id, Double basePrice) {
        this.datastore.getKitTypes().stream()
                .filter((kitType) -> id.equals(kitType.getId()))
                .findFirst()
                .get().setBasePrice(basePrice);
    }
}
