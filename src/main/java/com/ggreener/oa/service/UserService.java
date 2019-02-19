package com.ggreener.oa.service;

import com.ggreener.oa.exception.SessionException;
import com.ggreener.oa.exception.UserException;
import com.ggreener.oa.mapper.UserMapper;
import com.ggreener.oa.po.UserPO;
import com.ggreener.oa.util.PasswordUtil;
import com.ggreener.oa.util.UuidUtil;
import com.ggreener.oa.util.Constants;
import com.ggreener.oa.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lifu on 2018/9/30.
 *
 *
 */
@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    public UserVO login(String name, String password) {
        try {
            String pwd = PasswordUtil.Encrypt(name + password);
            LOGGER.debug(pwd);
            UserPO user = userMapper.selectUserPOByName(name, pwd);
            if (null != user) {
                UserVO result = new UserVO();
                BeanUtils.copyProperties(user, result);
                return result;
            }
        } catch (Exception e) {
            LOGGER.error("登录失败:" + e.getMessage(), e);
        }
        return null;
    }

    public void addUser(String name, String password, String nickName) throws UserException {
        String pwd = PasswordUtil.Encrypt(name + password);
        UserPO user = userMapper.selectUserPOByName(name, pwd);
        if (null == user) {
            UserPO userNew = new UserPO();
            String uuid = UuidUtil.generateUUID();
            Date date = new Date();
            userNew.setName(name);
            userNew.setNickName(nickName);
            userNew.setRole(Constants.NORMAL_ROLE);
            userNew.setPassword(PasswordUtil.Encrypt( name  + password));
            userNew.setUuid(uuid);
            userNew.setStatus(Constants.STATUS_NORMAL);
            userNew.setCreateTime(date);
            userNew.setUpdateTime(date);
            try {
                userMapper.insert(userNew);
            } catch (Exception e) {
                LOGGER.error("新增用户失败！", e);
                throw new UserException("新增用户失败！");
            }
        } else {
            throw new UserException("用户已存在！");
        }
    }

    public UserVO validateUser(HttpSession session) throws SessionException {
        try {
            String uuid = session.getAttribute("uuid").toString();
            UserPO user = userMapper.selectUserPOByUuid(uuid);
            if (null != user) {
                UserVO result = new UserVO();
                result.setUuid(user.getUuid());
                result.setName(user.getName());
                result.setNickName(user.getNickName());
                result.setRole(user.getRole());
                return result;
            }
        } catch (Exception e) {
            throw new SessionException("未登录，请重新登录！");
        }
        return null;
    }

    public UserVO updateUserByUuid(String uuid, String name, String password, String nickName) throws Exception {
        String pwd = PasswordUtil.Encrypt(name + password);
        UserPO user = new UserPO();
        user.setName(name);
        user.setUuid(uuid);
        user.setPassword(pwd);
        user.setNickName(nickName);
        user.setUpdateTime(new Date());
        if (userMapper.update(user) > 0) {
            UserPO userNew = userMapper.selectUserPOByUuid(uuid);
            UserVO result = new UserVO();
            result.setUuid(userNew.getUuid());
            result.setName(userNew.getName());
            result.setNickName(userNew.getNickName());
            result.setRole(userNew.getRole());
            return result;
        } else {
            throw new UserException("更新失败！");
        }
    }

    public UserVO updateNormalUser(String uuid, String name, String srcPwd, String destPwd) throws Exception {
        UserPO user = userMapper.selectUserPOByUuid(uuid);
        if (null != user && user.getPassword().equals(srcPwd)) {
            user.setPassword(destPwd);
            user.setUpdateTime(new Date());
            user.setName(name);
            if (userMapper.update(user) > 0) {
                UserVO result = new UserVO();
                result.setUuid(user.getUuid());
                result.setName(user.getName());
                result.setNickName(user.getNickName());
                result.setRole(user.getRole());
                return result;
            } else {
                throw new UserException("更新失败！");
            }
        } else {
            throw new UserException("原密码错误！");
        }
    }

    public UserVO updateUserStatus(String uuid, Integer status) throws Exception {
        UserPO user = userMapper.selectUserPOByUuid(uuid);
        if (null != user ) {
            user.setStatus(status);
            user.setUpdateTime(new Date());
            if (userMapper.update(user) > 0) {
                UserVO result = new UserVO();
                result.setUuid(user.getUuid());
                result.setName(user.getName());
                result.setNickName(user.getNickName());
                result.setRole(user.getRole());
                return result;
            } else {
                throw new UserException("停用该用户失败！");
            }
        } else {
            throw new UserException("用户不存在！");
        }
    }

    public List<UserVO> listUsers(Integer role){
        List<UserVO> result = new ArrayList<>();
        List<UserPO> users = userMapper.list(role);
        if (null != users && users.size() > 0) {
            for (UserPO user : users) {
                UserVO userTmp = new UserVO();
                BeanUtils.copyProperties(user, userTmp);
                result.add(userTmp);
            }
        }
        return result;
    }
}
