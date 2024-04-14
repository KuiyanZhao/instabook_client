package com.instabook.client.frame;

import cn.hutool.core.util.IdUtil;
import com.instabook.client.component.MessageListCellRender;
import com.instabook.client.component.MessageListModel;
import com.instabook.client.context.StorageContext;
import com.instabook.client.model.dos.Chat;
import com.instabook.client.model.dos.Message;
import com.instabook.client.model.dos.User;
import com.instabook.client.service.MessageService;
import com.instabook.client.service.impl.MessageServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class ChatFrame extends JFrame {

    private final Chat chatStorage;
    private JTextField messageField;

    private JList<Message> messageJList;

    private JScrollPane jScrollPane;

    private List<Message> messages;

    private MessageService messageService;

    public ChatFrame(Chat chat) {
        chatStorage = chat;
        setTitle("Chat");
        setSize(800, 600);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(500, 300));
        setLocationRelativeTo(null);
        User anotherUser = chat.getAnotherUser();

        //profile panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel avatarLabel = null;
        try {
            URL headImgUrl = new URL(anotherUser.getHeadImg(50, 50));
            avatarLabel = new JLabel(new ImageIcon(headImgUrl));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        JLabel nameLabel = new JLabel(anotherUser.getUserName());
        topPanel.add(avatarLabel);
        topPanel.add(nameLabel);
        add(topPanel, BorderLayout.NORTH);

        //chat panel
        if (messageService == null) {
            messageService = new MessageServiceImpl();
        }
        messages = messageService.getMessages(chat.getChatId());
        messages.forEach(obj -> obj.setDone(1));
        MessageListModel messageListModel = new MessageListModel(messages);
        messageJList = new JList<>(messageListModel){
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
        messageJList.setCellRenderer(new MessageListCellRender(getWidth()));
        jScrollPane = new JScrollPane(messageJList);
//        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalScrollBar = jScrollPane.getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getMaximum());
        });
        add(jScrollPane, BorderLayout.CENTER);

        //message sending panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        messageField = new JTextField();
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                StorageContext.chatFrameMap.remove(chatStorage.getChatId());
                dispose();
            }
        });

        messageJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int index = messageJList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        Rectangle cellBounds = messageJList.getCellBounds(index, index);

                        JOptionPane.showMessageDialog(null,
                                "width:" + cellBounds.getWidth() + "\nwidth:" + getWidth());

                    } else {
                        messageJList.clearSelection();
                    }
                }
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                messageJList.setCellRenderer(new MessageListCellRender(getWidth()));
                messageJList.repaint();

                SwingUtilities.invokeLater(() -> {
                    JScrollBar verticalScrollBar = jScrollPane.getVerticalScrollBar();
                    verticalScrollBar.setValue(verticalScrollBar.getMaximum());
                });
            }
        });

    }

    private void sendMessage() {
        String text = messageField.getText();
        if (text.isEmpty()) {
            return;
        }

        //package message
        Message message = new Message();
        message.setContent(text);
        message.setUserHeadImg(StorageContext.user.getHeadImg());
        message.setAnotherUserId(chatStorage.getAnotherUser().getUserId());
        message.setAnotherUserName(chatStorage.getAnotherUser().getUserName());
        message.setAnotherUserHeadImg(chatStorage.getAnotherUser().getHeadImg());
        message.setUserName(StorageContext.user.getUserName());
        message.setUserId(StorageContext.user.getUserId());
        message.setType(0);
        message.setRequestId(IdUtil.getSnowflakeNextIdStr());

        //update chat label
        messages.add(message);
        MessageListModel messageListModel = new MessageListModel(messages);
        messageJList.setModel(messageListModel);
        messageJList.repaint();
        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalScrollBar = jScrollPane.getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getMaximum());
        });

        StorageContext.menuFrame.sendMessage(message);
        messageField.setText("");
    }

    public void refreshChatLabel(Message message) {
        message.setDone(1);
        boolean find = false;
        if (message.getUserId().equals(StorageContext.user.getUserId())) {//send done
            if (message.getType() != -1) {
                for (Message obj : messages) {
                    if (Objects.equals(message.getRequestId(), obj.getRequestId())) {
                        obj.setDone(1);
                        find = true;
                        break;
                    }
                }
                if (!find) {
                    messages.add(message);
                }
            } else if (message.getType() == -1) {//error
                for (Message obj : messages) {
                    if (Objects.equals(message.getRequestId(), obj.getRequestId())) {
                        obj.setDone(1);
                        find = true;
                        break;
                    }
                }
                if (find) {
                    messages.add(message);
                }
            }
        } else {
            messages.add(message);
        }

        MessageListModel messageListModel = new MessageListModel(messages);
        messageJList.setModel(messageListModel);
        messageJList.repaint();

        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalScrollBar = jScrollPane.getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getMaximum());
        });

    }
}
