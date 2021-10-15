package com.app.sanyou.entity;

import java.util.Date;

public class Equipment {

    private String id;

    /**
     * 设备编号
     */
    private String equipNo;

    /**
     * 厂家
     */
    private String factoryId;

    /**
     * 生产线
     */
    private String subFactoryId;

    /**
     * 设备状态
     */
    private Byte equipStatus;

    /**
     * 设备在线时长
     */
    private Date equipOnlineTime;

    /**
     * 设备周期
     */
    private Integer equipCycle;

    /**
     * 设备健康上限
     */
    private Double equipHealthLimit;

    /**
     * 设备亚健康上限
     */
    private Double equipSubhealthLimit;

    /**
     * 设备地址
     */
    private String equipAddress;

    /**
     * 经度
     */
    private Double equipLng;

    /**
     * 纬度
     */
    private Double equipLat;

    private Date createtime;

    private Date updatetime;

    private Date deletetime;

    private Byte deleteMark;

    private Byte enableMark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEquipNo() {
        return equipNo;
    }

    public void setEquipNo(String equipNo) {
        this.equipNo = equipNo;
    }

    public String getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    public String getSubFactoryId() {
        return subFactoryId;
    }

    public void setSubFactoryId(String subFactoryId) {
        this.subFactoryId = subFactoryId;
    }

    public Byte getEquipStatus() {
        return equipStatus;
    }

    public void setEquipStatus(Byte equipStatus) {
        this.equipStatus = equipStatus;
    }

    public Date getEquipOnlineTime() {
        return equipOnlineTime;
    }

    public void setEquipOnlineTime(Date equipOnlineTime) {
        this.equipOnlineTime = equipOnlineTime;
    }

    public Integer getEquipCycle() {
        return equipCycle;
    }

    public void setEquipCycle(Integer equipCycle) {
        this.equipCycle = equipCycle;
    }

    public Double getEquipHealthLimit() {
        return equipHealthLimit;
    }

    public void setEquipHealthLimit(Double equipHealthLimit) {
        this.equipHealthLimit = equipHealthLimit;
    }

    public Double getEquipSubhealthLimit() {
        return equipSubhealthLimit;
    }

    public void setEquipSubhealthLimit(Double equipSubhealthLimit) {
        this.equipSubhealthLimit = equipSubhealthLimit;
    }

    public String getEquipAddress() {
        return equipAddress;
    }

    public void setEquipAddress(String equipAddress) {
        this.equipAddress = equipAddress;
    }

    public Double getEquipLng() {
        return equipLng;
    }

    public void setEquipLng(Double equipLng) {
        this.equipLng = equipLng;
    }

    public Double getEquipLat() {
        return equipLat;
    }

    public void setEquipLat(Double equipLat) {
        this.equipLat = equipLat;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getDeletetime() {
        return deletetime;
    }

    public void setDeletetime(Date deletetime) {
        this.deletetime = deletetime;
    }

    public Byte getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Byte deleteMark) {
        this.deleteMark = deleteMark;
    }

    public Byte getEnableMark() {
        return enableMark;
    }

    public void setEnableMark(Byte enableMark) {
        this.enableMark = enableMark;
    }
}
