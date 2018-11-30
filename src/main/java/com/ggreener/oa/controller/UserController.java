package com.ggreener.oa.controller;

import com.alibaba.fastjson.JSONObject;
import com.ggreener.oa.exception.SessionException;
import com.ggreener.oa.service.UserService;
import com.ggreener.oa.util.PasswordUtil;
import com.ggreener.oa.util.Constants;
import com.ggreener.oa.vo.ResponseVO;
import com.ggreener.oa.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lifu on 2018-09-29
 *
 */
@RestController
@RequestMapping(value = {"user"})
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "*")
    @GetMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    Object login(@RequestParam(value = "name", required = true) String name,
                 @RequestParam(value = "password", required = true) String password,
                 HttpServletRequest request, HttpServletResponse response){
        ResponseVO resp = new ResponseVO();
        UserVO vo = userService.login(name, password);
        if (null == vo) {
            resp.setStatus(1);
            resp.setMessage("用户名或密码错误！");
        } else {
            resp.setStatus(0);
            resp.setMessage("登录成功！");
            resp.setObj(vo);
            request.getSession().setAttribute("uuid", vo.getUuid());
        }
        return resp;
    }

    @PostMapping(value = "add", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE },
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object addUser(@RequestBody JSONObject json, HttpServletRequest request) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user && user.getRole() == Constants.ADMIN_ROLE) {
                String name = json.getString("name");
                String password = json.getString("password");
                userService.addUser(name, password);
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("新增用户成功！");
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("UserController==>addUser:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("UserController==>addUser:新增用户失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @GetMapping(value = "logout", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object logout(HttpServletRequest request) {
        ResponseVO resp = new ResponseVO();
        request.getSession().removeAttribute("uuid");
        resp.setStatus(Constants.RESPONSE_SUCCESS);
        resp.setMessage("退出登录成功！");
        return resp;
    }

    @PutMapping(value = "/admin/update", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE },
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object updateUserByAdmin(@RequestBody JSONObject json, HttpServletRequest request) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user && user.getRole() == Constants.ADMIN_ROLE) {
                String uuid = json.getString("uuid");
                String name = json.getString("name");
                String password = json.getString("password");
                resp.setObj(userService.updateUserByUuid(uuid, name, password));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("更新成功！");
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("UserController==>updateUserByAdmin:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("UserController==>updateUserByAdmin:更新用户失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @PutMapping(value = "/normal/update", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE },
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object updateUserByNormal(HttpServletRequest request, @RequestBody JSONObject json) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                String srcPwd = json.getString("srcPwd");
                String destPwd = json.getString("destPwd");
                String srcDe = PasswordUtil.Encrypt(user.getName() + srcPwd);
                String destDe = PasswordUtil.Encrypt(user.getName() + destPwd);
                resp.setObj(userService.updateNormalUser(user.getUuid(), user.getName(), srcDe, destDe));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("更新成功！");
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("更新失败");
            }
        } catch (SessionException e) {
            LOGGER.error("UserController==>updateUserByNormal:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("UserController==>updateUserByNormal:更新用户失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @DeleteMapping(value = "/update/status", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object updateUserStatus(HttpServletRequest request, @RequestParam(value = "id", required = true)String uuid,
                      @RequestParam(value = "status", required = true)Integer status) {
        ResponseVO resp = new ResponseVO();
        try {
            if (status != Constants.STATUS_NORMAL && status != Constants.STATUS_DELETE) {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("用户状态错误！");
                return resp;
            }
            UserVO user = userService.validateUser(request.getSession());
            if (null != user && user.getRole() == Constants.ADMIN_ROLE) {
                resp.setObj(userService.updateUserStatus(uuid, status));
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setMessage("停用该用户成功！");
            } else {
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限！");
            }
        } catch (SessionException e) {
            LOGGER.error("UserController==>updateUserStatus:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("UserController==>updateUserStatus:更新用户状态失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    @GetMapping(value = "/islogin", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object isLogin(HttpServletRequest request, HttpServletResponse response) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user) {
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setObj(user);
            } else {
                resp.setStatus(Constants.RESPONSE_REDIRECT);
                resp.setMessage("./ggreen/login.html");
            }
        } catch (SessionException e) {
            LOGGER.error("UserController==>isLogin:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("UserController==>isLogin:登录失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage("登录失败");
        }
        return resp;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object listUsers(HttpServletRequest request) {
        ResponseVO resp = new ResponseVO();
        try {
            UserVO user = userService.validateUser(request.getSession());
            if (null != user && user.getRole() == Constants.ADMIN_ROLE) {
                resp.setStatus(Constants.RESPONSE_SUCCESS);
                resp.setObj(userService.listUsers(Constants.NORMAL_ROLE));
            } else if (null != user){
                resp.setStatus(Constants.RESPONSE_FAIL);
                resp.setMessage("没有权限");
            } else {
                resp.setStatus(Constants.RESPONSE_REDIRECT);
                resp.setMessage("./ggreen/login.html");
            }
        } catch (SessionException e) {
            LOGGER.error("UserController==>listUsers:登录过期,", e);
            resp.setStatus(Constants.RESPONSE_REDIRECT);
            resp.setMessage("./login.html");
        } catch (Exception e) {
            LOGGER.error("UserController==>listUsers:获取用户列表失败,", e);
            resp.setStatus(Constants.RESPONSE_FAIL);
            resp.setMessage("获取用户列表失败");
        }
        return resp;
    }
}
