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
@Data
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
    private Float registeredCapital;
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
    //合作单位
    private List<Long> cooperation;
    //技术产品
    private Long techProduct;
    //实用专利
    private String utilityPatents;
    //实用专利
    private Long utilityPatentsTag;
    //软件著作
    private String softwares;
    //员工人数
    private Long staffNumber;
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

    private Float totalAssets;
    private Float income;
    private Float profit;
    private Long totalProjects;
    private Long credit;

    private Long normalService;
    private Long abutment;
    private Long techCase;
}
