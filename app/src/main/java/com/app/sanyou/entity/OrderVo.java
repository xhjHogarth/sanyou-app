package com.app.sanyou.entity;

import java.util.Date;

/**
 * User: asus
 * Date: 2021-12-13
 * Time: 15:35
 * Version:V1.0
 */
public class OrderVo {

    private Integer id;

    private String orderName;

    private String orderCode;

    private Double ddbLength;

    private Double ddbWidth;

    private Double ddbHeight;

    private Double yjbLength;

    private Double yjbWidth;

    private Double yjbHeight;

    private String userId;

    private Date createtime;

    private Date updatetime;

    private Byte deleteMark;

    private Integer projectId;

    private String username;

    private String ddbSize;
    private String yjbSize;

    private String projectCode;
    private String projectName;

    private String startProductCode;
    private String endProductCode;

    private String query;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Byte getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Byte deleteMark) {
        this.deleteMark = deleteMark;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getStartProductCode() {
        return startProductCode;
    }

    public void setStartProductCode(String startProductCode) {
        this.startProductCode = startProductCode;
    }

    public String getEndProductCode() {
        return endProductCode;
    }

    public void setEndProductCode(String endProductCode) {
        this.endProductCode = endProductCode;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
