package com.ggreener.oa.controller;

import com.alibaba.fastjson.JSONObject;
import com.ggreener.oa.exception.SessionException;
import com.ggreener.oa.po.HolderPO;
import com.ggreener.oa.service.HolderService;
import com.ggreener.oa.service.CompanyService;
import com.ggreener.oa.service.UserService;
import com.ggreener.oa.util.Constants;
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
@RequestMapping(value = {"holder"})
public class HolderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HolderController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private HolderService holderService;

    @Autowired
    private CompanyService companyService;

    @PostMapping(value = "add", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE },
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object addHolder(@RequestBody JSONObject json, HttpServletRequest request) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                HolderPO holder = new HolderPO();
                Date date = new Date();
                holder.setCreateTime(date);
                holder.setUpdateTime(date);
                holder.setCreateUser(user.getUuid());
                holder.setUpdateUser(user.getUuid());
                holder.setCompanyId(json.getLong("companyId"));
                companyService.get(holder.getCompanyId());
                holder.setName(json.getString("name"));
                holder.setStatus(Constants.STATUS_NORMAL);
                holder.setPercent(json.getString("percent"));
                holder.setAmount(json.getDouble("amount"));
                resp.setObj(holderService.addHolder(holder));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("添加股东信息成功！");
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("HolderController==>addHolder:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("HolderController==>addHolder:添加股东信息失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @PutMapping(value = "update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object updateHolder(@RequestBody JSONObject json, HttpServletRequest request) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                HolderPO holder = new HolderPO();
                holder.setUpdateTime(new Date());
                holder.setUpdateUser(user.getUuid());
                holder.setId(json.getLong("id"));
                holderService.getHolder(holder.getId());
                holder.setName(json.getString("name"));
                holder.setPercent(json.getString("percent"));
                holder.setAmount(json.getDouble("amount"));
                resp.setObj(holderService.updateHolder(holder));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("更新股东信息成功！");
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("HolderController==>updateHolder:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("HolderController==>updateHolder:更新股东信息失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @DeleteMapping(value = "delete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object deleteHolder(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                holderService.deleteHolder(id, user.getUuid());
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("删除股东信息成功！");
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("HolderController==>deleteHolder:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("HolderController==>deleteHolder:删除股东信息失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object listHolders(HttpServletRequest request,@RequestParam(value = "companyId", required = true) Long companyId) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                resp.setObj(holderService.list(companyId));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setObj(user);
            } else {
                resp.setStatus(Constants.RESPONSE_REDIRECT);
                resp.setMessage("./ggreen/login.html");
            }
        } catch (SessionException e) {
            LOGGER.error("HolderController==>listHolders:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("HolderController==>listHolders:获取股东信息列表失败！,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }
}
