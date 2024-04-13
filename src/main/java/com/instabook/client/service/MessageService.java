package com.instabook.client.service;

import com.instabook.client.model.dos.Chat;
import com.instabook.client.model.dos.Message;

import java.util.List;

public interface MessageService {

    List<Chat> getChats();

    List<Message> getMessages(String chatId);

}
