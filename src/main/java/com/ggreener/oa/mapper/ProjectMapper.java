package com.ggreener.oa.mapper;

import com.ggreener.oa.po.ProjectPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProjectMapper {
    Long insert(ProjectPO project);
    ProjectPO selectById(Long projectId);
    Long delete(@Param("projectId")Long projectId,
                @Param("updateUser")String updateUser,
                @Param("updateTime")Date updateTime);
    List<ProjectPO> list(@Param("projectType")Long projectType,
                         @Param("startDate")Date startDate,
                         @Param("endDate")Date endDate);
    List<ProjectPO> selectProjectAmount();
    Long update(ProjectPO project);
}
