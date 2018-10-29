package com.ggreener.oa.controller;

import com.alibaba.fastjson.JSONObject;
import com.ggreener.oa.exception.SessionException;
import com.ggreener.oa.service.TagService;
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

/**
 * Created by lifu on 2018/10/8.
 * <p>
 * XXX
 */
@RestController
@RequestMapping(value = {"tag"})
public class TagController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagController.class);

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "add", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object addTag(@RequestBody JSONObject json, HttpServletRequest request) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user && user.getRole() == Constants.ADMIN_ROLE) {
                String name = json.getString("name");
                Long parentId = json.getLong("parentId");
                Integer order = json.getInteger("order");
                resp.setObj(tagService.addTag(name, parentId, order, user.getUuid()));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("新增标签成功！");
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("TagController==>addTag:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("TagController==>addTag: 新增tag失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @PutMapping(value = "update", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object updateTag(@RequestBody JSONObject json, HttpServletRequest request) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user && user.getRole() == Constants.ADMIN_ROLE) {
                Long id = json.getLong("id");
                String name = json.getString("name");
                Long parentId = json.getLong("parentId");
                Integer order = json.getInteger("order");
                resp.setObj(tagService.updateTag(id, name, parentId, order, null, user.getUuid()));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("更新标签成功！");
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("TagController==>updateTag:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("TagController==>updateTag: 更新tag失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @DeleteMapping(value = "delete", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object deleteTag(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id,
                     @RequestParam(value = "status", required = true) Integer status) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user && user.getRole() == Constants.ADMIN_ROLE) {
                resp.setObj(tagService.updateTag(id, null, null, null, status, user.getUuid()));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("删除标签成功！");
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("TagController==>deleteTag:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("TagController==>deleteTag: 删除tag失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "list")
    Object listTag(HttpServletRequest request, @RequestParam(value = "parentId", required = true) Long parentId) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                resp.setObj(tagService.list(parentId));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("获取标签列表成功！");
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("未登录，请重新登陆！");
            }
        } catch (SessionException e) {
            LOGGER.error("TagController==>listTag:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("TagController==>listTag: 获取tag列表失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }
}
