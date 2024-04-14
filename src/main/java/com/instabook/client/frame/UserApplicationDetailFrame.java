package com.instabook.client.frame;

import com.instabook.client.context.StorageContext;
import com.instabook.client.model.dos.UserApplication;
import com.instabook.client.service.UserApplicationService;
import com.instabook.client.service.impl.UserApplicationServiceImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserApplicationDetailFrame extends JFrame {
    public UserApplicationDetailFrame(UserApplication userApplication) {
        setTitle("User Application Detail");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        if (StorageContext.user.getUserId().equals(userApplication.getUserId())) {
            //show the reply status of the other user
            JLabel replyStatusLabel = new JLabel("Reply Status of the Other User: ");
            JLabel replyStatusText = new JLabel(getReplyStatusText(userApplication.getStatus()));
            panel.add(replyStatusLabel);
            panel.add(replyStatusText);
        } else {
            if (userApplication.getStatus() == 0) {
                //show the pass / refuse button
                JButton passButton = new JButton("Pass");
                passButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        UserApplicationService userApplicationService = new UserApplicationServiceImpl();
                        userApplicationService.replyFriend(userApplication.getApplicationId(), 1);
                        JOptionPane.showMessageDialog(null, "Passed");
                        dispose();
                    }
                });
                panel.add(passButton);

                JButton refuseButton = new JButton("Refuse");
                refuseButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        UserApplicationService userApplicationService = new UserApplicationServiceImpl();
                        userApplicationService.replyFriend(userApplication.getApplicationId(), -1);
                        JOptionPane.showMessageDialog(null, "Refused");
                        dispose();
                    }
                });
                panel.add(refuseButton);
            } else {
                //show the reply status of myself
                JLabel myReplyStatusLabel = new JLabel("My Reply Status: ");
                JLabel myReplyStatusText = new JLabel(getReplyStatusText(userApplication.getStatus()));
                panel.add(myReplyStatusLabel);
                panel.add(myReplyStatusText);
            }
        }
        add(panel);
        setVisible(true);

    }
    private String getReplyStatusText(int status) {
        return switch (status) {
            case -1 -> "Disapproved";
            case 0 -> "Pending";
            case 1 -> "Approved";
            default -> "Unknown";
        };
    }
}
