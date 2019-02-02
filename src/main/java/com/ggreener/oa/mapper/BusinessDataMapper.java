package com.ggreener.oa.mapper;

import com.ggreener.oa.po.BusinessDataPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BusinessDataMapper {
    Long insert(BusinessDataPO businessDataPO);
    List<BusinessDataPO> selectByCompanyId(Long companyId);
    Long update(BusinessDataPO businessDataPO);
    Long delete(@Param("id") Long id,
                @Param("updateUser") String updateUser,
                @Param("updateTime") Date updateTime);
}

