package com.ggreener.oa.mapper;

import com.ggreener.oa.po.ProjectCompanyDetailPO;
import com.ggreener.oa.po.ProjectCompanyPO;
import com.ggreener.oa.po.ProjectPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProjectCompanyMapper {
    Long insert(ProjectCompanyPO projectCompanyPO);
    List<ProjectCompanyDetailPO> selectByProjectId(@Param("projectId") Long projectId, @Param("projectType") Long projectType,
                                                   @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    List<ProjectCompanyDetailPO> selectByCompanyId(@Param("companyId") Long companyId, @Param("projectType") Long projectType,
                                                   @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    Long delete(@Param("id") Long id, @Param("updateUser") String updateUser,
                @Param("updateTime") Date updateTime);
    Long update(ProjectCompanyPO projectCompanyPO);
    ProjectCompanyPO get(Long id);
    List<Long> selectByCompanyIds(@Param("companyIds") List<Long> companyIds);
}
