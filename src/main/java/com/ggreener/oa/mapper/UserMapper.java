package com.ggreener.oa.mapper;

import com.ggreener.oa.po.UserPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserMapper {
    UserPO selectUserPOByName(@Param("name")String name, @Param("password")String password);
    UserPO selectUserPOByUuid(@Param("uuid")String uuid);
    Long update(UserPO user);
    Long insert(UserPO user);
    List<UserPO> list(Integer role);
}
