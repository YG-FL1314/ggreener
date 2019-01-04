package com.ggreener.oa.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ggreener.oa.exception.SessionException;
import com.ggreener.oa.po.RequirePO;
import com.ggreener.oa.service.RequireService;
import com.ggreener.oa.service.CompanyService;
import com.ggreener.oa.service.UserService;
import com.ggreener.oa.util.Constants;
import com.ggreener.oa.vo.CompanyVO;
import com.ggreener.oa.vo.ResponseVO;
import com.ggreener.oa.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lifu on 2018-09-29
 *
 */
@RestController
@RequestMapping(value = {"require"})
public class RequireController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequireController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RequireService requireService;

    @Autowired
    private CompanyService companyService;

    @PostMapping(value = "add", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE },
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object addRequire(@RequestBody JSONObject json, HttpServletRequest request) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                Long companyId = json.getLong("companyId");
                companyService.get(companyId);
                List<Long> requires = new ArrayList<>();
                JSONArray array = json.getJSONArray("tags");
                for (int i = 0; i < array.size(); i++) {
                    requires.add(array.getLong(i));
                }
                requireService.addRequires(companyId, requires, user.getUuid());
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("添加需求信息成功！");
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("RequireController==>addRequire:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("RequireController==>addRequire:添加需求信息失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @DeleteMapping(value = "delete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object deleteRequire(HttpServletRequest request, @RequestParam(value = "companyId", required = true) Long companyId) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                requireService.deleteRequire(companyId);
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("删除需求信息成功！");
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("RequireController==>deleteRequire:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("RequireController==>deleteRequire:删除需求信息失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @GetMapping(value = "get", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object listRequires(HttpServletRequest request,@RequestParam(value = "companyId", required = true) Long companyId) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                resp.setObj(requireService.getRequires(companyId));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
            } else {
                resp.setStatus(Constants.RESPONSE_REDIRECT);
                resp.setMessage("./ggreen/login.html");
            }
        } catch (SessionException e) {
            LOGGER.error("RequireController==>listRequires:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("RequireController==>listRequires:获取需求信息列表失败！,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @PutMapping(value = "update", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object updateRequires(HttpServletRequest request, @RequestBody JSONObject json) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                Long companyId = json.getLong("companyId");
                companyService.get(companyId);
                List<Long> requires = new ArrayList<>();
                JSONArray array = json.getJSONArray("tags");
                for (int i = 0; i < array.size(); i++) {
                    requires.add(array.getLong(i));
                }
                requireService.updateRequires(companyId, requires, user.getUuid());
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("更新需求信息成功！");
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("RequireController==>updateRequires:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("RequireController==>updateRequires: 更新需求,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }
}
