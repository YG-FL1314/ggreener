package com.ggreener.oa.service;

import com.ggreener.oa.exception.CompanyException;
import com.ggreener.oa.mapper.CompanyMapper;
import com.ggreener.oa.mapper.CompanyTagsMapper;
import com.ggreener.oa.po.CompanyPO;
import com.ggreener.oa.po.CompanyTagsPO;
import com.ggreener.oa.po.TagPO;
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

    public CompanyVO list(CompanyPO company, List<Long> tags) throws CompanyException {
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

    public CompanyVO get(Long companyId) throws CompanyException {
        CompanyVO companyVO = new CompanyVO();
        CompanyPO companyPO = companyMapper.selectByCompanyId(companyId);
        if (null == companyVO) {
            throw new CompanyException("公司不存在！");
        }

        List<TagPO> tags = companyTagsMapper.selectTagsByCompanyId(companyId);
        if (null != tags && tags.size() > 0) {
            Map<Long, List<TagPO>> map = tags.stream().collect(Collectors.groupingBy(TagPO::getParentId));
        }
        return companyVO;
    }

}
