package com.ggreener.oa.service;

import com.ggreener.oa.exception.RequireException;
import com.ggreener.oa.mapper.RequireMapper;
import com.ggreener.oa.po.RequirePO;
import com.ggreener.oa.po.TagDetailPO;
import com.ggreener.oa.util.Constants;
import com.ggreener.oa.vo.RequireVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lifu on 2018/9/30.
 *
 *
 */
@Service
public class RequireService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequireService.class);

    @Autowired
    private RequireMapper requireMapper;

    public void addRequires(Long companyId, List<Long> tags, String userId) throws RequireException {
        if (requireMapper.batchInsert(companyId, tags, userId, new Date()) > 0) {

        } else {
            throw new RequireException("添加需求信息失败！");
        }
    }

    public void updateRequires(Long companyId, List<Long> tags, String userId) throws RequireException {
        if (requireMapper.delete(companyId) >= 0) {
            if (!CollectionUtils.isEmpty(tags)) {
                if (requireMapper.batchInsert(companyId, tags, userId, new Date()) > 0) {

                } else {
                    throw new RequireException("添加需求信息失败！");
                }
            }
        } else {
            throw new RequireException("删除需求信息失败！");
        }
    }

    public RequireVO getRequires(Long companyId) {
        RequireVO result = new RequireVO();
        result.setCompanyId(companyId);
        List<TagDetailPO> requires = requireMapper.selectByCompanyId(companyId);
        if (null != requires) {
            for (TagDetailPO require : requires) {
                switch(require.getParentId().intValue()) {
                    case Constants.REQUIRE_BRAND_FLAG:
                        if (result.getBrand() != null) {
                            result.getBrand().add(require.getTagId());
                        } else {
                            List<Long> tags = new ArrayList<>();
                            tags.add(require.getTagId());
                            result.setBrand(tags);
                        }
                        break;
                    case Constants.REQUIRE_RESOURCE_FLAG:
                        if (result.getResources() != null) {
                            result.getResources().add(require.getTagId());
                        } else {
                            List<Long> tags = new ArrayList<>();
                            tags.add(require.getTagId());
                            result.setResources(tags);
                        }
                        break;
                    case Constants.REQUIRE_FINANCE_FLAG:
                        if (result.getFinances() != null) {
                            result.getFinances().add(require.getTagId());
                        } else {
                            List<Long> tags = new ArrayList<>();
                            tags.add(require.getTagId());
                            result.setFinances(tags);
                        }
                        break;
                    case Constants.REQUIRE_ABILITY_FLAG:
                        if (result.getAbility() != null) {
                            result.getAbility().add(require.getTagId());
                        } else {
                            List<Long> tags = new ArrayList<>();
                            tags.add(require.getTagId());
                            result.setAbility(tags);
                        }
                        break;
                    case Constants.REQUIRE_INTERNATION_FLAG:
                        if (result.getInternations() != null) {
                            result.getInternations().add(require.getTagId());
                        } else {
                            List<Long> tags = new ArrayList<>();
                            tags.add(require.getTagId());
                            result.setInternations(tags);
                        }
                        break;
                    case Constants.REQUIRE_STANDARD_FLAG:
                        if (result.getStandards() != null) {
                            result.getStandards().add(require.getTagId());
                        } else {
                            List<Long> tags = new ArrayList<>();
                            tags.add(require.getTagId());
                            result.setStandards(tags);
                        }
                        break;
                    case Constants.REQUIRE_INDENTIFACTION_FLAG:
                        if (result.getIdentify() != null) {
                            result.getIdentify().add(require.getTagId());
                        } else {
                            List<Long> tags = new ArrayList<>();
                            tags.add(require.getTagId());
                            result.setIdentify(tags);
                        }
                        break;
                    case Constants.REQUIRE_CONSULT_FLAG:
                        if (result.getConsult() != null) {
                            result.getConsult().add(require.getTagId());
                        } else {
                            List<Long> tags = new ArrayList<>();
                            tags.add(require.getTagId());
                            result.setConsult(tags);
                        }
                        break;
                    case Constants.REQUIRE_OTHER_FLAG:
                        if (result.getOthers() != null) {
                            result.getOthers().add(require.getTagId());
                        } else {
                            List<Long> tags = new ArrayList<>();
                            tags.add(require.getTagId());
                            result.setOthers(tags);
                        }
                        break;
                }
            }
        }
        return result;
    }

    public void deleteRequire(Long companyId) throws RequireException {
        if (requireMapper.delete(companyId) < 0) {
            throw new RequireException("删除需求信息失败！");
        }
    }
}
