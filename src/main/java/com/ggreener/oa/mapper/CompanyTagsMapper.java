package com.ggreener.oa.mapper;

import com.ggreener.oa.po.CompanyTagsPO;
import com.ggreener.oa.po.TagDetailPO;
import com.ggreener.oa.po.TagPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CompanyTagsMapper {
    Long batchInsert(@Param("companyId") Long companyId, @Param("list") List<Long> list, @Param("time") Date time);
    Long delete(@Param("companyId") Long companyId, @Param("memberId") Long memberId);
    List<TagPO> selectTagsByCompanyId(Long companyId);
    List<CompanyTagsPO> selectCompanyByTags(@Param("tags") List<Long> tags);
    List<TagDetailPO> listByCompanyIds(@Param("ids") List<Long> ids);
    Long deleteMember(@Param("companyId") Long companyId, @Param("memberId") Long memberId);
    List<TagDetailPO> selectCompanyByParentIds(@Param("companyIds") List<Long> companyIds, @Param("parentIds") List<Long> parentIds);
}
