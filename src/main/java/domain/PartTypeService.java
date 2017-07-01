package domain;

import models.PartType;
import repository.DaoFactory;
import repository.PartTypeDao;

import java.util.List;

public class PartTypeService {
    private PartTypeDao partTypeDao;

    public PartTypeService() { init(); }

    private void init() {
        partTypeDao = DaoFactory.getPartTypeDao(DaoFactory.DAO_TYPES.MOCK);
    }

    public List<PartType> getPartTypes() {
        return partTypeDao.findAll();
    }

    public void updateSurcharge(final Integer partTypeId, final Double surcharge) throws NullPointerException {
        PartType partType = partTypeDao.findById(partTypeId);
        if(partType == null) {
            throw new NullPointerException("Part Type not found.");
        }

        partTypeDao.updateSurcharge(partTypeId, surcharge);
    }
}
