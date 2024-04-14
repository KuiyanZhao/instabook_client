package com.instabook.client.component;

import com.instabook.client.context.StorageContext;
import com.instabook.client.model.dos.UserApplication;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class UserApplicationListCellRender extends JLabel implements ListCellRenderer<UserApplication> {
    @Override
    public Component getListCellRendererComponent(JList<? extends UserApplication> jList,
                                                  UserApplication userApplication, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        if (StorageContext.user.getUserId().equals(userApplication.getUserId())) {
            setText(userApplication.getAnotherUserName());
            try {
                URL headImgUrl = new URL(userApplication.getAnotherUserHeadImg(50, 50));
                setIcon(new ImageIcon(headImgUrl));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            setText(userApplication.getUserName());
            try {
                URL headImgUrl = new URL(userApplication.getUserHeadImg(50, 50));
                setIcon(new ImageIcon(headImgUrl));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
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
