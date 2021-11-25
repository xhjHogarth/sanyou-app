package com.app.sanyou.entity;

import java.util.Date;

public class CollectHistory {
    private Integer id;

    private String collectCode;

    private Date collectDate;

    private String userId;

    private Float verticality;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return collect_code
     */
    public String getCollectCode() {
        return collectCode;
    }

    /**
     * @param collectCode
     */
    public void setCollectCode(String collectCode) {
        this.collectCode = collectCode;
    }

    /**
     * @return collect_date
     */
    public Date getCollectDate() {
        return collectDate;
    }

    /**
     * @param collectDate
     */
    public void setCollectDate(Date collectDate) {
        this.collectDate = collectDate;
    }

    /**
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return verticality
     */
    public Float getVerticality() {
        return verticality;
    }

    /**
     * @param verticality
     */
    public void setVerticality(Float verticality) {
        this.verticality = verticality;
    }
}