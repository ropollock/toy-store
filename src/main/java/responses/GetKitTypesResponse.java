package responses;

import models.KitType;

import java.util.List;

public class GetKitTypesResponse {
    private List<KitType> kitTypes;

    public GetKitTypesResponse(List<KitType> kitTypes) {
        this.kitTypes = kitTypes;
    }

    public List<KitType> getKitTypes() {
        return kitTypes;
    }
}
