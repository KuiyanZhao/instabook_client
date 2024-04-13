package com.instabook.client.model.dos;

import com.instabook.client.context.StorageContext;

import java.util.List;

public class Chat {

    private String chatId;

    private List<Message> messages;

    private User anotherUser;

    public Chat(Message message) {
        this.chatId = message.getChatId();
        this.messages = List.of(message);
        this.anotherUser = new User();
        if (StorageContext.user.getUserId().equals(message.getUserId())) {
            this.anotherUser.setUserName(message.getAnotherUserName());
            this.anotherUser.setHeadImg(message.getAnotherUserHeadImg());
            this.anotherUser.setUserId(message.getAnotherUserId());
        } else {
            this.anotherUser.setUserName(message.getUserName());
            this.anotherUser.setHeadImg(message.getUserHeadImg());
            this.anotherUser.setUserId(message.getUserId());
        }
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public User getAnotherUser() {
        return anotherUser;
    }

    public void setAnotherUser(User anotherUser) {
        this.anotherUser = anotherUser;
    }
}
