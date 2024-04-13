package com.instabook.client.component;

import com.instabook.client.model.dos.UserRelationship;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class FriendListCellRender extends JLabel implements ListCellRenderer<UserRelationship> {
    @Override
    public Component getListCellRendererComponent(JList<? extends UserRelationship> jList,
                                                  UserRelationship userRelationship, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        setText(userRelationship.getAnotherUserName());
        try {
            URL headImgUrl = new URL(userRelationship.getAnotherUserHeadImg(50, 50));
            setIcon(new ImageIcon(headImgUrl));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        setIconTextGap(30);
        if (isSelected) {
            setBackground(jList.getSelectionBackground());
            setForeground(jList.getSelectionForeground());
        } else {
            setBackground(jList.getBackground());
            setForeground(jList.getForeground());
        }

        setEnabled(jList.isEnabled());
        setOpaque(true);

        return this;
    }
}
