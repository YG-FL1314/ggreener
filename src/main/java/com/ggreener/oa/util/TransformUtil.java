package com.ggreener.oa.util;

import com.alibaba.fastjson.JSONObject;
import com.ggreener.oa.po.CompanyPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by lifu on 2018/10/22.
 * <p>
 * XXX
 */
public class TransformUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransformUtil.class);

    public static CompanyPO transformJsonToCompanyPO(JSONObject json, String uuid) throws Exception {
        CompanyPO company = new CompanyPO();
        try {
            String name = "";
            if (json.containsKey("name")) {
                name = json.getString("name");
            } else {
                throw new Exception("公司名称为空！");
            }
            Date establishedTime = json.getDate("establishedTime");
            float registeredCapital = json.getFloat("registeredCapital");
            String sharesCode = json.getString("sharesCode");
            String patents = json.getString("patents");
            String utilityPatents = json.getString("utilityPatents");
            String softwares = json.getString("softwares");
            float officeArea = json.getFloat("officeArea");
            Long staffNumber = json.getLong("staffNumber");
            Long technicians = json.getLong("technicians");
            String telephone = json.getString("telephone");
            String products = json.getString("products");
            String fax = json.getString("fax");
            String website = json.getString("website");
            String address = json.getString("address");
            company.setName(name);
            company.setEstablishedTime(establishedTime);
            company.setRegisteredCapital(registeredCapital);
            company.setSharesCode(sharesCode);
            company.setPatents(patents);
            company.setUtilityPatents(utilityPatents);
            company.setSoftwares(softwares);
            company.setOfficeArea(officeArea);
            company.setStaffNumber(staffNumber);
            company.setTechnicians(technicians);
            company.setTelephone(telephone);
            company.setFax(fax);
            company.setWebsite(website);
            company.setAddress(address);
            company.setStatus(Constants.STATUS_NORMAL);
            company.setCreateTime(new Date());
            company.setCreateUser(uuid);
            company.setUpdateTime(new Date());
            company.setUpdateUser(uuid);
            company.setProducts(products);
        } catch (Exception e) {
            LOGGER.error("TransformUtil==>transformJsonToCompanyPO: 转化失败，", e);
            throw new Exception(e);
        }
        return company;
    }
}
