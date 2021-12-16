package com.app.sanyou.entity;

import java.util.Date;
import java.util.List;

/**
 * User: asus
 * Date: 2021-10-22
 * Time: 20:05
 * Version:V1.0
 */
public class ProjectVo {

    private Integer id;

    private String projectName;

    private String projectCode;

    private Double ddbLength;

    private Double ddbWidth;

    private Double ddbHeight;

    private Double yjbLength;

    private Double yjbWidth;

    private Double yjbHeight;

    private String factoryId;

    private String userId;

    private Date createtime;

    private Date updatetime;

    private Byte deleteMark;

    private String username;

    private  String factoryName;

    private int hasFile;

    private String filename;

    private String ddbSize;
    private String yjbSize;

    /**
     * 阴极板总数
     */
    private int totalNum;
    /**
     * 使用中的阴极板总数
     */
    private int usingNum;
    /**
     * 维修中的阴极板总数
     */
    private int maintainNum;
    /**
     * 备用的阴极板总数
     */
    private int reserveNum;
    /**
     * 报废的阴极板总数
     */
    private int deprecatedNum;

    private List<OrderVo> orderList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public int getHasFile() {
        return hasFile;
    }

    public void setHasFile(int hasFile) {
        this.hasFile = hasFile;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getUsingNum() {
        return usingNum;
    }

    public void setUsingNum(int usingNum) {
        this.usingNum = usingNum;
    }

    public int getMaintainNum() {
        return maintainNum;
    }

    public void setMaintainNum(int maintainNum) {
        this.maintainNum = maintainNum;
    }

    public int getReserveNum() {
        return reserveNum;
    }

    public void setReserveNum(int reserveNum) {
        this.reserveNum = reserveNum;
    }

    public int getDeprecatedNum() {
        return deprecatedNum;
    }

    public void setDeprecatedNum(int deprecatedNum) {
        this.deprecatedNum = deprecatedNum;
    }

    public List<OrderVo> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderVo> orderList) {
        this.orderList = orderList;
    }
}
