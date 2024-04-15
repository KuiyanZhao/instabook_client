package com.instabook.client.service;

import com.instabook.client.model.dos.Chat;
import com.instabook.client.model.dos.Message;
import com.instabook.client.model.dos.MessageFile;

import java.io.File;
import java.util.List;

public interface MessageService {

    List<Chat> getChats();

    List<Message> getMessages(String chatId);

    MessageFile uploadImageMessage(File selectedFile, String requestId);

    Message deleteMessage(Message messagePicked);
}
