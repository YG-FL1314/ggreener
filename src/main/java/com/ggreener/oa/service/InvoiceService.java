package com.ggreener.oa.service;

import com.ggreener.oa.exception.InvoiceException;
import com.ggreener.oa.mapper.InvoiceMapper;
import com.ggreener.oa.po.InvoicePO;
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
public class InvoiceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceService.class);

    @Autowired
    private InvoiceMapper invoiceMapper;

    public InvoiceVO addInvoice(InvoicePO invoice) throws InvoiceException {
        if (invoiceMapper.insert(invoice) > 0) {
            InvoiceVO result = new InvoiceVO();
            BeanUtils.copyProperties(invoice, result);
            return result;
        } else {
            throw new InvoiceException("添加发票信息失败！");
        }
    }

    public InvoiceVO getInvoice(Long invoiceId) throws InvoiceException {
        InvoiceVO result = new InvoiceVO();
        InvoicePO invoice = invoiceMapper.get(invoiceId);
        if (null != invoice) {
            BeanUtils.copyProperties(invoice, result);
        } else {
            throw new InvoiceException("发票信息不存在！");
        }
        return result;
    }

    public InvoiceVO updateInvoice(InvoicePO invoice) throws InvoiceException {
        if (invoiceMapper.update(invoice) > 0) {
            InvoiceVO result = new InvoiceVO();
            BeanUtils.copyProperties(invoice, result);
            return result;
        } else {
            throw new InvoiceException("更新发票信息失败！");
        }
    }

    public List<InvoiceVO> getInvoiceByCompanyId(Long companyId) {
        List<InvoicePO> list = invoiceMapper.selectByCompanyId(companyId);
        List<InvoiceVO> result = new ArrayList<>();
        if (null != list && list.size() > 0) {
            for (InvoicePO invoicePO : list) {
                InvoiceVO tmp = new InvoiceVO();
                BeanUtils.copyProperties(invoicePO, tmp);
                result.add(tmp);
            }
        }
        return result;
    }

    public void deleteInvoice(Long id, String userId) throws InvoiceException {
        if (invoiceMapper.delete(id, userId, new Date()) <= 0) {
            throw new InvoiceException("删除发票信息失败！");
        }
    }

}
