package com.ggreener.oa.mapper;

import com.ggreener.oa.po.RequirePO;
import com.ggreener.oa.po.TagDetailPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RequireMapper {
    Long insert(RequirePO chat);
    Long batchInsert(@Param("companyId") Long companyId, @Param("list") List<Long> list,
                     @Param("userId") String userId, @Param("date") Date date);
    List<TagDetailPO> selectByCompanyId(Long companyId);
    Long delete(Long companyId);
    List<RequirePO> listByRequireId(@Param("requireIds") List<Long> requireIds,
                               @Param("companyIds") List<Long> companyIds);
}
