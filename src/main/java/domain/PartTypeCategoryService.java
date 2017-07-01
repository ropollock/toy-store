package domain;

import models.PartTypeCategory;
import repository.DaoFactory;
import repository.PartTypeCategoryDao;

import java.util.List;

public class PartTypeCategoryService {
    private PartTypeCategoryDao partTypeCategoryDao;

    public PartTypeCategoryService() { init(); }

    private void init() {
        partTypeCategoryDao = DaoFactory.getPartTypeCategory(DaoFactory.DAO_TYPES.MOCK);
    }

    public List<PartTypeCategory> getPartTypeCategories() {
        return partTypeCategoryDao.findAll();
    }
}
