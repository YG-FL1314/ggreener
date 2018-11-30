package com.ggreener.oa.mapper;

import com.ggreener.oa.po.CompanyListPO;
import com.ggreener.oa.po.CompanyPO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyMapper {
    Long insert(CompanyPO company);
    CompanyPO selectByCompanyId(Long companyId);
    Long delete(Long companyId);
    CompanyPO selectByName(String name);
    List<CompanyListPO> list();
}
