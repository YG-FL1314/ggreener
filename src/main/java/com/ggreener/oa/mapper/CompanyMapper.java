package com.ggreener.oa.mapper;

import com.ggreener.oa.po.CompanyOverviewPO;
import com.ggreener.oa.po.CompanyPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyMapper {
    Long insert(CompanyPO company);
    CompanyPO selectByCompanyId(Long companyId);
    Long delete(Long companyId);
    CompanyPO selectByName(String name);
    List<CompanyOverviewPO> selectByIds(@Param("name") String name,
                                        @Param("ids") List<Long> ids,
                                        @Param("start") Long start,
                                        @Param("limit") Long limit);

    Long countByIds(@Param("name") String name,
                    @Param("ids") List<Long> ids);
}
