package com.instabook.client.component;

import com.instabook.client.model.dos.Chat;

import javax.swing.*;
import java.util.List;

public class ChatListModel extends AbstractListModel<Chat> {

    private final List<Chat> chatList;

    public ChatListModel (List<Chat> chatList) {
        this.chatList = chatList;
    }
    @Override
    public int getSize() {
        return chatList.size();
    }

    @Override
    public Chat getElementAt(int i) {
        return chatList.get(i);
    }
}
