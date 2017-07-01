package domain;

import models.KitType;
import repository.DaoFactory;
import repository.KitTypeDao;

import java.util.List;

public class KitTypeService {
    private KitTypeDao kitTypeDao;

    public KitTypeService() { init(); }

    private void init() {
        kitTypeDao = DaoFactory.getKitTypeDao(DaoFactory.DAO_TYPES.MOCK);
    }

    public List<KitType> getKitTypes() {
        return kitTypeDao.findAll();
    }

    public void updateBasePrice(final Integer kitTypeId, final Double basePrice) throws NullPointerException {
        KitType kitType = kitTypeDao.findById(kitTypeId);
        if(kitType == null) {
            throw new NullPointerException("Kit Type not found.");
        }

        kitTypeDao.updateBasePrice(kitTypeId, basePrice);
    }
}
