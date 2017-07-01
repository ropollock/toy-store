package requests;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PatchPartTypeRequest {
    @NotNull @NumberFormat(style = NumberFormat.Style.NUMBER) @Min(0)
    private Double surcharge;

    public Double getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(Double surcharge) {
        this.surcharge = surcharge;
    }

    @Override
    public String toString() {
        return "PatchPartTypeRequest{" +
                "surcharge=" + surcharge +
                '}';
    }
}
