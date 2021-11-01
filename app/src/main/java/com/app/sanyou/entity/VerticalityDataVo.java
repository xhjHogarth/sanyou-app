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
    private Integer verticality;
    /**
     * 阴极板状态
     */
    private Integer state;
    /**
     * 阴极板是否收藏(1-收藏,2-未收藏)
     */
    private int collectStatus;
    /**
     * 阴极板动态检测数据
     */
    private List<IndustryData> industryDataList;

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

    public Integer getVerticality() {
        return verticality;
    }

    public void setVerticality(Integer verticality) {
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
}