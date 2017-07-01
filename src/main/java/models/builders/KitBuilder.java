package models.builders;

import models.Kit;
import models.KitType;

public class KitBuilder {
    private Integer id;
    private Integer kitTypeId;
    private String name;
    private Integer bodyPartId;
    private Integer enginePartId;
    private Integer wheelsPartId;
    private Integer powerSourcePartId;
    private Integer colorPartId;
    private Integer finishPartId;
    private String partsHash;
    private Integer orderId;

    public KitBuilder() {}

    /**
     * KitBuilder
     *
     * Takes a Kit Type and applies its default values to the Kit Builder.
     *
     * @param kitType KitType
     */
    public KitBuilder(final KitType kitType) {
        this.setKitTypeId(kitType.getId());
        this.setName(kitType.getName());
        this.setWheelsPartId(kitType.getDefaultWheelsPartId());
        this.setPowerSourcePartId(kitType.getDefaultPowerSourcePartId());
        this.setBodyPartId(kitType.getDefaultBodyPartId());
        this.setColorPartId(kitType.getDefaultColorPartId());
        this.setFinishPartId(kitType.getDefaultFinishPartId());
        this.setEnginePartId(kitType.getDefaultEnginePartId());
    }

    public Integer getId() {
        return id;
    }

    public KitBuilder setId(final Integer id) {
        this.id = id;
        return this;
    }

    public Integer getKitTypeId() {
        return kitTypeId;
    }

    public KitBuilder setKitTypeId(final Integer kitTypeId) {
        this.kitTypeId = kitTypeId;
        return this;
    }

    public String getName() {
        return name;
    }

    public KitBuilder setName(final String name) {
        this.name = name;
        return this;
    }

    public Integer getBodyPartId() {
        return bodyPartId;
    }

    public KitBuilder setBodyPartId(final Integer bodyPartId) {
        this.bodyPartId = bodyPartId;
        return this;
    }

    public Integer getEnginePartId() {
        return enginePartId;
    }

    public KitBuilder setEnginePartId(final Integer enginePartId) {
        this.enginePartId = enginePartId;
        return this;
    }

    public Integer getWheelsPartId() {
        return wheelsPartId;
    }

    public KitBuilder setWheelsPartId(final Integer wheelsPartId) {
        this.wheelsPartId = wheelsPartId;
        return this;
    }

    public Integer getPowerSourcePartId() {
        return powerSourcePartId;
    }

    public KitBuilder setPowerSourcePartId(final Integer powerSourcePartId) {
        this.powerSourcePartId = powerSourcePartId;
        return this;
    }

    public Integer getColorPartId() {
        return colorPartId;
    }

    public KitBuilder setColorPartId(final Integer colorPartId) {
        this.colorPartId = colorPartId;
        return this;
    }

    public Integer getFinishPartId() {
        return finishPartId;
    }

    public KitBuilder setFinishPartId(final Integer finishPartId) {
        this.finishPartId = finishPartId;
        return this;
    }

    public String getPartsHash() {
        return partsHash;
    }

    public KitBuilder setPartHash(final String partHash) {
        this.partsHash = partHash;
        return this;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public KitBuilder setOrderId(final Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public Kit build() {
        return new Kit(this);
    }
}
