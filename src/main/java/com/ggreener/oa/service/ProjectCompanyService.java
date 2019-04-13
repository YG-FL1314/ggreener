package com.ggreener.oa.service;

import com.ggreener.oa.exception.ProjectCompanyException;
import com.ggreener.oa.exception.ProjectException;
import com.ggreener.oa.mapper.ProjectCompanyMapper;
import com.ggreener.oa.mapper.TagMapper;
import com.ggreener.oa.po.ProjectCompanyDetailPO;
import com.ggreener.oa.po.ProjectCompanyPO;
import com.ggreener.oa.po.TagPO;
import com.ggreener.oa.util.Constants;
import com.ggreener.oa.vo.ProjectCompanyDetailVO;
import com.ggreener.oa.vo.ProjectCompanyVO;
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
public class ProjectCompanyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectCompanyService.class);

    @Autowired
    private ProjectCompanyMapper projectCompanyMapper;

    @Autowired
    private TagMapper tagMapper;

    public ProjectCompanyVO addProjectCompany(ProjectCompanyPO projectCompanyPO) throws ProjectCompanyException {
        if (projectCompanyMapper.insert(projectCompanyPO) > 0) {
            ProjectCompanyVO result = new ProjectCompanyVO();
            BeanUtils.copyProperties(projectCompanyPO, result);
            return result;
        } else {
            throw new ProjectCompanyException("添加项目和公司关系失败！");
        }
    }

    public List<ProjectCompanyDetailVO> getListByProjectId(Long projectId) throws ProjectCompanyException {
        List<ProjectCompanyDetailVO> result = new ArrayList<>();
        List<ProjectCompanyDetailPO> list = projectCompanyMapper.selectByProjectId(projectId);
        if (null != list) {
            for (ProjectCompanyDetailPO tmp : list) {
                ProjectCompanyDetailVO vo = new ProjectCompanyDetailVO();
                BeanUtils.copyProperties(tmp, vo);
                vo.setId(tmp.getCompanyId());
                result.add(vo);
            }
        }
        return result;
    }

    public List<ProjectCompanyDetailVO> getListByCompanyId(Long companyId) throws ProjectCompanyException {
        List<ProjectCompanyDetailVO> result = new ArrayList<>();
        List<ProjectCompanyDetailPO> list = projectCompanyMapper.selectByCompanyId(companyId);
        if (null != list) {
            Map<Long, TagPO> map = tagMapper.list(new Long(Constants.PROJECT_TYPE_FLAG)).stream()
                    .collect(Collectors.toMap(TagPO::getId, tag -> tag));
            for (ProjectCompanyDetailPO tmp : list) {
                ProjectCompanyDetailVO vo = new ProjectCompanyDetailVO();
                BeanUtils.copyProperties(tmp, vo);
                vo.setProjectType(map.get(tmp.getProjectType()).getName());
                result.add(vo);
            }
        }
        return result;
    }

    public ProjectCompanyVO updateProjectCompany(ProjectCompanyPO projectCompanyPO) throws ProjectCompanyException {
        if (projectCompanyMapper.update(projectCompanyPO) > 0) {
            ProjectCompanyVO result = new ProjectCompanyVO();
            BeanUtils.copyProperties(projectCompanyPO, result);
            return result;
        } else {
            throw new ProjectCompanyException("更新项目关系失败！");
        }
    }

    public void deleteProjectCompany(Long id, String userId) throws ProjectCompanyException {
        if (projectCompanyMapper.delete(id, userId, new Date()) <= 0) {
            throw new ProjectCompanyException("删除项目关系失败！");
        }
    }

    public ProjectCompanyVO getProjectCompany(Long id) throws ProjectCompanyException {
        ProjectCompanyVO result = new ProjectCompanyVO();
        ProjectCompanyPO po = projectCompanyMapper.get(id);
        if (null != po) {
            BeanUtils.copyProperties(po, result);
        } else {
            throw new ProjectCompanyException("项目关系不存在！");
        }
        return result;
    }
}
