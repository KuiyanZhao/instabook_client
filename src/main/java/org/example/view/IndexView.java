package org.example.view;

import org.example.MainFrame;

import javax.swing.*;
import java.awt.*;

public class IndexView extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public IndexView() {
        setTitle("Login Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 6; // 设置组件所在列（从0开始计数）
        gbc.gridy = 6; // 设置组件所在行（从0开始计数）
        gbc.anchor = GridBagConstraints.WEST;
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");


        panel.add(usernameLabel, gbc);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        getContentPane().add(panel, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new IndexView().setVisible(true);
            }
        });
    }

}
