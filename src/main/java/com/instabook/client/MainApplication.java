package com.instabook.client;

import com.instabook.client.frame.LoginFrame;

import javax.swing.*;

public class MainApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }

}
