package com.ggreener.oa.mapper;

import com.ggreener.oa.po.ChatPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ChatMapper {
    Long insert(ChatPO chat);
    List<ChatPO> selectByCompanyId(@Param("companyId") Long companyId, @Param("start") int start, @Param("limit") int limit);
    Long countByCompanyId(Long companyId);
    Long delete(@Param("id") Long id,
                @Param("updateUser") String updateUser,
                @Param("updateTime") Date updateTime);
    Long update(ChatPO chat);
    ChatPO get(Long id);
}
