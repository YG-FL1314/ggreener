package com.ggreener.oa.mapper;

import com.ggreener.oa.po.CompanyTagsPO;
import com.ggreener.oa.po.TagDetailPO;
import com.ggreener.oa.po.TagPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyTagsMapper {
    Long batchInsert(List<CompanyTagsPO> list);
    Long delete(Long companyId);
    List<TagPO> selectTagsByCompanyId(Long companyId);
    List<CompanyTagsPO> selectCompanyByTags(@Param("tags") List<Long> tags);
    List<TagDetailPO> listByCompanyIds(@Param("ids") List<Long> ids);
}
