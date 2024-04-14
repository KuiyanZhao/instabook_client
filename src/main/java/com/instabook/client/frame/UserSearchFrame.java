package com.instabook.client.frame;

import com.instabook.client.component.UserListCellRender;
import com.instabook.client.component.UserListModel;
import com.instabook.client.model.dos.User;
import com.instabook.client.service.UserService;
import com.instabook.client.service.impl.UserServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class UserSearchFrame extends JFrame {

    private JTextField searchField;
    private JButton searchButton;
    private JList<User> userList;

    private List<User> users = new ArrayList<>();

    private UserService userService;

    public UserSearchFrame() {
        setTitle("user search");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userService == null) {
                    userService = new UserServiceImpl();
                }
                String username = searchField.getText();
                users = userService.getList(username);
                userList.setModel(new UserListModel(users));
                userList.repaint();
            }
        });
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        // 创建用户列表
        userList = new JList<>() {
            @Override
            public int locationToIndex(Point location) {
                int index = super.locationToIndex(location);
                if (index != -1 && !getCellBounds(index, index).contains(location)) {
                    return -1;
                } else {
                    return index;
                }
            }
        };
        userList.setCellRenderer(new UserListCellRender());
        userList.setModel(new UserListModel(users));
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        userList.setVisibleRowCount(-1);
        userList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = userList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        User user = users.get(index);
                        new UserApplicationFrame(user);
                    }
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(userList);
        add(scrollPane, BorderLayout.CENTER);
    }

}
