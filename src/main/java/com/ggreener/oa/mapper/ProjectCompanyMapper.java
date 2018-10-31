package com.ggreener.oa.mapper;

import com.ggreener.oa.po.ProjectCompanyPO;
import com.ggreener.oa.po.ProjectPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProjectCompanyMapper {
    Long insert(ProjectCompanyPO projectCompanyPO);
    List<ProjectCompanyPO> selectByProjectId(Long projectId);
    List<ProjectCompanyPO> selectByCompanyId(Long companyId);
    Long delete(@Param("id") Long id, @Param("updateUser") String updateUser,
                @Param("updateTime") Date updateTime);
    Long update(ProjectCompanyPO projectCompanyPO);
}
