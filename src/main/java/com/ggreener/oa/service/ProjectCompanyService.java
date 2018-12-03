package com.ggreener.oa.service;

import com.ggreener.oa.exception.ProjectCompanyException;
import com.ggreener.oa.exception.ProjectException;
import com.ggreener.oa.mapper.ProjectCompanyMapper;
import com.ggreener.oa.po.ProjectCompanyPO;
import com.ggreener.oa.vo.ProjectCompanyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public ProjectCompanyVO addProjectCompany(ProjectCompanyPO projectCompanyPO) throws ProjectCompanyException {
        if (projectCompanyMapper.insert(projectCompanyPO) > 0) {
            ProjectCompanyVO result = new ProjectCompanyVO();
            BeanUtils.copyProperties(projectCompanyPO, result);
            return result;
        } else {
            throw new ProjectCompanyException("添加项目和公司关系失败！");
        }
    }

    public List<ProjectCompanyVO> getListByProjectId(Long projectId) throws ProjectCompanyException {
        List<ProjectCompanyVO> result = new ArrayList<>();
        List<ProjectCompanyPO> list = projectCompanyMapper.selectByProjectId(projectId);
        if (null != list) {
            for (ProjectCompanyPO tmp : list) {
                ProjectCompanyVO vo = new ProjectCompanyVO();
                BeanUtils.copyProperties(tmp, vo);
                result.add(vo);
            }
        }
        return result;
    }

    public List<ProjectCompanyVO> getListByCompanyId(Long companyId) throws ProjectCompanyException {
        List<ProjectCompanyVO> result = new ArrayList<>();
        List<ProjectCompanyPO> list = projectCompanyMapper.selectByCompanyId(companyId);
        if (null != list) {
            for (ProjectCompanyPO tmp : list) {
                ProjectCompanyVO vo = new ProjectCompanyVO();
                BeanUtils.copyProperties(tmp, vo);
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
}
