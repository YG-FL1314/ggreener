package com.ggreener.oa.service;

import com.ggreener.oa.exception.TagException;
import com.ggreener.oa.mapper.TagMapper;
import com.ggreener.oa.mapper.UserMapper;
import com.ggreener.oa.po.TagPO;
import com.ggreener.oa.util.constants;
import com.ggreener.oa.vo.TagVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lifu on 2018/10/8.
 * <p>
 * XXX
 */
@Service
public class TagService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagService.class);

    @Autowired
    private TagMapper tagMapper;

    public TagVO addTag(String name, Long parentId, Integer order, String userId) throws Exception {
        TagPO tag = new TagPO();
        tag.setName(name);
        tag.setParentId(parentId);
        tag.setOrder(order);
        tag.setCreateUser(userId);
        tag.setStatus(constants.STATUS_NORMAL);
        tag.setUpdateUser(userId);
        tag.setCreateTime(new Date());
        tag.setUpdateTime(new Date());
        if (tagMapper.insert(tag) > 0) {
            TagVO tagVO = new TagVO();
            tagVO.setId(tag.getId());
            tagVO.setName(tag.getName());
            tagVO.setParentId(tag.getParentId());
            tagVO.setOrder(tag.getOrder());
            return tagVO;
        } else {
            throw new TagException("增加标签失败！");
        }
    }

    public TagVO updateTag(Long id, String name, Long parentId, Integer order, Integer status, String userId) throws Exception {
        TagPO tag = new TagPO();
        tag.setId(id);
        tag.setName(name);
        tag.setParentId(parentId);
        tag.setOrder(order);
        tag.setStatus(status);
        tag.setUpdateUser(userId);
        tag.setUpdateTime(new Date());
        if (tagMapper.update(tag) > 0) {
            TagVO tagVO = new TagVO();
            tagVO.setId(tag.getId());
            tagVO.setName(tag.getName());
            tagVO.setParentId(tag.getParentId());
            tagVO.setOrder(tag.getOrder());
            return tagVO;
        } else {
            throw new TagException("更新标签失败！");
        }
    }

    public List<TagVO> list(Long parentId) {
        List<TagPO> list = tagMapper.list(parentId);
        List<TagVO> result = new ArrayList<>();
        for (TagPO tag : list) {
            TagVO tagNew = new TagVO();
            BeanUtils.copyProperties(tag, tagNew);
            result.add(tagNew);
        }
        return result;
    }
}
