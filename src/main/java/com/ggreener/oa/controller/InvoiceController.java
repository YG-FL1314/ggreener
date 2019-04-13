package com.ggreener.oa.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ggreener.oa.exception.SessionException;
import com.ggreener.oa.po.InvoicePO;
import com.ggreener.oa.service.CompanyService;
import com.ggreener.oa.service.InvoiceService;
import com.ggreener.oa.service.UserService;
import com.ggreener.oa.util.Constants;
import com.ggreener.oa.vo.CompanyVO;
import com.ggreener.oa.vo.InvoiceVO;
import com.ggreener.oa.vo.ResponseVO;
import com.ggreener.oa.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by lifu on 2018-09-29
 *
 */
@RestController
@RequestMapping(value = {"invoice"})
public class InvoiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private CompanyService companyService;

    @PostMapping(value = "add", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE },
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object addInvoice(@RequestBody JSONObject json, HttpServletRequest request) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                InvoicePO invoice = JSON.parseObject(json.toString(), new TypeReference<InvoicePO>(){});
                Date date = new Date();
                invoice.setCreateTime(date);
                invoice.setUpdateTime(date);
                invoice.setCreateUser(user.getUuid());
                invoice.setUpdateUser(user.getUuid());
                companyService.get(invoice.getCompanyId());
                invoice.setStatus(Constants.STATUS_NORMAL);
                resp.setObj(invoiceService.addInvoice(invoice));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("添加发票信息成功！");
                CompanyVO company = new CompanyVO();
                company.setId(invoice.getCompanyId());
                companyService.update(company, user.getUuid());
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("InvoiceController==>addInvoice:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("InvoiceController==>addInvoice:添加发票信息失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @PutMapping(value = "update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object updateInvoice(@RequestBody JSONObject json, HttpServletRequest request) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                InvoicePO invoice = JSON.parseObject(json.toString(), new TypeReference<InvoicePO>(){});
                invoice.setUpdateTime(new Date());
                invoice.setUpdateUser(user.getUuid());
                InvoiceVO vo = invoiceService.getInvoice(invoice.getId());
                resp.setObj(invoiceService.updateInvoice(invoice));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("更新发票信息成功！");
                CompanyVO company = new CompanyVO();
                company.setId(vo.getCompanyId());
                companyService.update(company, user.getUuid());
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("InvoiceController==>updateInvoice:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("InvoiceController==>updateInvoice:更新发票信息失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @DeleteMapping(value = "delete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object deleteInvoice(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                InvoiceVO vo = invoiceService.getInvoice(id);
                invoiceService.deleteInvoice(id, user.getUuid());
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("删除发票信息成功！");
                CompanyVO company = new CompanyVO();
                company.setId(vo.getCompanyId());
                companyService.update(company, user.getUuid());
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("InvoiceController==>deleteInvoice:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("InvoiceController==>deleteInvoice:删除发票信息失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object listInvoices(HttpServletRequest request,@RequestParam(value = "companyId", required = true) Long companyId) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                resp.setObj(invoiceService.getInvoiceByCompanyId(companyId));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
            } else {
                resp.setStatus(Constants.RESPONSE_REDIRECT);
                resp.setMessage("./login.html");
            }
        } catch (SessionException e) {
            LOGGER.error("InvoiceController==>listInvoices:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("InvoiceController==>listInvoices:获取发票信息列表失败！,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }
}
