package com.ggreener.oa.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ggreener.oa.exception.SessionException;
import com.ggreener.oa.po.CompanyPO;
import com.ggreener.oa.service.CompanyService;
import com.ggreener.oa.service.UserService;
import com.ggreener.oa.util.Constants;
import com.ggreener.oa.util.TransformUtil;
import com.ggreener.oa.vo.ResponseVO;
import com.ggreener.oa.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
