package com.instabook.client.component;

import com.instabook.client.model.dos.UserApplication;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class UserApplicationCellRender extends JLabel implements ListCellRenderer<UserApplication> {
    @Override
    public Component getListCellRendererComponent(JList<? extends UserApplication> jList,
                                                  UserApplication userApplication, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        setText(userApplication.getUserName());
        try {
            URL headImgUrl = new URL(userApplication.getUserHeadImg(50, 50));
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
