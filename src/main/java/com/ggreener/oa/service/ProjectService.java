package com.ggreener.oa.service;

import com.ggreener.oa.exception.ProjectException;
import com.ggreener.oa.mapper.ProjectMapper;
import com.ggreener.oa.mapper.TagMapper;
import com.ggreener.oa.po.ProjectPO;
import com.ggreener.oa.po.TagPO;
import com.ggreener.oa.util.Constants;
import com.ggreener.oa.vo.ProjectVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by lifu on 2018/9/30.
 *
 *
 */
@Service
public class ProjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private TagMapper tagMapper;

    public ProjectVO addProject(ProjectPO project) throws ProjectException {
        if (projectMapper.insert(project) > 0) {
            ProjectVO result = new ProjectVO();
            BeanUtils.copyProperties(project, result);
            return result;
        } else {
            throw new ProjectException("添加项目失败！");
        }
    }

    public ProjectVO getProject(Long projectId) throws ProjectException {
        ProjectVO result = new ProjectVO();
        ProjectPO project = projectMapper.selectById(projectId);
        if (null != project) {
            Map<Long, TagPO> map = tagMapper.list(new Long(Constants.PROJECT_TYPE_FLAG)).stream()
                    .collect(Collectors.toMap(TagPO::getId, tag -> tag));
            BeanUtils.copyProperties(project, result);
            result.setType(map.get(project.getType()).getName());
        } else {
            throw new ProjectException("项目不存在！");
        }
        return result;
    }

    public ProjectVO updateProject(ProjectPO project) throws ProjectException {
        if (projectMapper.update(project) > 0) {
            ProjectVO result = new ProjectVO();
            BeanUtils.copyProperties(project, result);
            return result;
        } else {
            throw new ProjectException("更新联系人失败！");
        }
    }

    public void deleteProject(Long contactId, String userId) throws ProjectException {
        if (projectMapper.delete(contactId, userId, new Date()) <= 0) {
            throw new ProjectException("删除项目失败！");
        }
    }

    public List<ProjectVO> listProjects() {
        List<ProjectPO> list = projectMapper.list();
        List<ProjectVO> result = new ArrayList<>();
        if (null != list && list.size() > 0) {
            Map<Long, ProjectPO> projectAmountMap = projectMapper.selectProjectAmount().stream()
                    .collect(Collectors.toMap(ProjectPO::getId, project -> project));
            Map<Long, TagPO> map = tagMapper.list(new Long(Constants.PROJECT_TYPE_FLAG)).stream()
                    .collect(Collectors.toMap(TagPO::getId, tag -> tag));
            for (ProjectPO projectPO : list) {
                ProjectVO projectTmp = new ProjectVO();
                BeanUtils.copyProperties(projectPO, projectTmp);
                if (projectAmountMap.containsKey(projectTmp.getId())) {
                    projectTmp.setAmount(projectAmountMap.get(projectTmp.getId()).getAmount());
                }
                if (map.containsKey(projectPO.getType())) {
                    projectTmp.setType(map.get(projectPO.getType()).getName());
                } else {
                    projectTmp.setType(projectPO.getType().toString());
                }
                result.add(projectTmp);
            }
        }
        return result;
    }
}
