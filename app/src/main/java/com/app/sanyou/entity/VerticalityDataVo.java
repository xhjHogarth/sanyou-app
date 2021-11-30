package com.app.sanyou.entity;

import java.util.Date;
import java.util.List;

public class VerticalityDataVo {

    /**
     * 阴极板Id
     */
    private String verticalityId;
    /**
     * 项目Id
     */
    private String projectId;
    /**
     * 合同Id
     */
    private String contractId;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 垂直度
     */
    private Float verticality;
    /**
     * 阴极板状态
     */
    private Integer state;

    /**
     * 阴极板维修类型
     */
    private Integer maintainType;

    /**
     * 修改阴极板状态的时间
     */
    private Date updateStateDate;

    /**
     * 报修时间
     */
    private Date updateMaintainDate;

    /**
     * 报修人
     */
    private String userid;

    private String username;

    private String query;

    /**
     * 阴极板是否收藏(1-收藏,2-未收藏)
     */
    private int collectStatus;
    /**
     * 阴极板动态检测数据
     */
    private List<IndustryData> industryDataList;

    /**
     * 导电棒尺寸
     */
    private String ddbSize;

    /**
     * 阴极板尺寸
     */
    private String yjbSize;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getVerticalityId() {
        return verticalityId;
    }

    public void setVerticalityId(String verticalityId) {
        this.verticalityId = verticalityId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Float getVerticality() {
        return verticality;
    }

    public void setVerticality(Float verticality) {
        this.verticality = verticality;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public Integer getMaintainType() {
        return maintainType;
    }

    public void setMaintainType(Integer maintainType) {
        this.maintainType = maintainType;
    }

    public Date getUpdateStateDate() {
        return updateStateDate;
    }

    public void setUpdateStateDate(Date updateStateDate) {
        this.updateStateDate = updateStateDate;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}