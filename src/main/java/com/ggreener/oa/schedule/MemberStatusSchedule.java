package com.ggreener.oa.schedule;

import com.ggreener.oa.mapper.MemberMapper;
import com.ggreener.oa.po.MemberPO;
import com.ggreener.oa.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@EnableScheduling
public class MemberStatusSchedule {
    @Autowired
    private MemberMapper memberMapper;

    @Scheduled(cron = "0 1 0 * * ?")
    public void updateMemberStatus() {
        List<MemberPO> members = memberMapper.list();
        if (!CollectionUtils.isEmpty(members)) {
           for (MemberPO member : members) {
               long currentTime = System.currentTimeMillis();
               if (currentTime > member.getJoiningTime().getTime()
                       && currentTime <= member.getValidityTime().getTime() - Constants.TWO_MONTH_TIME) {
                   if (member.getStatus() != Constants.EFFECTIVE) {
                        memberMapper.updateStatusById(Constants.EFFECTIVE, member.getId());
                   }
               } else if (currentTime > member.getJoiningTime().getTime()
                       && currentTime > member.getValidityTime().getTime() - Constants.TWO_MONTH_TIME
                       && currentTime <= member.getValidityTime().getTime()) {
                   if (member.getStatus() != Constants.EFFECTIVE_SOON) {
                       memberMapper.updateStatusById(Constants.EFFECTIVE_SOON, member.getId());
                   }
               } else if (currentTime > member.getValidityTime().getTime()) {
                   if (member.getStatus() != Constants.NOT_EFFECTIVE) {
                       memberMapper.updateStatusById(Constants.NOT_EFFECTIVE, member.getId());
                   }
               }
           }
        }

    }

    public void startSchedule() {
        updateMemberStatus();
    }
}
