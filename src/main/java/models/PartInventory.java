package models;

public class PartInventory implements Identifiable {
    private Integer id;
    private Integer stock;

    public PartInventory() {}

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "PartInventory{" +
                "id=" + id +
                ", stock=" + stock +
                '}';
    }
}
