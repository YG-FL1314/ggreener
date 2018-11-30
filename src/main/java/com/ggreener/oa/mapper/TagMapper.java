package com.ggreener.oa.mapper;

import com.ggreener.oa.po.TagPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TagMapper {
    Long insert(TagPO tag);
    Long update(TagPO tag);
    List<TagPO> list(@Param("parentId") Long parentId);
}
