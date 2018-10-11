package com.ggreener.oa.mapper;

import com.ggreener.oa.po.UserPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UserMapper {
    UserPO selectUserPOByName(@Param("name")String name, @Param("password")String password);
    UserPO selectUserPOByUuid(@Param("uuid")String uuid);
    Long update(UserPO user);
    Long insert(UserPO user);
}
