package responses;

import models.PartTypeCategory;

import java.util.List;

public class GetPartTypeCategoriesResponse {
    private List<PartTypeCategory> partTypeCategories;

    public GetPartTypeCategoriesResponse(List<PartTypeCategory> partTypeCategories) {
        this.partTypeCategories = partTypeCategories;
    }

    public List<PartTypeCategory> getPartTypeCategories() {
        return partTypeCategories;
    }
}
