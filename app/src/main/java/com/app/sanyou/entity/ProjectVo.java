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

    private String customProjectName;
    private String projectId;

    private String factoryId;
    private String dirPath;

    private String filename;

    private String extension;

    private Integer ddbLength;
    private Integer ddbWidth;
    private Integer ddbHeight;

    private Integer yjbLength;
    private Integer yjbWidth;
    private Integer yjbHeight;

    private String md5;

    private Date createtime;

    private String factoryName;
    private String projectName;
    private List<ContractData> contractDataList;

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

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<ContractData> getContractDataList() {
        return contractDataList;
    }

    public void setContractDataList(List<ContractData> contractDataList) {
        this.contractDataList = contractDataList;
    }

    public String getCustomProjectName() {
        return customProjectName;
    }

    public void setCustomProjectName(String customProjectName) {
        this.customProjectName = customProjectName;
    }

    public Integer getDdbLength() {
        return ddbLength;
    }

    public void setDdbLength(Integer ddbLength) {
        this.ddbLength = ddbLength;
    }

    public Integer getDdbWidth() {
        return ddbWidth;
    }

    public void setDdbWidth(Integer ddbWidth) {
        this.ddbWidth = ddbWidth;
    }

    public Integer getDdbHeight() {
        return ddbHeight;
    }

    public void setDdbHeight(Integer ddbHeight) {
        this.ddbHeight = ddbHeight;
    }

    public Integer getYjbLength() {
        return yjbLength;
    }

    public void setYjbLength(Integer yjbLength) {
        this.yjbLength = yjbLength;
    }

    public Integer getYjbWidth() {
        return yjbWidth;
    }

    public void setYjbWidth(Integer yjbWidth) {
        this.yjbWidth = yjbWidth;
    }

    public Integer getYjbHeight() {
        return yjbHeight;
    }

    public void setYjbHeight(Integer yjbHeight) {
        this.yjbHeight = yjbHeight;
    }
}
