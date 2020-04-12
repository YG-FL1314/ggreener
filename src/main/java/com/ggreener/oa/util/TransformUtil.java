package com.ggreener.oa.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ggreener.oa.po.CompanyPO;
import com.ggreener.oa.po.CompanyTagsPO;
import com.ggreener.oa.vo.CompanyVO;
import org.apache.commons.lang3.StringUtils;
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
        if (!json.containsKey("name")) {
            throw new Exception("公司名称为空！");
        }
        CompanyPO company = JSON.parseObject(json.toString(), new TypeReference<CompanyPO>(){});
        if (StringUtils.isNotEmpty(company.getBrief()) && company.getBrief().length() > 1000) {
            throw new Exception("公司简介不能超过1000个字符");
        }
        company.setStatus(Constants.STATUS_NORMAL);
        company.setCreateTime(new Date());
        company.setCreateUser(uuid);
        company.setUpdateTime(new Date());
        company.setUpdateUser(uuid);
        return company;
    }
}
