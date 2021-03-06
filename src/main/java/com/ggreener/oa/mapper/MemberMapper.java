package com.ggreener.oa.mapper;

import com.ggreener.oa.po.MemberPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MemberMapper {
    Long insert(MemberPO member);
    MemberPO selectByCompanyId(Long companyId);
    Long update(MemberPO member);
    MemberPO get(Long id);
    List<MemberPO> list();
    Long updateStatusById(@Param("status") Integer status, @Param("id") Long id);
    List<MemberPO> listByCompanyIds(@Param("companyIds") List<Long> companyIds, @Param("status") List<Long> status);
}
