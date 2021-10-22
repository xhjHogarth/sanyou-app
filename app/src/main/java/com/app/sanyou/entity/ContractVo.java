package com.app.sanyou.entity;

/**
 * User: asus
 * Date: 2021-10-23
 * Time: 1:09
 * Version:V1.0
 */
public class ContractVo {

    private String contractId;

    private String projectId;

    private String contractName;

    /**
     * 阴极板起始编码
     */
    private String codeStart;
    /**
     * 阴极板结束编码
     */
    private String codeEnd;
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

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getCodeStart() {
        return codeStart;
    }

    public void setCodeStart(String codeStart) {
        this.codeStart = codeStart;
    }

    public String getCodeEnd() {
        return codeEnd;
    }

    public void setCodeEnd(String codeEnd) {
        this.codeEnd = codeEnd;
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
}
