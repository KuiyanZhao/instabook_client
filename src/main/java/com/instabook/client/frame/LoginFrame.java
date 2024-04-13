package com.instabook.client.frame;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.instabook.client.constant.ApiConstants;
import com.instabook.client.context.StorageContext;
import com.instabook.client.handler.FormatResponseHandler;
import com.instabook.client.model.dos.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Instabook");
        setSize(400, 125);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                User user = login(username, password);

                jumpMenuFrame(user);

            }
        });

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            User user = registerUser(username, password);

            jumpMenuFrame(user);

        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        getContentPane().add(panel, BorderLayout.NORTH);
    }

    private void jumpMenuFrame(User user) {
        if (user == null) {
            return;
        }

        StorageContext.user = user;


        this.dispose();
        SwingUtilities.invokeLater(() -> {
            StorageContext.menuFrame = new MenuFrame(user);
            StorageContext.menuFrame.setVisible(true);
        });
    }

    private User login(String username, String password) {
        String urlString = ApiConstants.loginUrl + "?userName=" + username + "&password=" + password;
        return FormatResponseHandler.handleResponse(HttpUtil.get(urlString), User.class);
    }

    private User registerUser(String username, String password) {
        String urlString = ApiConstants.registerUrl;
        JSONObject requestBody = new JSONObject();
        requestBody.put("userName", username);
        requestBody.put("password", password);
        String s = HttpUtil.post(urlString, requestBody.toJSONString());

        User user = FormatResponseHandler.handleResponse(s, User.class);
        if (user == null) {
            return null;
        }

        JOptionPane.showMessageDialog(null, "register success!",
                "INFORMATION_MESSAGE", JOptionPane.INFORMATION_MESSAGE);

        return user;
    }


}
