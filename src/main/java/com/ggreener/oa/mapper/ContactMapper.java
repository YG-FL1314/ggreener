package com.ggreener.oa.mapper;

import com.ggreener.oa.po.ContactPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ContactMapper {
    Long insert(ContactPO contact);
    List<ContactPO> selectByCompanyId(Long companyId);
    Long delete(@Param("contactId")Long contactId,
                @Param("updateUser")String updateUser,
                @Param("updateTime")Date updateTime);
    Long update(ContactPO contact);
    ContactPO get(Long contactId);
}
