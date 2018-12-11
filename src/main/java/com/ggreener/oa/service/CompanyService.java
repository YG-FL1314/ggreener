package com.ggreener.oa.service;

import com.alibaba.fastjson.JSONObject;
import com.ggreener.oa.exception.CompanyException;
import com.ggreener.oa.mapper.CompanyMapper;
import com.ggreener.oa.mapper.CompanyTagsMapper;
import com.ggreener.oa.po.*;
import com.ggreener.oa.util.Constants;
import com.ggreener.oa.vo.CompanyListVO;
import com.ggreener.oa.vo.CompanyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by lifu on 2018/10/8.
 * <p>
 * XXX
 */
@Service
public class CompanyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private CompanyTagsMapper companyTagsMapper;

    public CompanyVO addCompany(CompanyPO company, List<Long> tags) throws CompanyException {
        CompanyVO companyVO = new CompanyVO();
        if (companyMapper.insert(company) > 0) {
            BeanUtils.copyProperties(company, companyVO);
            Date date = new Date();
            List<CompanyTagsPO> list = new ArrayList<>();
            if (tags.size() > 0) {
                companyTagsMapper.batchInsert(company.getId(), tags, date);
            }
        } else {
            throw new CompanyException("添加公司失败！");
        }
        return companyVO;
    }

    public JSONObject list(String name, List<Long> tags, Long start, Long limit) throws CompanyException {
        JSONObject result = new JSONObject();
        List<CompanyListVO> list = new ArrayList<>();
        Long count = 0L;
        List<Long> companyIds = new ArrayList<>();
        Map<Long, List<CompanyTagsPO>> mapIds = companyTagsMapper.selectCompanyByTags(tags).stream()
                .collect(Collectors.groupingBy(CompanyTagsPO::getCompanyId));
        if (null != tags) {
            mapIds.forEach((k, v) -> {
                if (v.size() >= tags.size()) companyIds.add(k);
            });
        } else {
            mapIds.forEach((k, v) -> {
                companyIds.add(k);
            });
        }

        List<CompanyOverviewPO> companies = companyMapper.selectByIds(name, companyIds, start, limit);
        count = companyMapper.countByIds(name, companyIds);
        Map<Long, List<TagDetailPO>> map = companyTagsMapper.listByCompanyIds(companyIds).stream()
                .collect(Collectors.groupingBy(TagDetailPO::getCompanyId));
        for (CompanyOverviewPO companyOverview : companies) {
            CompanyListVO company = new CompanyListVO();
            company.setCompanyId(companyOverview.getId());
            company.setName(companyOverview.getName());
            company.setMemberCode(companyOverview.getMemberCode());
            company.setMember(companyOverview.getMemberName());
            company.setCreateTime(companyOverview.getEstablishedTime());
            company.setRegister(companyOverview.getRegisteredCapital());
            List<TagDetailPO> tagDetails = map.get(company.getCompanyId());
            if (tagDetails != null || tagDetails.size() > 0) {
                for (TagDetailPO tagDetail : tagDetails) {
                    switch(tagDetail.getParentId().intValue()) {
                        case Constants.CONCERN_LEVEL_FLAG:
                            company.setAttention(tagDetail.getName());
                            break;
                        case Constants.AREA_FLAG:
                            company.setRegion(tagDetail.getName());
                            break;
                        case Constants.UNIT_PROPERTIES_FLAG:
                            if (company.getUnitProperty() != null) {
                                StringBuilder sb = new StringBuilder(company.getUnitProperty());
                                sb.append(",");
                                sb.append(tagDetail.getName());
                                company.setUnitProperty(sb.toString());
                            } else {
                                company.setUnitProperty(tagDetail.getName());
                            }
                            break;
                        case Constants.INDUSTRIES_FLAG:
                            if (company.getIndustry() != null) {
                                StringBuilder sb = new StringBuilder(company.getIndustry());
                                sb.append(",");
                                sb.append(tagDetail.getName());
                                company.setIndustry(sb.toString());
                            } else {
                                company.setIndustry(tagDetail.getName());
                            }
                            break;
                        case Constants.BUSINESS_FLAG:
                            if (company.getBusiness() != null) {
                                StringBuilder sb = new StringBuilder(company.getBusiness());
                                sb.append(",");
                                sb.append(tagDetail.getName());
                                company.setBusiness(sb.toString());
                            } else {
                                company.setBusiness(tagDetail.getName());
                            }
                            break;
                        case Constants.BUSINESS_AREA_FLAG:
                            if (company.getBusinessArea() != null) {
                                StringBuilder sb = new StringBuilder(company.getBusinessArea());
                                sb.append(",");
                                sb.append(tagDetail.getName());
                                company.setBusinessArea(sb.toString());
                            } else {
                                company.setBusinessArea(tagDetail.getName());
                            }
                            break;
                        case Constants.ADVANTAGES_FLAG:
                            if (company.getAdvantage() != null) {
                                StringBuilder sb = new StringBuilder(company.getAdvantage());
                                sb.append(",");
                                sb.append(tagDetail.getName());
                                company.setAdvantage(sb.toString());
                            } else {
                                company.setAdvantage(tagDetail.getName());
                            }
                            break;
                    }
                }
            }
            list.add(company);
        }
        result.put("count", count);
        result.put("list", list);
        return result;
    }

    public CompanyVO get(Long companyId) throws CompanyException {
        CompanyVO companyVO = new CompanyVO();
        CompanyPO companyPO = companyMapper.selectByCompanyId(companyId);
        if (null == companyPO) {
            throw new CompanyException("公司不存在！");
        }
        BeanUtils.copyProperties(companyPO, companyVO);
        List<Long> list = new ArrayList<>();
        list.add(companyId);
        List<TagDetailPO> tags = companyTagsMapper.listByCompanyIds(list);
        for (TagDetailPO tagDetail : tags) {
            switch(tagDetail.getParentId().intValue()) {
                case Constants.CONCERN_LEVEL_FLAG:
                    companyVO.setAttention(tagDetail.getTagId());
                    break;
                case Constants.AREA_FLAG:
                    companyVO.setRegion(tagDetail.getTagId());
                    break;
                case Constants.ZOL_FLAG:
                    companyVO.setZol(tagDetail.getTagId());
                    break;
                case Constants.UNIT_PROPERTIES_FLAG:
                    companyVO.setUnitProperty(tagDetail.getTagId());
                    break;
                case Constants.EQUITY_FLAG:
                    companyVO.setEquity(tagDetail.getTagId());
                    break;
                case Constants.HIGH_TECHNOLOGY_FLAG:
                    if (companyVO.getHighTechs() != null) {
                        companyVO.getHighTechs().add(tagDetail.getTagId());
                    } else {
                        List<Long> result = new ArrayList<>();
                        result.add(tagDetail.getTagId());
                        companyVO.setHighTechs(result);
                    }
                    break;
                case Constants.TECHNOLOGY_PRODUCT_FLAG:
                    companyVO.setTechProduct(tagDetail.getTagId());
                    break;
                case Constants.COMPANY_MARKET_FLAG:
                    companyVO.setCompanyMarket(tagDetail.getTagId());
                    break;
                case Constants.COMPANY_TYPE_FLAG:
                    companyVO.setCompanyType(tagDetail.getTagId());
                    break;
                case Constants.INDUSTRIES_FLAG:
                    if (companyVO.getIndustries() != null) {
                        companyVO.getIndustries().add(tagDetail.getTagId());
                    } else {
                        List<Long> result = new ArrayList<>();
                        result.add(tagDetail.getTagId());
                        companyVO.setIndustries(result);
                    }
                    break;
                case Constants.BUSINESS_FLAG:
                    if (companyVO.getBusiness() != null) {
                        companyVO.getBusiness().add(tagDetail.getTagId());
                    } else {
                        List<Long> result = new ArrayList<>();
                        result.add(tagDetail.getTagId());
                        companyVO.setBusiness(result);
                    }
                    break;
                case Constants.BUSINESS_AREA_FLAG:
                    if (companyVO.getBusinessArea() != null) {
                        companyVO.getBusinessArea().add(tagDetail.getTagId());
                    } else {
                        List<Long> result = new ArrayList<>();
                        result.add(tagDetail.getTagId());
                        companyVO.setBusinessArea(result);
                    }
                    break;
                case Constants.SEGMENT_MARKET_FLAG:
                    if (companyVO.getSegmentMarket() != null) {
                        companyVO.getSegmentMarket().add(tagDetail.getTagId());
                    } else {
                        List<Long> result = new ArrayList<>();
                        result.add(tagDetail.getTagId());
                        companyVO.setSegmentMarket(result);
                    }
                    break;
                case Constants.ADVANTAGES_FLAG:
                    if (companyVO.getAdvantages() != null) {
                        companyVO.getAdvantages().add(tagDetail.getTagId());
                    } else {
                        List<Long> result = new ArrayList<>();
                        result.add(tagDetail.getTagId());
                        companyVO.setAdvantages(result);
                    }
                    break;
            }
        }
        return companyVO;
    }

    public boolean exist(String name) {
        CompanyPO companyPO = companyMapper.selectByName(name);
        if (null == companyPO) {
            return false;
        }
        return true;
    }

    public CompanyVO update(CompanyVO company, String userId) throws CompanyException {
        CompanyPO companyPO = new CompanyPO();
        if (company.getId() == null ||
                companyMapper.selectByCompanyId(company.getId()) == null) {
            throw new CompanyException("公司不存在！");
        }
        BeanUtils.copyProperties(company, companyPO);
        companyPO.setUpdateTime(new Date());
        companyPO.setUpdateUser(userId);
        if (companyMapper.update(companyPO) > 0) {
            List<Long> tags = new ArrayList<>();
            tags.add(company.getAttention());
            tags.add(company.getRegion());
            tags.add(company.getZol());
            tags.add(company.getUnitProperty());
            tags.add(company.getEquity());
            tags.addAll(company.getHighTechs());
            tags.add(company.getCompanyMarket());
            tags.add(company.getCompanyMarket());
            tags.addAll(company.getIndustries());
            tags.addAll(company.getBusiness());
            tags.addAll(company.getBusinessArea());
            tags.addAll(company.getSegmentMarket());
            tags.addAll(company.getAdvantages());
            tags.add(company.getTechProduct());
            //删除除了会员关系的company_tag相关信息
            companyTagsMapper.delete(companyPO.getId(), new Long(Constants.MEMBER_FLAG));
            companyTagsMapper.batchInsert(company.getId(), tags, new Date());
        }
        return company;
    }
}
