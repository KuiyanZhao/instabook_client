package com.instabook.client.frame;

import com.instabook.client.context.StorageContext;
import com.instabook.client.model.dos.Chat;
import com.instabook.client.model.dos.User;
import com.instabook.client.model.dos.UserRelationship;
import com.instabook.client.service.FriendService;
import com.instabook.client.service.UserApplicationService;
import com.instabook.client.service.impl.FriendServiceImpl;
import com.instabook.client.service.impl.UserApplicationServiceImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class UserApplicationFrame extends JFrame {
    public UserApplicationFrame(User user) {
        setTitle("User Application Frame");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        FriendService friendService = new FriendServiceImpl();
        UserRelationship userRelationship = friendService.getRelationship(user.getUserId());
        if (userRelationship == null || userRelationship.getFriendStatus() == 0) {
            JButton applyButton = new JButton("Apply");
            applyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    UserApplicationService userApplicationService = new UserApplicationServiceImpl();
                    userApplicationService.applyFriend(user.getUserId());
                    JOptionPane.showMessageDialog(null, "Apply Success");
                }
            });
            panel.add(applyButton);
        } else {
            JButton chatButton = new JButton("Chat");
            chatButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (StorageContext.chatFrameMap == null) {
                        StorageContext.chatFrameMap = new HashMap<String, ChatFrame>();
                    }
                    ChatFrame chatFrame = StorageContext.chatFrameMap.get(user.getChatId());
                    if (chatFrame == null) {
                        Chat chat = new Chat(user);
                        chatFrame = new ChatFrame(chat);
                        StorageContext.chatFrameMap.put(user.getChatId(), chatFrame);
                        chatFrame.setVisible(true);
                    } else {
                        if (!chatFrame.isVisible()) {
                            chatFrame.setVisible(true);
                        }
                        chatFrame.toFront();
                        chatFrame.requestFocus();
                    }
                }
            });
            panel.add(chatButton);
        }
        add(panel);
        setVisible(true);
    }
}
