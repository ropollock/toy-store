package domain;

import models.PartInventory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.DaoFactory;
import repository.PartInventoryDao;
import repository.PartTypeDao;

import java.util.List;

public class PartInventoryService {
    private final static Logger LOGGER = LogManager.getLogger(PartInventoryService.class);
    private PartInventoryDao partInventoryDao;
    private final Integer threshold = 10;

    public PartInventoryService() {
        init();
    }

    private void init() {
        this.partInventoryDao = DaoFactory.getPartInventoryDao(DaoFactory.DAO_TYPES.MOCK);
    }

    public void registerPartTypesSale(List<Integer> partTypeIds) {
        partTypeIds.stream()
                .map(partInventoryDao::findByPartTypeId)
                .forEach(this::removeStock);

        partTypeIds.stream()
                .map(partInventoryDao::findByPartTypeId)
                .filter(this::checkPartTypeThreshold)
                .forEach(this::notifyDistribution);
    }

    private void removeStock(PartInventory partInventory) {
        partInventoryDao.removeStock(partInventory.getId(), 1);
    }

    private boolean checkPartTypeThreshold(PartInventory partInventory) {
        boolean lowStock = partInventory.getStock() < this.threshold;
        if(lowStock) {
            LOGGER.info(String.format("Low stock for part type %s stock: %s",
                    partInventory.getId(), partInventory.getStock()));
        } else {
            LOGGER.info(String.format("Plenty of stock for part type %s stock: %s",
                    partInventory.getId(), partInventory.getStock()));
        }
        return lowStock;
    }

    /**
     * notifyDistribution
     *
     * Shim for a call to an external part distribution service.
     *
     * @param partInventory PartInventory
     */
    private void notifyDistribution(PartInventory partInventory) {
        LOGGER.info(
                String.format("(shim) Notifying part distribution that part type %s is too low." ,
                        partInventory.getId()));
    }
}
