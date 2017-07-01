package repository;

import models.PartInventory;

import java.util.List;
import java.util.stream.Collectors;

public class MockPartInventoryDao implements PartInventoryDao {
    private final Datastore datastore;

    public MockPartInventoryDao() {
        this.datastore = DatastoreFactory.getDatastore(DatastoreFactory.DATASTORE_TYPES.MOCK);
    }

    /**
     * findByPartTypeId
     *
     * Gets a part inventory by part type id.
     *
     * @param id Integer part type id
     * @return PartInventory
     */
    @Override
    public PartInventory findByPartTypeId(final Integer id) {
        return this.datastore.getPartInventories().stream()
                .filter((partInventory) -> id.equals(partInventory.getId()))
                .findFirst()
                .orElse(null);
    }

    /**
     * findStockBelow
     *
     * Gets all part inventories with stock below the given threshold.
     *
     * @param threshold Integer
     * @return List<PartInventory>
     */
    @Override
    public List<PartInventory> findStockBelow(final Integer threshold) {
        return this.datastore.getPartInventories().stream()
                .filter((partInventory) -> partInventory.getStock() < threshold)
                .collect(Collectors.toList());
    }

    /**
     * removeStock
     * <p>
     * Removes stock from the part inventory by amount.
     *
     * @param id     Integer id
     * @param amount Integer to decrease stock by
     */
    @Override
    public void removeStock(Integer id, Integer amount) {
        this.datastore.getPartInventories().stream()
                .filter((partInventory) -> id.equals(partInventory.getId()))
                .forEach(part -> part.setStock(part.getStock() -1));
    }
}
