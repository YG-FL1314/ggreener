package com.ggreener.oa.service;

import com.ggreener.oa.exception.ContactException;
import com.ggreener.oa.exception.SessionException;
import com.ggreener.oa.exception.UserException;
import com.ggreener.oa.mapper.ContactMapper;
import com.ggreener.oa.mapper.TagMapper;
import com.ggreener.oa.po.ContactPO;
import com.ggreener.oa.po.TagPO;
import com.ggreener.oa.po.UserPO;
import com.ggreener.oa.util.Constants;
import com.ggreener.oa.util.PasswordUtil;
import com.ggreener.oa.util.UuidUtil;
import com.ggreener.oa.vo.ContactVO;
import com.ggreener.oa.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by lifu on 2018/9/30.
 *
 *
 */
@Service
public class ContactService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private TagMapper tagMapper;

    public ContactVO addContact(ContactPO contact) throws ContactException {
        if (contactMapper.insert(contact) > 0) {
            ContactVO result = new ContactVO();
            BeanUtils.copyProperties(contact, result);
            return result;
        } else {
            throw new ContactException("添加联系人失败！");
        }
    }

    public ContactVO getContact(Long contactId) throws ContactException {
        ContactVO result = new ContactVO();
        ContactPO contact = contactMapper.get(contactId);
        if (null != contact) {
            BeanUtils.copyProperties(contact, result);
        } else {
            throw new ContactException("联系人不存在！");
        }
        return result;
    }

    public ContactVO updateContact(ContactPO contact) throws ContactException {
        if (contactMapper.update(contact) > 0) {
            ContactVO result = new ContactVO();
            BeanUtils.copyProperties(contact, result);
            return result;
        } else {
            throw new ContactException("更新联系人失败！");
        }
    }

    public void deleteContact(Long contactId, String userId) throws ContactException {
        if (contactMapper.delete(contactId, userId, new Date()) <= 0) {
            throw new ContactException("删除联系人失败！");
        }
    }

    public List<ContactVO> list(Long companyId) {
        List<ContactPO> list = contactMapper.selectByCompanyId(companyId);
        List<TagPO> duties = tagMapper.list(new Long(Constants.DUTY_FLAG));
        Map<Long, String> map =  new HashMap<>();
        for (TagPO duty : duties) {
            map.put(duty.getId(), duty.getName());
        }
        List<ContactVO> result = new ArrayList<>();
        if (null != list && list.size() > 0) {
            for (ContactPO contactPO : list) {
                ContactVO contactTmp = new ContactVO();
                BeanUtils.copyProperties(contactPO, contactTmp);
                contactTmp.setDuty(map.get(contactPO.getDutyId()));
                result.add(contactTmp);
            }
        }
        return result;
    }
}
