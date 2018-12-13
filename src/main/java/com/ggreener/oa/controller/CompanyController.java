package com.ggreener.oa.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ggreener.oa.exception.SessionException;
import com.ggreener.oa.po.CompanyPO;
import com.ggreener.oa.service.CompanyService;
import com.ggreener.oa.service.UserService;
import com.ggreener.oa.util.Constants;
import com.ggreener.oa.util.TransformUtil;
import com.ggreener.oa.vo.CompanyVO;
import com.ggreener.oa.vo.ResponseVO;
import com.ggreener.oa.vo.UserVO;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lifu on 2018/10/7.
 * <p>
 * XXX
 */
@RestController
@RequestMapping(value = {"company"})
public class CompanyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "add", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object addCompany(@RequestBody JSONObject json, HttpServletRequest request) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                CompanyPO entity = TransformUtil.transformJsonToCompanyPO(json, user.getUuid());
                if (companyService.exist(entity.getName())) {
                    resp.setStatus(Constants.RESPONSE_FAIL);
                    resp.setMessage("公司已存在！");
                    return resp;
                }
                List<Long> tags = new ArrayList<>();
                if (json.containsKey("tags")) {
                    JSONArray list = json.getJSONArray("tags");
                    for (int i = 0; i < list.size(); i++) {
                        tags.add(list.getLong(i));
                    }
                }
                resp.setObj(companyService.addCompany(entity, tags));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("添加公司成功！");
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("添加公司失败！");
            }
        } catch (SessionException e) {
            LOGGER.error("CompanyController==>addCompany:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("CompanyController==>addCompany: 添加公司失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @PostMapping(value = "list", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object listCompanies(@RequestBody JSONObject json, HttpServletRequest request) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                String name = json.getString("name");
                Long start = json.getLong("start");
                Long limit = json.getLong("limit");
                JSONArray tags = json.getJSONArray("tags");
                List<Long> tagList = null;
                if (tags != null && tags.size() > 0) {
                    tagList = new ArrayList<>();
                    for (int i = 0; i < tags.size(); i++) {
                        tagList.add(tags.getLong(i));
                    }
                }
                resp.setObj(companyService.list(name, tagList, start, limit));
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("CompanyController==>listCompanies:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("CompanyController==>listCompanies: 查询公司,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @GetMapping(value = "get", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object getCompanyDetail(@RequestParam(value = "companyId", required = true) Long companyId,
                        HttpServletRequest request) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                resp.setObj(companyService.get(companyId));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("CompanyController==>getCompany:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("CompanyController==>getCompany: 查询公司,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @PutMapping(value = "update", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object updateCompany(HttpServletRequest request, @RequestBody JSONObject json) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                CompanyVO company = JSON.parseObject(json.toString(), new TypeReference<CompanyVO>(){});
                resp.setObj(companyService.update(company, user.getUuid()));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("CompanyController==>getCompany:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("CompanyController==>getCompany: 查询公司,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @DeleteMapping(value = "delete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object deleteCompany(HttpServletRequest request,
                         @RequestParam(value = "companyId", required = true) Long companyId) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                companyService.delete(companyId, user.getUuid());
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("删除企业成功！");
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("CompanyController==>deleteCompany:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("CompanyController==>deleteCompany:删除企业失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

}
