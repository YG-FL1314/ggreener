package com.ggreener.oa.controller;

import com.alibaba.fastjson.JSONObject;
import com.ggreener.oa.exception.SessionException;
import com.ggreener.oa.po.ContactPO;
import com.ggreener.oa.service.CompanyService;
import com.ggreener.oa.service.ContactService;
import com.ggreener.oa.service.UserService;
import com.ggreener.oa.util.Constants;
import com.ggreener.oa.util.PasswordUtil;
import com.ggreener.oa.vo.ResponseVO;
import com.ggreener.oa.vo.UserVO;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by lifu on 2018-09-29
 *
 */
@RestController
@RequestMapping(value = {"contact"})
public class ContactController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private CompanyService companyService;

    @PostMapping(value = "add", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE },
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object addContact(@RequestBody JSONObject json, HttpServletRequest request) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                ContactPO contact = new ContactPO();
                Date date = new Date();
                contact.setCreateTime(date);
                contact.setUpdateTime(date);
                contact.setCreateUser(user.getUuid());
                contact.setUpdateUser(user.getUuid());
                contact.setName(json.getString("name"));
                contact.setCompanyId(json.getLong("companyId"));
                companyService.get(contact.getCompanyId());
                contact.setDutyId(json.getLong("dutyId"));
                contact.setMail(json.getString("mail"));
                contact.setTelephone(json.getString("telephone"));
                contact.setQq(json.getString("qq"));
                contact.setWeixin(json.getString("weixin"));
                contact.setRemark(json.getString("remark"));
                contact.setPhone(json.getString("phone"));
                contact.setStatus(Constants.STATUS_NORMAL);
                resp.setObj(contactService.addContact(contact));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("添加联系人成功！");
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("ContactController==>addContact:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("ContactController==>addContact:添加联系人失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @PutMapping(value = "update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object updateContact(@RequestBody JSONObject json, HttpServletRequest request) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                ContactPO contact = new ContactPO();
                contact.setUpdateTime(new Date());
                contact.setUpdateUser(user.getUuid());
                contact.setId(json.getLong("id"));
                contactService.getContact(contact.getId());
                contact.setName(json.getString("name"));
                contact.setDutyId(json.getLong("dutyId"));
                contact.setMail(json.getString("mail"));
                contact.setTelephone(json.getString("telephone"));
                contact.setQq(json.getString("qq"));
                contact.setWeixin(json.getString("weixin"));
                contact.setRemark(json.getString("remark"));
                contact.setPhone(json.getString("phone"));
                resp.setObj(contactService.updateContact(contact));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("更新联系人成功！");
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("ContactController==>updateContact:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("ContactController==>updateContact:更新联系人失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @DeleteMapping(value = "delete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object deleteContact(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                contactService.deleteContact(id, user.getUuid());
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("删除联系人成功！");
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("ContactController==>deleteContact:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("ContactController==>deleteContact:删除联系人失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object listContacts(HttpServletRequest request,@RequestParam(value = "companyId", required = true) Long companyId) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                resp.setObj(contactService.list(companyId));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
            } else {
                resp.setStatus(Constants.RESPONSE_REDIRECT);
                resp.setMessage("./ggreen/login.html");
            }
        } catch (SessionException e) {
            LOGGER.error("ContactController==>listContacts:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("ContactController==>listContacts:获取联系人列表失败！,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }
}
