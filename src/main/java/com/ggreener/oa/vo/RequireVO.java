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
public class RequireVO implements Serializable {
    private static final long serialVersionUID = -119392608697125465L;
    private Long companyId;
    private List<Long> brand;
    private List<Long> resources;
    private List<Long> finances;
    private List<Long> ability;
    private List<Long> internations;
    private List<Long> standards;
    private List<Long> identify;
    private List<Long> consult;
    private List<Long> others;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public List<Long> getBrand() {
        return brand;
    }

    public void setBrand(List<Long> brand) {
        this.brand = brand;
    }

    public List<Long> getResources() {
        return resources;
    }

    public void setResources(List<Long> resources) {
        this.resources = resources;
    }

    public List<Long> getFinances() {
        return finances;
    }

    public void setFinances(List<Long> finances) {
        this.finances = finances;
    }

    public List<Long> getAbility() {
        return ability;
    }

    public void setAbility(List<Long> ability) {
        this.ability = ability;
    }

    public List<Long> getInternations() {
        return internations;
    }

    public void setInternations(List<Long> internations) {
        this.internations = internations;
    }

    public List<Long> getStandards() {
        return standards;
    }

    public void setStandards(List<Long> standards) {
        this.standards = standards;
    }

    public List<Long> getIdentify() {
        return identify;
    }

    public void setIdentify(List<Long> identify) {
        this.identify = identify;
    }

    public List<Long> getConsult() {
        return consult;
    }

    public void setConsult(List<Long> consult) {
        this.consult = consult;
    }

    public List<Long> getOthers() {
        return others;
    }

    public void setOthers(List<Long> others) {
        this.others = others;
    }
}
