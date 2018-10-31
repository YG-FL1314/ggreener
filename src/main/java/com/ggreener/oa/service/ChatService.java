package com.ggreener.oa.service;

import com.ggreener.oa.exception.ChatException;
import com.ggreener.oa.mapper.ChatMapper;
import com.ggreener.oa.po.ChatPO;
import com.ggreener.oa.vo.ChatVO;
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
public class ChatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private ChatMapper chatMapper;

    public ChatVO addChat(ChatPO chat) throws ChatException {
        if (chatMapper.insert(chat) > 0) {
            ChatVO result = new ChatVO();
            BeanUtils.copyProperties(chat, result);
            return result;
        } else {
            throw new ChatException("添加互动信息失败！");
        }
    }

    public ChatVO getChat(Long chatId) throws ChatException {
        ChatVO result = new ChatVO();
        ChatPO chat = chatMapper.get(chatId);
        if (null != chat) {
            BeanUtils.copyProperties(chat, result);
        } else {
            throw new ChatException("互动信息不存在！");
        }
        return result;
    }

    public ChatVO updateChat(ChatPO chat) throws ChatException {
        if (chatMapper.update(chat) > 0) {
            ChatVO result = new ChatVO();
            BeanUtils.copyProperties(chat, result);
            return result;
        } else {
            throw new ChatException("更新互动信息失败！");
        }
    }

    public void deleteChat(Long id, String userId) throws ChatException {
        if (chatMapper.delete(id, userId, new Date()) <= 0) {
            throw new ChatException("删除互动信息失败！");
        }
    }

    public List<ChatVO> list(Long companyId) {
        List<ChatPO> list = chatMapper.selectByCompanyId(companyId);
        List<ChatVO> result = new ArrayList<>();
        if (null != list && list.size() > 0) {
            for (ChatPO chatPO : list) {
                ChatVO chatTmp = new ChatVO();
                BeanUtils.copyProperties(chatPO, chatTmp);
                result.add(chatTmp);
            }
        }
        return result;
    }
}
