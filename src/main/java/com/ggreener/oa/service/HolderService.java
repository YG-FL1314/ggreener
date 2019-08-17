package com.ggreener.oa.service;

import com.ggreener.oa.exception.HolderException;
import com.ggreener.oa.mapper.HolderMapper;
import com.ggreener.oa.po.HolderPO;
import com.ggreener.oa.vo.HolderVO;
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
public class HolderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HolderService.class);

    @Autowired
    private HolderMapper holderMapper;

    public HolderVO addHolder(HolderPO holder) throws HolderException {
        if (holderMapper.insert(holder) > 0) {
            HolderVO result = new HolderVO();
            BeanUtils.copyProperties(holder, result);
            return result;
        } else {
            throw new HolderException("添加股东信息失败！");
        }
    }

    public HolderVO getHolder(Long holderId) throws HolderException {
        HolderVO result = new HolderVO();
        HolderPO holder = holderMapper.get(holderId);
        if (null != holder) {
            BeanUtils.copyProperties(holder, result);
        } else {
            throw new HolderException("股东信息不存在！");
        }
        return result;
    }

    public HolderVO updateHolder(HolderPO holder) throws HolderException {
        if (holderMapper.update(holder) > 0) {
            HolderVO result = new HolderVO();
            BeanUtils.copyProperties(holder, result);
            return result;
        } else {
            throw new HolderException("更新股东信息失败！");
        }
    }

    public void deleteHolder(Long id, String userId) throws HolderException {
        if (holderMapper.delete(id, userId, new Date()) <= 0) {
            throw new HolderException("删除股东信息失败！");
        }
    }

    public List<HolderVO> list(Long companyId) {
        List<HolderPO> list = holderMapper.selectByCompanyId(companyId);
        List<HolderVO> result = new ArrayList<>();
        if (null != list && list.size() > 0) {
            for (HolderPO holderPO : list) {
                HolderVO holderTmp = new HolderVO();
                BeanUtils.copyProperties(holderPO, holderTmp);
                result.add(holderTmp);
            }
        }
        return result;
    }
}
