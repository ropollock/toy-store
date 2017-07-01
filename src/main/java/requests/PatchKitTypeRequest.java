package requests;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PatchKitTypeRequest {
    @NotNull
    @NumberFormat(style = NumberFormat.Style.NUMBER) @Min(0)
    private Double basePrice;

    public Double getBasePrice() {
        return basePrice;
    }

    @Override
    public String toString() {
        return "PatchKitTypeRequest{" +
                "basePrice=" + basePrice +
                '}';
    }
}
