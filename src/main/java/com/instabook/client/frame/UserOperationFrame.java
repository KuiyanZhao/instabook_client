package com.instabook.client.frame;

import com.instabook.client.context.StorageContext;
import com.instabook.client.model.dos.UserRelationship;
import com.instabook.client.service.impl.FriendServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserOperationFrame extends JFrame {


    public UserOperationFrame(UserRelationship userPicked) {
        setTitle("User Operation: " + userPicked.getAnotherUserName());
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton blockButton = new JButton("Block");
        blockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FriendServiceImpl friendService = new FriendServiceImpl();
                UserRelationship userRelationship = friendService.operate(userPicked.getAnotherUserId(), -1);
                StorageContext.menuFrame.refreshFriendList();
                JOptionPane.showMessageDialog(null, userRelationship.getAnotherUserName() + " blocked");
                dispose();
            }
        });

        JButton unblockButton = new JButton("Unblock");
        unblockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FriendServiceImpl friendService = new FriendServiceImpl();
                UserRelationship userRelationship = friendService.operate(userPicked.getAnotherUserId(), 1);
                StorageContext.menuFrame.refreshFriendList();
                JOptionPane.showMessageDialog(null, userRelationship.getAnotherUserName() + " unblocked");
                dispose();
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want " +
                        "to delete this user?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {

                }FriendServiceImpl friendService = new FriendServiceImpl();
                UserRelationship userRelationship = friendService.operate(userPicked.getAnotherUserId(), -2);
                StorageContext.menuFrame.refreshFriendList();
                JOptionPane.showMessageDialog(null, userRelationship.getAnotherUserName() + " deleted");
                dispose();
            }
        });

        if (userPicked.getRelationStatus() == 0) {
            buttonPanel.add(blockButton);
        } else {
            buttonPanel.add(unblockButton);
        }
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
