package com.instabook.client.component;

import com.instabook.client.model.dos.Chat;
import com.instabook.client.model.dos.Message;
import com.instabook.client.model.dos.User;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class ChatListCellRender extends JLabel implements ListCellRenderer<Chat> {
    @Override
    public Component getListCellRendererComponent(JList<? extends Chat> list,
                                                  Chat chat, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        User user = chat.getAnotherUser();
        Message newestMessage = chat.getMessages().get(chat.getMessages().size() - 1);
        Integer type = newestMessage.getType();
        String content = newestMessage.getContent();
        if (type == 1) {
            content = "[picture]";
        }

        if (content.length() > 20) {
            content = content.substring(0, 20) + "...";
        }
        String text = "<html>" + user.getUserName() + "<br/>" +
                content + " <html/>";
        setText(text);
        try {
            URL headImgUrl = new URL(chat.getAnotherUser().getHeadImg(50, 50));
            setIcon(new ImageIcon(headImgUrl));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        setIconTextGap(30);
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        setEnabled(list.isEnabled());
        setOpaque(true);

        return this;


    }
}
