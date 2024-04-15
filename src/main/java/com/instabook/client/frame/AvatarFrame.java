package com.instabook.client.frame;

import com.instabook.client.context.StorageContext;
import com.instabook.client.model.dos.User;
import com.instabook.client.service.UserService;
import com.instabook.client.service.impl.UserServiceImpl;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class AvatarFrame extends JFrame {

    private JLabel currentAvatarLabel;
    private JButton uploadButton;

    public AvatarFrame(User user) {
        setTitle("user detail");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try {
            URL url = new URL(user.getHeadImg(300, 300));
            currentAvatarLabel = new JLabel(new ImageIcon(url));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        add(currentAvatarLabel, BorderLayout.CENTER);
        uploadButton = new JButton("upload");
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showOpenDialog(AvatarFrame.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                UserService userService = new UserServiceImpl();
                User responseUser = userService.uploadHeadImg(selectedFile);
                responseUser.setToken(StorageContext.user.getToken());
                StorageContext.user = responseUser;
                try {
                    URL url = new URL(responseUser.getHeadImg(300, 300));
                    currentAvatarLabel.setIcon(new ImageIcon(url));
                    currentAvatarLabel.repaint();
                    StorageContext.menuFrame.refreshHeadImg();
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
                revalidate();
                repaint();
            }
        });
        add(uploadButton, BorderLayout.SOUTH);
        setVisible(true);
    }

}
