package com.instabook.client;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.instabook.client.constant.ApiConstants;
import com.instabook.client.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextArea userDetailsArea;

    public MainFrame() {
        setTitle("Login Page");
        setSize(400, 300);
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
        userDetailsArea = new JTextArea();

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    JSONObject jsonObject = displayDetail(login(username, password));
                    if (jsonObject != null) {
                        userDetailsArea.setText(jsonObject.toJSONString());
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    userDetailsArea.setText("Error occurred: " + ex.getMessage());
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    JSONObject jsonObject = displayDetail(registerUser(username, password));
                    if (jsonObject != null) {
                        userDetailsArea.setText(jsonObject.toJSONString());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    userDetailsArea.setText("Error occurred: " + ex.getMessage());
                }
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(userDetailsArea), BorderLayout.CENTER);
    }

    private JSONObject displayDetail(JSONObject jsonResponse) {
        int code = jsonResponse.getIntValue("code");
        if (code == 200) {//success
            return jsonResponse.getJSONObject("data");
        } else {
            userDetailsArea.setText("error:" + jsonResponse.getString("msg"));
            return null;
        }
    }

    private JSONObject login(String username, String password) throws IOException {
        String urlString = ApiConstants.loginUrl + "?userName=" + username + "&password=" + password;
        String s = HttpUtil.get(urlString);
        JSONObject response = JSON.parseObject(s);

        return response;
    }

    private void displayUserDetails(User user) {
        if (user != null) {
            userDetailsArea.setText(JSON.toJSONString(user));
        } else {
            userDetailsArea.setText("User not found");
        }
    }

    private JSONObject registerUser(String username, String password) throws IOException {
        String urlString = "http://47.103.205.194:8082/user";
        JSONObject requestBody = new JSONObject();
        requestBody.put("userName", username);
        requestBody.put("password", password);
        String s = HttpUtil.post(urlString, requestBody.toJSONString());
        JSONObject response = JSON.parseObject(s);

        return response;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}

