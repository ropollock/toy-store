package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class KitType implements Identifiable {
    private Integer id;
    private String name;
    private Double basePrice;
    private Integer defaultBodyPartId;
    private Integer defaultEnginePartId;
    private Integer defaultWheelsPartId;
    private Integer defaultPowerSourcePartId;
    private Integer defaultColorPartId;
    private Integer defaultFinishPartId;

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

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Integer getDefaultBodyPartId() {
        return defaultBodyPartId;
    }

    public void setDefaultBodyPartId(Integer defaultBodyPartId) {
        this.defaultBodyPartId = defaultBodyPartId;
    }

    public Integer getDefaultEnginePartId() {
        return defaultEnginePartId;
    }

    public void setDefaultEnginePartId(Integer defaultEnginePartId) {
        this.defaultEnginePartId = defaultEnginePartId;
    }

    public Integer getDefaultWheelsPartId() {
        return defaultWheelsPartId;
    }

    public void setDefaultWheelsPartId(Integer defaultWheelsPartId) {
        this.defaultWheelsPartId = defaultWheelsPartId;
    }

    public Integer getDefaultPowerSourcePartId() {
        return defaultPowerSourcePartId;
    }

    public void setDefaultPowerSourcePartId(Integer defaultPowerSourcePartId) {
        this.defaultPowerSourcePartId = defaultPowerSourcePartId;
    }

    public Integer getDefaultColorPartId() {
        return defaultColorPartId;
    }

    public void setDefaultColorPartId(Integer defaultColorPartId) {
        this.defaultColorPartId = defaultColorPartId;
    }

    public Integer getDefaultFinishPartId() {
        return defaultFinishPartId;
    }

    public void setDefaultFinishPartId(Integer defaultFinishPartId) {
        this.defaultFinishPartId = defaultFinishPartId;
    }

    /**
     * getNonDefaultParts
     *
     * Compares the kit type with a kit and returns a kist of any non default part types.
     *
     * @param kit Kit
     * @return List<Integer>
     */
    @JsonIgnore
    public List<Integer> getNonDefaultParts(final Kit kit) {
        List<Integer> nonDefaultParts = new ArrayList<>();
        if(!this.getDefaultBodyPartId().equals(kit.getBodyPartId())) {
            nonDefaultParts.add(kit.getBodyPartId());
        }
        if(!this.getDefaultEnginePartId().equals(kit.getEnginePartId())) {
            nonDefaultParts.add(kit.getEnginePartId());
        }
        if(!this.getDefaultWheelsPartId().equals(kit.getWheelsPartId())) {
            nonDefaultParts.add(kit.getWheelsPartId());
        }
        if(!this.getDefaultPowerSourcePartId().equals(kit.getPowerSourcePartId())) {
            nonDefaultParts.add(kit.getPowerSourcePartId());
        }
        if(!this.getDefaultColorPartId().equals(kit.getColorPartId())) {
            nonDefaultParts.add(kit.getColorPartId());
        }
        if(!this.getDefaultFinishPartId().equals(kit.getFinishPartId())) {
            nonDefaultParts.add(kit.getFinishPartId());
        }
        return nonDefaultParts;
    }

    @Override
    public String toString() {
        return "KitType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", basePrice=" + basePrice +
                ", defaultBodyPartId=" + defaultBodyPartId +
                ", defaultEnginePartId=" + defaultEnginePartId +
                ", defaultWheelsPartId=" + defaultWheelsPartId +
                ", defaultPowerSourcePartId=" + defaultPowerSourcePartId +
                ", defaultColorPartId=" + defaultColorPartId +
                ", defaultFinishPartId=" + defaultFinishPartId +
                '}';
    }
}
