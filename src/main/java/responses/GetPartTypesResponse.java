package responses;

import models.PartType;

import java.util.List;

public class GetPartTypesResponse {
    private List<PartType> partTypes;

    public GetPartTypesResponse(List<PartType> partTypes) {
        this.partTypes = partTypes;
    }

    public List<PartType> getPartTypes() {
        return partTypes;
    }
}
