package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.builders.KitBuilder;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class Kit implements Identifiable {
    private static final Logger LOGGER = LogManager.getLogger(Kit.class);

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

    public Kit() {}

    public Kit(KitBuilder builder) {
        this.id = builder.getId();
        this.name = builder.getName();
        this.kitTypeId = builder.getKitTypeId();
        this.bodyPartId = builder.getBodyPartId();
        this.enginePartId = builder.getEnginePartId();
        this.wheelsPartId = builder.getWheelsPartId();
        this.powerSourcePartId = builder.getPowerSourcePartId();
        this.colorPartId = builder.getColorPartId();
        this.finishPartId = builder.getFinishPartId();
        this.partsHash =
                (builder.getPartsHash() != null) ? builder.getPartsHash() : Kit.generatePartHash(this);
        this.orderId = builder.getOrderId();
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getKitTypeId() {
        return kitTypeId;
    }

    public String getName() {
        return name;
    }

    public Integer getBodyPartId() {
        return bodyPartId;
    }

    public Integer getEnginePartId() {
        return enginePartId;
    }

    public Integer getWheelsPartId() {
        return wheelsPartId;
    }

    public Integer getPowerSourcePartId() {
        return powerSourcePartId;
    }

    public String getPartsHash() {
        return partsHash;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getFinishPartId() {
        return finishPartId;
    }

    public Integer getColorPartId() {
        return colorPartId;
    }

    @JsonIgnore
    public List<Integer> getPartTypeIds() {
        return Arrays.asList(bodyPartId,
                enginePartId,
                wheelsPartId,
                powerSourcePartId,
                colorPartId,
                finishPartId);
    }

    @Override
    public String toString() {
        return "Kit{" +
                "id=" + id +
                ", kitTypeId=" + kitTypeId +
                ", name='" + name + '\'' +
                ", bodyPartId=" + bodyPartId +
                ", enginePartId=" + enginePartId +
                ", wheelsPartId=" + wheelsPartId +
                ", powerSourcePartId=" + powerSourcePartId +
                ", colorPartId=" + colorPartId +
                ", finishPartId=" + finishPartId +
                ", partsHash='" + partsHash + '\'' +
                ", orderId=" + orderId +
                '}';
    }

    /**
     * generatePartHash
     *
     * Returns a sha1 hash describing the unique parts of a kit.
     * The hash order is:
     * kitTypeId, bodyPartId, enginePartId, powerSourcePartId,
     * wheelsPartId, colorPartId, finishPartId
     *
     * @param kit Kit
     * @return String
     */
    public static String generatePartHash(final Kit kit) {
        StringBuilder sb = new StringBuilder();
        sb.append(kit.getKitTypeId());
        sb.append(kit.getBodyPartId());
        sb.append(kit.getEnginePartId());
        sb.append(kit.getPowerSourcePartId());
        sb.append(kit.getWheelsPartId());
        sb.append(kit.getColorPartId());
        sb.append(kit.getFinishPartId());
        return DigestUtils.sha1Hex(sb.toString());
    }
}
