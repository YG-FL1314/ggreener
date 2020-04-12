package com.ggreener.oa.mapper;

import com.ggreener.oa.po.InvoicePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InvoiceMapper {
    Long insert(InvoicePO invoice);
    List<InvoicePO> selectByCompanyId(Long companyId);
    Long update(InvoicePO invoice);
    InvoicePO get(Long id);
    Long delete(@Param("id") Long id,
                @Param("updateUser") String updateUser,
                @Param("updateTime") Date updateTime);
}

