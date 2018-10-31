package com.ggreener.oa.service;

import com.ggreener.oa.exception.MemberException;
import com.ggreener.oa.mapper.MemberMapper;
import com.ggreener.oa.po.MemberPO;
import com.ggreener.oa.vo.MemberVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lifu on 2018/9/30.
 *
 *
 */
@Service
public class MemberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberService.class);

    @Autowired
    private MemberMapper memberMapper;

    public MemberVO addMember(MemberPO member) throws MemberException {
        if (memberMapper.insert(member) > 0) {
            MemberVO result = new MemberVO();
            BeanUtils.copyProperties(member, result);
            return result;
        } else {
            throw new MemberException("添加会员信息失败！");
        }
    }

    public MemberVO getMember(Long memberId) throws MemberException {
        MemberVO result = new MemberVO();
        MemberPO member = memberMapper.get(memberId);
        if (null != member) {
            BeanUtils.copyProperties(member, result);
        } else {
            throw new MemberException("会员信息不存在！");
        }
        return result;
    }

    public MemberVO updateMember(MemberPO member) throws MemberException {
        if (memberMapper.update(member) > 0) {
            MemberVO result = new MemberVO();
            BeanUtils.copyProperties(member, result);
            return result;
        } else {
            throw new MemberException("更新会员信息失败！");
        }
    }

    public MemberVO getMemberByCompanyId(Long companyId) {
        MemberPO memberPO = memberMapper.selectByCompanyId(companyId);
        MemberVO result = new MemberVO();
        if (null != memberPO ) {
            BeanUtils.copyProperties(memberPO, result);
        }
        return result;
    }
}
