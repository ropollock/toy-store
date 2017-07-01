package models;

public class PartType implements Identifiable {
    private Integer id;
    private String name;
    private Double surcharge;
    private Integer categoryId;

    public PartType() {}

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(Double surcharge) {
        this.surcharge = surcharge;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "PartType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surcharge=" + surcharge +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }
}
