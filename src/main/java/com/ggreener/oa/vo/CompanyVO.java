package com.ggreener.oa.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lifu on 2018/10/8.
 * <p>
 * XXX
 */
public class CompanyVO implements Serializable {
    private static final long serialVersionUID = 1441455311765181191L;
    //公司ID
    private Long id;
    //公司名称
    private String name;
    //关注等级
    private Long attention;
    //地区
    private Long region;
    //中关村
    private Long zol;
    //单位性质
    private Long unitProperty;
    //成立时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM", timezone = "GMT+8")
    private Date establishedTime;
    //注册资金
    private float registeredCapital;
    //出资方式
    private Long equity;
    //高薪技术
    private List<Long> highTechs;
    //上市公司
    private Long companyMarket;
    //股票代码
    private String sharesCode;
    //单位类型
    private Long companyType;
    //所属行业
    private List<Long> industries;
    //主营业务
    private List<Long> business;
    //业务领域
    private List<Long> businessArea;
    //细分市场
    private List<Long> segmentMarket;
    //单位优势
    private List<Long> advantages;
    //技术产品
    private Long techProduct;
    //发明专利
    private String patents;
    //实用专利
    private String utilityPatents;
    //软件著作
    private String softwares;
    //员工人数
    private Long staffNumber;
    //技术人员数
    private Long technicians;
    //办公面积
    private float officeArea;
    //自主研发技术产品
    private String products;
    //单位总机
    private String telephone;
    //传真
    private String fax;
    //网址
    private String website;
    //地址
    private String address;
    //企业荣誉
    private String honor;
    //企业简介
    private String brief;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAttention() {
        return attention;
    }

    public void setAttention(Long attention) {
        this.attention = attention;
    }

    public Long getRegion() {
        return region;
    }

    public void setRegion(Long region) {
        this.region = region;
    }

    public Long getZol() {
        return zol;
    }

    public void setZol(Long zol) {
        this.zol = zol;
    }

    public Long getUnitProperty() {
        return unitProperty;
    }

    public void setUnitProperty(Long unitProperty) {
        this.unitProperty = unitProperty;
    }

    public Date getEstablishedTime() {
        return establishedTime;
    }

    public void setEstablishedTime(Date establishedTime) {
        this.establishedTime = establishedTime;
    }

    public float getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(float registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public Long getEquity() {
        return equity;
    }

    public void setEquity(Long equity) {
        this.equity = equity;
    }

    public List<Long> getHighTechs() {
        return highTechs;
    }

    public void setHighTechs(List<Long> highTechs) {
        this.highTechs = highTechs;
    }

    public Long getCompanyMarket() {
        return companyMarket;
    }

    public void setCompanyMarket(Long companyMarket) {
        this.companyMarket = companyMarket;
    }

    public String getSharesCode() {
        return sharesCode;
    }

    public void setSharesCode(String sharesCode) {
        this.sharesCode = sharesCode;
    }

    public Long getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Long companyType) {
        this.companyType = companyType;
    }

    public List<Long> getIndustries() {
        return industries;
    }

    public void setIndustries(List<Long> industries) {
        this.industries = industries;
    }

    public List<Long> getBusiness() {
        return business;
    }

    public void setBusiness(List<Long> business) {
        this.business = business;
    }

    public List<Long> getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(List<Long> businessArea) {
        this.businessArea = businessArea;
    }

    public List<Long> getSegmentMarket() {
        return segmentMarket;
    }

    public void setSegmentMarket(List<Long> segmentMarket) {
        this.segmentMarket = segmentMarket;
    }

    public List<Long> getAdvantages() {
        return advantages;
    }

    public void setAdvantages(List<Long> advantages) {
        this.advantages = advantages;
    }

    public Long getTechProduct() {
        return techProduct;
    }

    public void setTechProduct(Long techProduct) {
        this.techProduct = techProduct;
    }

    public String getPatents() {
        return patents;
    }

    public void setPatents(String patents) {
        this.patents = patents;
    }

    public String getUtilityPatents() {
        return utilityPatents;
    }

    public void setUtilityPatents(String utilityPatents) {
        this.utilityPatents = utilityPatents;
    }

    public String getSoftwares() {
        return softwares;
    }

    public void setSoftwares(String softwares) {
        this.softwares = softwares;
    }

    public Long getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(Long staffNumber) {
        this.staffNumber = staffNumber;
    }

    public Long getTechnicians() {
        return technicians;
    }

    public void setTechnicians(Long technicians) {
        this.technicians = technicians;
    }

    public float getOfficeArea() {
        return officeArea;
    }

    public void setOfficeArea(float officeArea) {
        this.officeArea = officeArea;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHonor() {
        return honor;
    }

    public void setHonor(String honor) {
        this.honor = honor;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }
}
