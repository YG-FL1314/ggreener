package com.ggreener.oa.service;

import com.alibaba.fastjson.JSONObject;
import com.ggreener.oa.exception.CompanyException;
import com.ggreener.oa.mapper.*;
import com.ggreener.oa.po.*;
import com.ggreener.oa.util.Constants;
import com.ggreener.oa.vo.CompanyListVO;
import com.ggreener.oa.vo.CompanyVO;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RequireMapper requireMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private ProjectCompanyMapper projectCompanyMapper;

    @Autowired
    private TagMapper tagMapper;

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

    public JSONObject list(String name, List<Long> tags, List<Long> requireIds, List<Long> memberStatus, Long cooperationInfo,
                           Long start, Long limit) throws CompanyException {
        JSONObject result = new JSONObject();
        List<CompanyListVO> list = new ArrayList<>();
        List<Long> parentIds = new ArrayList<>();
        Long count = 0L;
        List<Long> companyIds = new ArrayList<>();
        //判断本次请求是否含有会员tag并过滤出来
        List<Long> memberTags = tagMapper.list(Long.valueOf(Constants.MEMBER_FLAG)).stream()
                .map(s -> s.getId()).collect(Collectors.toList());
        List<Long> thisMemberTags = new ArrayList<>();
        if (!CollectionUtils.isEmpty(tags)) {
            memberTags.forEach(s -> {
                if (tags.contains(s)) {
                    thisMemberTags.add(s);
                }
            });
            tags.removeAll(thisMemberTags);
        }

        if (null != tags && tags.size() > 0) {
            parentIds = addParentIds(tags);
            Map<Long, List<CompanyTagsPO>> mapIds = companyTagsMapper.selectCompanyByTags(tags).stream()
                    .collect(Collectors.groupingBy(CompanyTagsPO::getCompanyId));
            for (Long k : mapIds.keySet()) {
                List<CompanyTagsPO> v = mapIds.get(k);
                if (v.size() >= tags.size())
                    companyIds.add(k);
            }
        } else {
            List<Long> companyTmpIds = companyMapper.list(name);
            companyIds.addAll(companyTmpIds);
        }

        if (parentIds.size() > 0 && companyIds.size() > 0) {
            int parentLength = parentIds.size();
            Map<Long, List<TagDetailPO>> mapIds = companyTagsMapper.selectCompanyByParentIds(companyIds, parentIds).stream()
                    .collect(Collectors.groupingBy(TagDetailPO::getCompanyId));
            companyIds.clear();
            for (Long k : mapIds.keySet()) {
                List<TagDetailPO> v = mapIds.get(k);
                Set<Long> set = new HashSet<>();
                for (TagDetailPO tmp : v) {
                    if (tmp.getTagId() == Constants.NO_MEMBER_FLAG) {
                        continue;
                    }
                    if (tmp.getTagId() == Constants.NO_HIGH_TECHNOLOGY_FLAG) {
                        continue;
                    }
                    set.add(tmp.getParentId());
                }
                if (set.size() >= parentLength) {
                    companyIds.add(k);
                }
            }
        }

        //filter by member
        if (!CollectionUtils.isEmpty(thisMemberTags) && !CollectionUtils.isEmpty(companyIds)) {
            List<TagDetailPO> companys = companyTagsMapper.selectCompanyByTagIds(companyIds, thisMemberTags);
            List<Long> newCompanyIds = new ArrayList<>();
            companys.forEach(company -> {
                newCompanyIds.add(company.getCompanyId());
            });
            companyIds = newCompanyIds;
        }

        //filter by memberStatus
        if (!CollectionUtils.isEmpty(memberStatus) && !CollectionUtils.isEmpty(companyIds)) {
            List<MemberPO> members = memberMapper.listByCompanyIds(companyIds, memberStatus);
            List<Long> newCompanyIds = new ArrayList<>();
            members.forEach(member -> {
                newCompanyIds.add(member.getCompanyId());
            });
            companyIds = newCompanyIds;
        }
        //filter by cooperationInfo
        if (cooperationInfo != null && cooperationInfo > 0 && !CollectionUtils.isEmpty(companyIds)) {
            List<Long> newCompanyIds = projectCompanyMapper.selectByCompanyIds(companyIds);
            if (Constants.HAVE_COOPERATION == cooperationInfo) {
                companyIds = newCompanyIds;
            } else {
                companyIds.removeAll(newCompanyIds);
            }
        }

        if (companyIds.size() > 0 && null != requireIds && requireIds.size() > 0) {
            List<RequirePO> requires = requireMapper.listByRequireId(requireIds, companyIds);
            companyIds.clear();
            if (requires != null && requires.size() > 0) {
                Map<Long, List<RequirePO>> map = requires.stream().collect(Collectors.groupingBy(RequirePO::getCompanyId));
                for (Long k : map.keySet()) {
                    List<RequirePO> v = map.get(k);
                    if (v.size() >= requireIds.size()) {
                        companyIds.add(k);
                    }
                }
            }
        }

        if (companyIds.size() > 0 ) {
            List<CompanyOverviewPO> companies = companyMapper.selectByIds(name, companyIds, start, limit);
            count = companyMapper.countByIds(name, companyIds);
            Map<Long, List<TagDetailPO>> map = companyTagsMapper.listByCompanyIds(companyIds).stream()
                    .collect(Collectors.groupingBy(TagDetailPO::getCompanyId));
            Map<String, UserPO> userMap = userMapper.list(Constants.NORMAL_ROLE).stream()
                    .collect(Collectors.toMap(k -> k.getUuid(), v -> v));
            for (CompanyOverviewPO companyOverview : companies) {
                CompanyListVO company = new CompanyListVO();
                company.setCompanyId(companyOverview.getId());
                company.setName(companyOverview.getName());
                company.setMemberCode(companyOverview.getMemberCode());
                company.setMember(companyOverview.getMemberName());
                company.setCreateTime(companyOverview.getEstablishedTime());
                company.setRegister(companyOverview.getRegisteredCapital());
                company.setUpdateTime(companyOverview.getUpdateTime());
                if (userMap.containsKey(companyOverview.getUpdateUser())) {
                    company.setUpdateUser(userMap.get(companyOverview.getUpdateUser()).getNickName());
                } else {
                    company.setUpdateUser("");
                }

                if (map.containsKey(company.getCompanyId())) {
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
                                case Constants.COMPANY_TYPE_FLAG:
                                    company.setCompanyType(tagDetail.getName());
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
                }
                list.add(company);
            }
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
                case Constants.COOPERATION_FLAG:
                    if (companyVO.getCooperation() != null) {
                        companyVO.getCooperation().add(tagDetail.getTagId());
                    } else {
                        List<Long> result = new ArrayList<>();
                        result.add(tagDetail.getTagId());
                        companyVO.setCooperation(result);
                    }
                    break;
                case Constants.CREDIT_FLAG:
                    companyVO.setCredit(tagDetail.getTagId());
                    break;
                case Constants.NORMAL_SERVICE_FLAG:
                    companyVO.setNormalService(tagDetail.getTagId());
                    break;
                case Constants.ABUTMENT_FLAG:
                    companyVO.setAbutment(tagDetail.getTagId());
                case Constants.TECH_CASE_FLAG:
                    companyVO.setTechCase(tagDetail.getTagId());
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
        CompanyPO src = companyMapper.selectByCompanyId(company.getId());
        if (company.getId() == null || src == null) {
            throw new CompanyException("公司不存在！");
        }
        BeanUtils.copyProperties(company, companyPO);
        companyPO.setEstablishedTime(src.getEstablishedTime());
        companyPO.setUpdateTime(new Date());
        companyPO.setUpdateUser(userId);
        companyMapper.update(companyPO);
        return company;
    }

    public CompanyPO updateCompanyBaseInfo(CompanyPO companyPO, String userId) throws CompanyException {
        if (companyPO.getId() == null ||
                companyMapper.selectByCompanyId(companyPO.getId()) == null) {
            throw new CompanyException("公司不存在！");
        }
        companyPO.setUpdateTime(new Date());
        companyPO.setUpdateUser(userId);
        if (companyMapper.update(companyPO) > 0) {
            if (!CollectionUtils.isEmpty(companyPO.getTags())) {
                //删除除了会员关系的company_tag相关信息
                companyTagsMapper.delete(companyPO.getId(), new Long(Constants.MEMBER_FLAG));
                companyTagsMapper.batchInsert(companyPO.getId(), companyPO.getTags(), new Date());
            }
        }
        return companyPO;
    }

    public void delete(Long companyId, String userId) throws CompanyException {
        if (companyMapper.delete(companyId, userId, new Date()) <= 0) {
            throw new CompanyException("删除企业失败!");
        }
    }

    private List<Long> addParentIds(List<Long> tags) {
        List<Long> result = new ArrayList<>();
        if (tags.contains(new Long(Constants.MEMBER_FLAG))) {
            result.add(new Long(Constants.MEMBER_FLAG));
            tags.remove(new Long(Constants.MEMBER_FLAG));
        }
        if (tags.contains(new Long(Constants.CONCERN_LEVEL_FLAG))) {
            result.add(new Long(Constants.CONCERN_LEVEL_FLAG));
            tags.remove(new Long(Constants.CONCERN_LEVEL_FLAG));
        }
        if (tags.contains(new Long(Constants.ZOL_FLAG))) {
            result.add(new Long(Constants.ZOL_FLAG));
            tags.remove(new Long(Constants.ZOL_FLAG));
        }
        if (tags.contains(new Long(Constants.COMPANY_MARKET_FLAG))) {
            result.add(new Long(Constants.COMPANY_MARKET_FLAG));
            tags.remove(new Long(Constants.COMPANY_MARKET_FLAG));
        }
        if (tags.contains(new Long(Constants.HIGH_TECHNOLOGY_FLAG))) {
            result.add(new Long(Constants.HIGH_TECHNOLOGY_FLAG));
            tags.remove(new Long(Constants.HIGH_TECHNOLOGY_FLAG));
        }
        if (tags.contains(new Long(Constants.COOPERATION_FLAG))) {
            result.add(new Long(Constants.COOPERATION_FLAG));
            tags.remove(new Long(Constants.COOPERATION_FLAG));
        }
        return result;
    }
}
