package com.ggreener.oa.service;

import com.ggreener.oa.exception.BusinessDataException;
import com.ggreener.oa.exception.InvoiceException;
import com.ggreener.oa.mapper.BusinessDataMapper;
import com.ggreener.oa.po.BusinessDataPO;
import com.ggreener.oa.po.InvoicePO;
import com.ggreener.oa.vo.BusinessDataVO;
import com.ggreener.oa.vo.InvoiceVO;
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
public class BusinessDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessDataService.class);

    @Autowired
    private BusinessDataMapper businessDataMapper;

    public BusinessDataVO addBusinessData(BusinessDataPO businessDataPO) throws BusinessDataException {
        if (businessDataMapper.insert(businessDataPO) > 0) {
            BusinessDataVO result = new BusinessDataVO();
            BeanUtils.copyProperties(businessDataPO, result);
            return result;
        } else {
            throw new BusinessDataException("添加经营信息失败！");
        }
    }

    public List<BusinessDataVO> listBusinessDatas(Long companyId) {
        List<BusinessDataVO> result = new ArrayList<>();
        List<BusinessDataPO> businesses = businessDataMapper.selectByCompanyId(companyId);
        if (null != businesses) {
            for (BusinessDataPO business : businesses) {
                BusinessDataVO vo = new BusinessDataVO();
                BeanUtils.copyProperties(business, vo);
                result.add(vo);
            }
        }
        return result;
    }

    public BusinessDataVO updateBusinessData(BusinessDataPO businessDataPO) throws BusinessDataException {
        if (businessDataMapper.update(businessDataPO) > 0) {
            BusinessDataVO result = new BusinessDataVO();
            BeanUtils.copyProperties(businessDataPO, result);
            return result;
        } else {
            throw new BusinessDataException("更新经营信息失败！");
        }
    }

    public void deleteBusinessData(Long id, String userId) throws BusinessDataException {
        if (businessDataMapper.delete(id, userId, new Date()) <= 0) {
            throw new BusinessDataException("删除经营信息失败！");
        }
    }

    public BusinessDataVO getBussinessData(Long businessDataId) throws BusinessDataException {
        BusinessDataVO result = new BusinessDataVO();
        BusinessDataPO chat = businessDataMapper.get(businessDataId);
        if (null != chat) {
            BeanUtils.copyProperties(chat, result);
        } else {
            throw new BusinessDataException("经营信息不存在！");
        }
        return result;
    }
}
