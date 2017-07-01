package requests;

import models.Kit;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CreateOrderRequest {
    @NotEmpty(message = "`kits` must not be not be empty.")
    private List<Kit> kits;

    @NotBlank(message = "`shippingAddress` must not be blank.")
    private String shippingAddress;

    @NotNull(message = "`customerId` must not be blank.")
    private Integer customerId;

    public List<Kit> getKits() {
        return kits;
    }

    public void setKits(List<Kit> kits) {
        this.kits = kits;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "CreateOrderRequest{" +
                "kits=" + kits +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", customerId=" + customerId +
                '}';
    }
}
