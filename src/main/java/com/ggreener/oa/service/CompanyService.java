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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
                for (Long tag : tags) {
                    CompanyTagsPO entity = new CompanyTagsPO();
                    entity.setCompanyId(company.getId());
                    entity.setTagId(tag);
                    entity.setTime(date);
                    list.add(entity);
                }
                companyTagsMapper.batchInsert(list);
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
        return companyVO;
    }

    public boolean exist(String name) {
        CompanyPO companyPO = companyMapper.selectByName(name);
        if (null == companyPO) {
            return false;
        }
        return true;
    }
}
