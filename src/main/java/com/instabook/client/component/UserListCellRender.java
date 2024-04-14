package com.instabook.client.component;

import com.instabook.client.model.dos.User;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class UserListCellRender extends JLabel implements ListCellRenderer<User> {
    @Override
    public Component getListCellRendererComponent(JList<? extends User> jList,
                                                  User user, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        setText(user.getUserName());
        try {
            URL headImgUrl = new URL(user.getHeadImg(50, 50));
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
