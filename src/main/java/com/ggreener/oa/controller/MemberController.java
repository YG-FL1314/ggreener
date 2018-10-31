package com.ggreener.oa.controller;

import com.alibaba.fastjson.JSONObject;
import com.ggreener.oa.exception.SessionException;
import com.ggreener.oa.po.MemberPO;
import com.ggreener.oa.service.MemberService;
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
@RequestMapping(value = {"member"})
public class MemberController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CompanyService companyService;

    @PostMapping(value = "add", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE },
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object addMember(@RequestBody JSONObject json, HttpServletRequest request) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                MemberPO member = new MemberPO();
                Date date = new Date();
                member.setCreateTime(date);
                member.setUpdateTime(date);
                member.setCreateUser(user.getUuid());
                member.setUpdateUser(user.getUuid());
                member.setCompanyId(json.getLong("companyId"));
                companyService.get(member.getCompanyId());
                member.setTagId(json.getLong("tagId"));
                member.setMemberCode(json.getString("memberCode"));
                member.setJoiningTime(json.getDate("joiningTime"));
                member.setValidityTime(json.getDate("validityTime"));
                resp.setObj(memberService.addMember(member));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("添加会员信息成功！");
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("MemberController==>addMember:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("MemberController==>addMember:添加会员信息失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @PutMapping(value = "update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object updateMember(@RequestBody JSONObject json, HttpServletRequest request) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                MemberPO member = new MemberPO();
                member.setUpdateTime(new Date());
                member.setUpdateUser(user.getUuid());
                member.setId(json.getLong("id"));
                memberService.getMember(member.getId());
                member.setTagId(json.getLong("tagId"));
                member.setMemberCode(json.getString("memberCode"));
                member.setJoiningTime(json.getDate("joiningTime"));
                member.setValidityTime(json.getDate("validityTime"));
                resp.setObj(memberService.updateMember(member));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("更新会员信息成功！");
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("MemberController==>updateMember:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("MemberController==>updateMember:更新会员信息失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object getMember(HttpServletRequest request,@RequestParam(value = "companyId", required = true) Long companyId) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                resp.setObj(memberService.getMemberByCompanyId(companyId));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setObj(user);
            } else {
                resp.setStatus(Constants.RESPONSE_REDIRECT);
                resp.setMessage("./ggreen/login.html");
            }
        } catch (SessionException e) {
            LOGGER.error("MemberController==>getMember:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("MemberController==>getMember:获取会员信息列表失败！,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }
}