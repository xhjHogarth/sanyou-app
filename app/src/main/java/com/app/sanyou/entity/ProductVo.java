package com.app.sanyou.entity;

import java.util.Date;
import java.util.List;

/**
 * User: asus
 * Date: 2021-12-13
 * Time: 20:02
 * Version:V1.0
 */
public class ProductVo {
    private Integer id;

    private String productCode;

    private Double productValue;

    private Integer productState;

    private Date createtime;

    private Date updateStateDate;

    private Integer maintainType;

    private Date updateMaintainDate;

    private String userid;

    private Double ddbLength;

    private Double ddbWidth;

    private Double ddbHeight;

    private Double yjbLength;

    private Double yjbWidth;

    private Double yjbHeight;

    private String ddbSize;
    private String yjbSize;

    private String query;
    private String factoryId;

    private String username;

    /**
     * 阴极板动态检测数据
     */
    private List<IndustryData> industryDataList;

    /**
     * 阴极板是否收藏(1-收藏,2-未收藏)
     */
    private int collectStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Double getProductValue() {
        return productValue;
    }

    public void setProductValue(Double productValue) {
        this.productValue = productValue;
    }

    public Integer getProductState() {
        return productState;
    }

    public void setProductState(Integer productState) {
        this.productState = productState;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdateStateDate() {
        return updateStateDate;
    }

    public void setUpdateStateDate(Date updateStateDate) {
        this.updateStateDate = updateStateDate;
    }

    public Integer getMaintainType() {
        return maintainType;
    }

    public void setMaintainType(Integer maintainType) {
        this.maintainType = maintainType;
    }

    public Date getUpdateMaintainDate() {
        return updateMaintainDate;
    }

    public void setUpdateMaintainDate(Date updateMaintainDate) {
        this.updateMaintainDate = updateMaintainDate;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Double getDdbLength() {
        return ddbLength;
    }

    public void setDdbLength(Double ddbLength) {
        this.ddbLength = ddbLength;
    }

    public Double getDdbWidth() {
        return ddbWidth;
    }

    public void setDdbWidth(Double ddbWidth) {
        this.ddbWidth = ddbWidth;
    }

    public Double getDdbHeight() {
        return ddbHeight;
    }

    public void setDdbHeight(Double ddbHeight) {
        this.ddbHeight = ddbHeight;
    }

    public Double getYjbLength() {
        return yjbLength;
    }

    public void setYjbLength(Double yjbLength) {
        this.yjbLength = yjbLength;
    }

    public Double getYjbWidth() {
        return yjbWidth;
    }

    public void setYjbWidth(Double yjbWidth) {
        this.yjbWidth = yjbWidth;
    }

    public Double getYjbHeight() {
        return yjbHeight;
    }

    public void setYjbHeight(Double yjbHeight) {
        this.yjbHeight = yjbHeight;
    }

    public String getDdbSize() {
        return ddbSize;
    }

    public void setDdbSize(String ddbSize) {
        this.ddbSize = ddbSize;
    }

    public String getYjbSize() {
        return yjbSize;
    }

    public void setYjbSize(String yjbSize) {
        this.yjbSize = yjbSize;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<IndustryData> getIndustryDataList() {
        return industryDataList;
    }

    public void setIndustryDataList(List<IndustryData> industryDataList) {
        this.industryDataList = industryDataList;
    }

    public int getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(int collectStatus) {
        this.collectStatus = collectStatus;
    }
}
