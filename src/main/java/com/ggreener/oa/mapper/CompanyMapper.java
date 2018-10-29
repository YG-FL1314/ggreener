package com.ggreener.oa.mapper;

import com.ggreener.oa.po.CompanyPO;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyMapper {
    Long insert(CompanyPO company);
    CompanyPO selectByCompanyId(Long companyId);
    Long delete(Long companyId);
}
