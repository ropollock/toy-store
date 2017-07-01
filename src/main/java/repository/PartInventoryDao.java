package repository;

import models.PartInventory;

import java.util.List;

public interface PartInventoryDao {
    /**
     * findByPartTypeId
     *
     * Gets a part inventory by part type id.
     *
     * @param id Integer part type id
     * @return PartInventory
     */
    PartInventory findByPartTypeId(Integer id);

    /**
     * findStockBelow
     *
     * Gets all part inventories with stock below the given threshold.
     *
     * @param threshold Integer
     * @return List<PartInventory>
     */
    List<PartInventory> findStockBelow(Integer threshold);

    /**
     * removeStock
     *
     * Removes stock from the part inventory by amount.
     *
     * @param id Integer id
     * @param amount Integer to decrease stock by
     */
    void removeStock(Integer id, Integer amount);
}
