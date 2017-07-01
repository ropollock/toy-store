package models;

public class PartTypeCategory implements Identifiable {
    private Integer id;
    private String category;

    public PartTypeCategory() {}

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "PartTypeCategory{" +
                "id=" + id +
                ", category='" + category + '\'' +
                '}';
    }
}
