package com.app.sanyou.entity;

import java.util.Date;
import java.util.List;

/**
 * User: asus
 * Date: 2021/6/14
 * Time: 15:14
 * Version:V1.0
 */
public class IndustryDataVo {

    private Date datatime;

    private String plateno;
    private String lineno;
    private Integer totalnumber;
    private String max1;
    private String max2;
    private String max3;
    private String max4;
    private String max5;
    private String max6;
    private String max7;
    private String max8;
    private String max9;
    private String max10;
    private String max11;
    private String max12;
    private String max13;
    private String max14;
    private String max15;
    private String max16;
    private String max17;
    private String max18;
    private String max19;
    private String max20;
    private String max21;
    private String max22;
    private String max23;
    private String max24;
    private String max25;
    private String max26;
    private Double max;
    private Integer maxno;

    private Date startTime;
    private Date endTime;
    private String userId;
    private Double equipHealthLimit;
    private Double equipSubhealthLimit;

    //饼图
    private int value;
    private String name;

    //柱状图
    private String date;
    private int healthValue;
    private int subHealthValue;
    private int unHealthValue;

    //查询条件
    private String queryLabel;
    private String query;

    //private List<TableData> list;   //表格数据
    private int page;			// 当前页数
    private int total;			// 总页数
    private long records;		// 总记录数

    //正太分布数据
    private List<Integer> levelNums;
    private List<String> levelNames;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getLevelNums() {
        return levelNums;
    }

    public void setLevelNums(List<Integer> levelNums) {
        this.levelNums = levelNums;
    }

    public List<String> getLevelNames() {
        return levelNames;
    }

    public void setLevelNames(List<String> levelNames) {
        this.levelNames = levelNames;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public long getRecords() {
        return records;
    }

    public void setRecords(long records) {
        this.records = records;
    }

    public String getQueryLabel() {
        return queryLabel;
    }

    public void setQueryLabel(String queryLabel) {
        this.queryLabel = queryLabel;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

//    public List<TableData> getList() {
//        return list;
//    }
//
//    public void setList(List<TableData> list) {
//        this.list = list;
//    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHealthValue() {
        return healthValue;
    }

    public void setHealthValue(int healthValue) {
        this.healthValue = healthValue;
    }

    public int getSubHealthValue() {
        return subHealthValue;
    }

    public void setSubHealthValue(int subHealthValue) {
        this.subHealthValue = subHealthValue;
    }

    public int getUnHealthValue() {
        return unHealthValue;
    }

    public void setUnHealthValue(int unHealthValue) {
        this.unHealthValue = unHealthValue;
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



    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDatatime() {
        return datatime;
    }

    public void setDatatime(Date datatime) {
        this.datatime = datatime;
    }

    public String getPlateno() {
        return plateno;
    }

    public void setPlateno(String plateno) {
        this.plateno = plateno;
    }

    public String getLineno() {
        return lineno;
    }

    public void setLineno(String lineno) {
        this.lineno = lineno;
    }

    public Integer getTotalnumber() {
        return totalnumber;
    }

    public void setTotalnumber(Integer totalnumber) {
        this.totalnumber = totalnumber;
    }

    public String getMax1() {
        return max1;
    }

    public void setMax1(String max1) {
        this.max1 = max1;
    }

    public String getMax2() {
        return max2;
    }

    public void setMax2(String max2) {
        this.max2 = max2;
    }

    public String getMax3() {
        return max3;
    }

    public void setMax3(String max3) {
        this.max3 = max3;
    }

    public String getMax4() {
        return max4;
    }

    public void setMax4(String max4) {
        this.max4 = max4;
    }

    public String getMax5() {
        return max5;
    }

    public void setMax5(String max5) {
        this.max5 = max5;
    }

    public String getMax6() {
        return max6;
    }

    public void setMax6(String max6) {
        this.max6 = max6;
    }

    public String getMax7() {
        return max7;
    }

    public void setMax7(String max7) {
        this.max7 = max7;
    }

    public String getMax8() {
        return max8;
    }

    public void setMax8(String max8) {
        this.max8 = max8;
    }

    public String getMax9() {
        return max9;
    }

    public void setMax9(String max9) {
        this.max9 = max9;
    }

    public String getMax10() {
        return max10;
    }

    public void setMax10(String max10) {
        this.max10 = max10;
    }

    public String getMax11() {
        return max11;
    }

    public void setMax11(String max11) {
        this.max11 = max11;
    }

    public String getMax12() {
        return max12;
    }

    public void setMax12(String max12) {
        this.max12 = max12;
    }

    public String getMax13() {
        return max13;
    }

    public void setMax13(String max13) {
        this.max13 = max13;
    }

    public String getMax14() {
        return max14;
    }

    public void setMax14(String max14) {
        this.max14 = max14;
    }

    public String getMax15() {
        return max15;
    }

    public void setMax15(String max15) {
        this.max15 = max15;
    }

    public String getMax16() {
        return max16;
    }

    public void setMax16(String max16) {
        this.max16 = max16;
    }

    public String getMax17() {
        return max17;
    }

    public void setMax17(String max17) {
        this.max17 = max17;
    }

    public String getMax18() {
        return max18;
    }

    public void setMax18(String max18) {
        this.max18 = max18;
    }

    public String getMax19() {
        return max19;
    }

    public void setMax19(String max19) {
        this.max19 = max19;
    }

    public String getMax20() {
        return max20;
    }

    public void setMax20(String max20) {
        this.max20 = max20;
    }

    public String getMax21() {
        return max21;
    }

    public void setMax21(String max21) {
        this.max21 = max21;
    }

    public String getMax22() {
        return max22;
    }

    public void setMax22(String max22) {
        this.max22 = max22;
    }

    public String getMax23() {
        return max23;
    }

    public void setMax23(String max23) {
        this.max23 = max23;
    }

    public String getMax24() {
        return max24;
    }

    public void setMax24(String max24) {
        this.max24 = max24;
    }

    public String getMax25() {
        return max25;
    }

    public void setMax25(String max25) {
        this.max25 = max25;
    }

    public String getMax26() {
        return max26;
    }

    public void setMax26(String max26) {
        this.max26 = max26;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Integer getMaxno() {
        return maxno;
    }

    public void setMaxno(Integer maxno) {
        this.maxno = maxno;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}

