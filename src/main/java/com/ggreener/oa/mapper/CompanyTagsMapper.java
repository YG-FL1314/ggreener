package com.ggreener.oa.mapper;

import com.ggreener.oa.po.CompanyPO;
import com.ggreener.oa.po.CompanyTagsPO;
import com.ggreener.oa.po.TagDetailPO;
import com.ggreener.oa.po.TagPO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyTagsMapper {
    Long batchInsert(List<CompanyTagsPO> list);
    Long delete(Long companyId);
    List<TagPO> selectTagsByCompanyId(Long companyId);
    List<Long> selectCompanyByTags(List<Long> tags);
    List<TagDetailPO> listByCompanyIds(List<Long> ids);
}
