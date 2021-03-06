package com.ggreener.oa.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ggreener.oa.exception.SessionException;
import com.ggreener.oa.po.ProjectCompanyPO;
import com.ggreener.oa.po.ProjectPO;
import com.ggreener.oa.service.CompanyService;
import com.ggreener.oa.service.ProjectCompanyService;
import com.ggreener.oa.service.ProjectService;
import com.ggreener.oa.service.UserService;
import com.ggreener.oa.util.Constants;
import com.ggreener.oa.vo.CompanyVO;
import com.ggreener.oa.vo.ProjectCompanyVO;
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
@RequestMapping(value = {"projectcompany"})
public class ProjectCompanyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectCompanyController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectCompanyService projectCompanyService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ProjectService projectService;

    @PostMapping(value = "add", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE },
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object addProjectCompany(@RequestBody JSONObject json, HttpServletRequest request) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                ProjectCompanyPO projectCompany = JSON.parseObject(json.toString(), new TypeReference<ProjectCompanyPO>(){});
                Date date = new Date();
                projectCompany.setCreateTime(date);
                projectCompany.setUpdateTime(date);
                projectCompany.setCreateUser(user.getUuid());
                projectCompany.setUpdateUser(user.getUuid());
                projectService.getProject(projectCompany.getProjectId());
                companyService.get(projectCompany.getCompanyId());
                projectCompany.setStatus(Constants.STATUS_NORMAL);
                resp.setObj(projectCompanyService.addProjectCompany(projectCompany));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("添加项目关系成功！");
                CompanyVO company = new CompanyVO();
                company.setId(projectCompany.getCompanyId());
                companyService.update(company, user.getUuid());
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("ProjectCompanyController==>addProjectCompany:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("ProjectCompanyController==>addProjectCompany:添加项目关系失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @PutMapping(value = "update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object updateProjectCompany(@RequestBody JSONObject json, HttpServletRequest request) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                ProjectCompanyPO projectCompany = JSON.parseObject(json.toString(), new TypeReference<ProjectCompanyPO>(){});
                projectCompany.setUpdateTime(new Date());
                projectCompany.setUpdateUser(user.getUuid());
                projectService.getProject(projectCompany.getProjectId());
                resp.setObj(projectCompanyService.updateProjectCompany(projectCompany));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("更新项目关系成功！");
                CompanyVO company = new CompanyVO();
                company.setId(projectCompany.getCompanyId());
                companyService.update(company, user.getUuid());
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("ProjectCompanyController==>updateProjectCompany:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("ProjectCompanyController==>updateProjectCompany:更新项目关系失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @DeleteMapping(value = "delete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object deleteProjectCompany(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                ProjectCompanyVO vo = projectCompanyService.getProjectCompany(id);
                projectCompanyService.deleteProjectCompany(id, user.getUuid());
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("删除项目关系成功！");
                CompanyVO company = new CompanyVO();
                company.setId(vo.getCompanyId());
                companyService.update(company, user.getUuid());
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("deleteProjectCompany==>deleteProjectCompany:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("deleteProjectCompany==>deleteProjectCompany:删除项目关系失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object listProjects(HttpServletRequest request,
                        @RequestParam(value = "projectId", required = false) Long projectId,
                        @RequestParam(value = "companyId", required = false) Long companyId,
                        @RequestParam(value = "projectType", required = false) Long projectType,
                        @RequestParam(value = "startDate", required = false) Date startDate,
                        @RequestParam(value = "endDate", required = false) Date endDate) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                if (null != projectId) {
                    resp.setObj(projectCompanyService.getListByProjectId(projectId, projectType, startDate, endDate));
                } else if (null != companyId){
                    resp.setObj(projectCompanyService.getListByCompanyId(companyId, projectType, startDate, endDate));
                }
                resp.setStatus(Constants.RESPONSE_SUCCESS);
            } else {
                resp.setStatus(Constants.RESPONSE_REDIRECT);
                resp.setMessage("./ggreen/login.html");
            }
        } catch (SessionException e) {
            LOGGER.error("ProjectCompanyController==>listProjectCompany:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("ProjectCompanyController==>listProjectCompany:获取项目关系列表失败！,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }
}
