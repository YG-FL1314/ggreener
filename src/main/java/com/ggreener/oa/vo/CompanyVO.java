package com.ggreener.oa.vo;

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
    private Long id;
    private Long level;
    private Long area;
    private Long zol;
    private Long contribution;
    private Long highTechs;
    private Long comapnyMarket;
    private Long companyType;
    private List<Long> industries;
    private List<Long> business;
    private List<Long> domains;
    private List<Long> markets;
    private Long products;
    private List<Long> advantages;
    private String name;
    private Date establishedTime;
    private float registeredCapital;
    private String sharesCode;
    private String patents;
    private String utilityPatents;
    private String softwares;
    private float officeArea;
    private Long staffNumber;
    private Long technicians;
    private String telephone;
    private String fax;
    private String website;
    private String address;

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

    public String getSharesCode() {
        return sharesCode;
    }

    public void setSharesCode(String sharesCode) {
        this.sharesCode = sharesCode;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public Long getZol() {
        return zol;
    }

    public void setZol(Long zol) {
        this.zol = zol;
    }

    public Long getContribution() {
        return contribution;
    }

    public void setContribution(Long contribution) {
        this.contribution = contribution;
    }

    public Long getHighTechs() {
        return highTechs;
    }

    public void setHighTechs(Long highTechs) {
        this.highTechs = highTechs;
    }

    public Long getComapnyMarket() {
        return comapnyMarket;
    }

    public void setComapnyMarket(Long comapnyMarket) {
        this.comapnyMarket = comapnyMarket;
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

    public List<Long> getDomains() {
        return domains;
    }

    public void setDomains(List<Long> domains) {
        this.domains = domains;
    }

    public List<Long> getMarkets() {
        return markets;
    }

    public void setMarkets(List<Long> markets) {
        this.markets = markets;
    }

    public Long getProducts() {
        return products;
    }

    public void setProducts(Long products) {
        this.products = products;
    }

    public List<Long> getAdvantages() {
        return advantages;
    }

    public void setAdvantages(List<Long> advantages) {
        this.advantages = advantages;
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

    public float getOfficeArea() {
        return officeArea;
    }

    public void setOfficeArea(float officeArea) {
        this.officeArea = officeArea;
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
}
