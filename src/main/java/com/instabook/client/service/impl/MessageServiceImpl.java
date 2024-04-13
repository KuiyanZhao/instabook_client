package com.instabook.client.service.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.instabook.client.constant.ApiConstants;
import com.instabook.client.context.StorageContext;
import com.instabook.client.handler.FormatResponseHandler;
import com.instabook.client.model.Page;
import com.instabook.client.model.dos.Chat;
import com.instabook.client.model.dos.Message;
import com.instabook.client.service.MessageService;

import java.util.List;

public class MessageServiceImpl implements MessageService {


    @Override
    public List<Chat> getChats() {
        try (HttpResponse response = HttpUtil.createGet(ApiConstants.chatPageUrl + "?pageSize=100")
                .header("Authorization", StorageContext.user.getToken()).execute()) {
            Page<Message> messages = FormatResponseHandler.handlePageResponse(response.body(), Message.class);
            if (messages == null) {
                return null;
            }
            return messages.getRecords().stream().map(Chat::new).toList();
        }
    }

    @Override
    public List<Message> getMessages(String chatId) {
        try (HttpResponse response = HttpUtil.createGet(ApiConstants.messagePageUrl + "?chatId=" + chatId)
                .header("Authorization", StorageContext.user.getToken()).execute()) {
            Page<Message> messages = FormatResponseHandler.handlePageResponse(response.body(), Message.class);
            if (messages == null) {
                return null;
            }
            return messages.getRecords();
        }
    }
}
