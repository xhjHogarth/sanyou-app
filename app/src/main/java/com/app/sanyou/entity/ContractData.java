package com.app.sanyou.entity;


public class ContractData {
    private String customProjectName;

    private String customContractName;

    private String projectId;

    private String contractId;

    private String contractName;

    public String getCustomProjectName() {
        return customProjectName;
    }

    public void setCustomProjectName(String customProjectName) {
        this.customProjectName = customProjectName;
    }

    public String getCustomContractName() {
        return customContractName;
    }

    public void setCustomContractName(String customContractName) {
        this.customContractName = customContractName;
    }

    /**
     * @return contract_id
     */
    public String getContractId() {
        return contractId;
    }

    /**
     * @param contractId
     */
    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    /**
     * @return project_id
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * @param projectId
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * @return contract_name
     */
    public String getContractName() {
        return contractName;
    }

    /**
     * @param contractName
     */
    public void setContractName(String contractName) {
        this.contractName = contractName;
    }
}