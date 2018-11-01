package com.ggreener.oa.mapper;

import com.ggreener.oa.po.RequirePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RequireMapper {
    Long insert(RequirePO chat);
    List<RequirePO> selectByCompanyId(Long companyId);
    Long delete(Long companyId);
    RequirePO get(Long id);
}
