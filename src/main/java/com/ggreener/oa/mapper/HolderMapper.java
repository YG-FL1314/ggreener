package com.ggreener.oa.mapper;

import com.ggreener.oa.po.HolderPO;
import com.ggreener.oa.po.HolderPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HolderMapper {
    Long insert(HolderPO chat);
    List<HolderPO> selectByCompanyId(Long companyId);
    Long delete(@Param("id") Long id,
                @Param("updateUser") String updateUser,
                @Param("updateTime") Date updateTime);
    Long update(HolderPO chat);
    HolderPO get(Long id);
}
