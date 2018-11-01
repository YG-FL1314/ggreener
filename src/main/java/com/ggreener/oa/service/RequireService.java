package com.ggreener.oa.service;

import com.ggreener.oa.exception.RequireException;
import com.ggreener.oa.mapper.RequireMapper;
import com.ggreener.oa.po.RequirePO;
import com.ggreener.oa.vo.RequireVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public RequireVO addRequire(RequirePO require) throws RequireException {
        if (requireMapper.insert(require) > 0) {
            RequireVO result = new RequireVO();
            BeanUtils.copyProperties(require, result);
            return result;
        } else {
            throw new RequireException("添加需求信息失败！");
        }
    }

    public RequireVO getRequire(Long requireId) throws RequireException {
        RequireVO result = new RequireVO();
        RequirePO require = requireMapper.get(requireId);
        if (null != require) {
            BeanUtils.copyProperties(require, result);
        } else {
            throw new RequireException("需求信息不存在！");
        }
        return result;
    }

    public void deleteRequire(Long companyId) throws RequireException {
        if (requireMapper.delete(companyId) <= 0) {
            throw new RequireException("删除需求信息失败！");
        }
    }

    public List<RequireVO> list(Long companyId) {
        List<RequirePO> list = requireMapper.selectByCompanyId(companyId);
        List<RequireVO> result = new ArrayList<>();
        if (null != list && list.size() > 0) {
            for (RequirePO requirePO : list) {
                RequireVO requireTmp = new RequireVO();
                BeanUtils.copyProperties(requirePO, requireTmp);
                result.add(requireTmp);
            }
        }
        return result;
    }
}
