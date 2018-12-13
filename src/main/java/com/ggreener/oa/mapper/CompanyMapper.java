package com.ggreener.oa.mapper;

import com.ggreener.oa.po.CompanyOverviewPO;
import com.ggreener.oa.po.CompanyPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CompanyMapper {
    Long insert(CompanyPO company);
    CompanyPO selectByCompanyId(Long companyId);
    Long delete(@Param("companyId") Long companyId, @Param("userId") String userId,
                @Param("updateTime") Date updateTime);
    CompanyPO selectByName(String name);
    List<CompanyOverviewPO> selectByIds(@Param("name") String name,
                                        @Param("ids") List<Long> ids,
                                        @Param("start") Long start,
                                        @Param("limit") Long limit);

    List<Long> list(@Param("name") String name);

    Long countByIds(@Param("name") String name,
                    @Param("ids") List<Long> ids);
    Long update(CompanyPO company);
}
