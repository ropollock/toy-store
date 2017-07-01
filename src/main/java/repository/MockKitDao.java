package repository;

import models.Kit;

import java.util.List;
import java.util.stream.Collectors;

public class MockKitDao implements KitDao {
    private final Datastore datastore;

    public MockKitDao() {
        this.datastore = DatastoreFactory.getDatastore(DatastoreFactory.DATASTORE_TYPES.MOCK);
    }

    /**
     * findAll
     * <p>
     * Get all kits.
     *
     * @return List<Kit>
     */
    @Override
    public List<Kit> findAll() {
        return this.datastore.getKits();
    }

    /**
     * insertKit
     * <p>
     * Adds a kit and returns an id.
     *
     * @param kit Kit
     * @return Integer id inserted
     */
    @Override
    public Integer insertKit(Kit kit) throws NullPointerException {
        kit.setId(this.datastore.nextId(this.datastore.getKits()));
        assertValidKit(kit);
        this.datastore.getKits().add(kit);
        return kit.getId();
    }

    private void assertValidKit(Kit kit) throws NullPointerException {
        // Check that the kit has a kit type id
        if(kit.getKitTypeId() == null) {
            throw new NullPointerException("Kit must have a kit type id.");
        }
    }

    /**
     * findById
     * <p>
     * Get kit by id.
     *
     * @param id Integer kit id
     * @return Kit
     */
    @Override
    public Kit findById(Integer id) {
        return this.datastore.getKits().stream()
                .filter((kit) -> id.equals(kit.getId()))
                .findFirst()
                .orElse(null);
    }

    /**
     * findByPartHash
     * <p>
     * Get all kits by part hash.
     *
     * @param partHash String sha1
     * @return List<Kit>
     */
    @Override
    public List<Kit> findByPartHash(String partHash) {
        return this.datastore.getKits().stream()
                .filter((kit) -> partHash.equals(kit.getPartsHash()))
                .collect(Collectors.toList());
    }

    /**
     * findByOrderId
     * <p>
     * Gets all kits by order id.
     *
     * @param orderId Integer order id
     * @return List<Kit>
     */
    @Override
    public List<Kit> findByOrderId(Integer orderId) {
        return this.datastore.getKits().stream()
                .filter((kit) -> orderId.equals(kit.getOrderId()))
                .collect(Collectors.toList());
    }
}
