package com.instabook.client.frame;

import cn.hutool.core.util.IdUtil;
import com.instabook.client.component.JListZ;
import com.instabook.client.component.MessageListCellRender;
import com.instabook.client.component.MessageListModel;
import com.instabook.client.context.StorageContext;
import com.instabook.client.model.dos.Chat;
import com.instabook.client.model.dos.Message;
import com.instabook.client.model.dos.MessageFile;
import com.instabook.client.model.dos.User;
import com.instabook.client.service.MessageService;
import com.instabook.client.service.impl.MessageServiceImpl;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        messageJList = new JListZ<>();
        messageJList.setModel(messageListModel);
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
        JButton imgButton = new JButton("image");
        imgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendImageMessage();
            }
        });
        messageField = new JTextField();
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        bottomPanel.add(imgButton, BorderLayout.WEST);
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
                        Message messagePicked = messageJList.getModel().getElementAt(index);
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            if (messagePicked.getType() == 1) {//image
                                try {
                                    URL url = new URL(messagePicked.getContent());
                                    ImageIcon imageIcon = new ImageIcon(url);
                                    JLabel imageLabel = new JLabel(imageIcon);
                                    JOptionPane.showMessageDialog(null, imageLabel, "Image Viewer", JOptionPane.PLAIN_MESSAGE);
                                } catch (MalformedURLException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        } else {
                            if (SwingUtilities.isRightMouseButton(e)) {
                                JPopupMenu popupMenu = new JPopupMenu();
                                JMenuItem deleteItem = new JMenuItem("Delete");
                                deleteItem.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this message?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                                        if (choice == JOptionPane.YES_OPTION) {
                                            if (messageService == null) {
                                                messageService = new MessageServiceImpl();
                                            }
                                            messageService.deleteMessage(messagePicked);
                                            ChatFrame chatFrame = StorageContext.chatFrameMap.get(messagePicked.getChatId());
                                            if (chatFrame != null) {
                                                chatFrame.messages = chatFrame.messages.stream()
                                                        .filter(obj -> !obj.getMessageId()
                                                                .equals(messagePicked.getMessageId()))
                                                        .collect(Collectors.toList());
                                                chatFrame.messageJList.setModel(new MessageListModel(chatFrame.messages));
                                                chatFrame.messageJList.repaint();
                                                SwingUtilities.invokeLater(() -> {
                                                    JScrollBar verticalScrollBar = jScrollPane.getVerticalScrollBar();
                                                    verticalScrollBar.setValue(verticalScrollBar.getMaximum());
                                                });
                                            }
                                        }
                                    }
                                });
                                popupMenu.add(deleteItem);
                                popupMenu.show(messageJList, e.getX(), e.getY());
                            }
                        }
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

    private void sendImageMessage() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(ChatFrame.this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (messageService == null) {
                messageService = new MessageServiceImpl();
            }
            String requestId = IdUtil.getSnowflakeNextIdStr();
            MessageFile messageFile = messageService.uploadImageMessage(selectedFile, requestId);
            Message message = new Message();
            message.setContent(messageFile.getUrl());
            message.setUserHeadImg(StorageContext.user.getHeadImg());
            message.setAnotherUserId(chatStorage.getAnotherUser().getUserId());
            message.setAnotherUserName(chatStorage.getAnotherUser().getUserName());
            message.setAnotherUserHeadImg(chatStorage.getAnotherUser().getHeadImg());
            message.setUserName(StorageContext.user.getUserName());
            message.setUserId(StorageContext.user.getUserId());
            message.setType(1);
            message.setRequestId(requestId);

            messages.add(message);
            MessageListModel messageListModel = new MessageListModel(messages);
            messageJList.setModel(messageListModel);
            messageJList.repaint();
            SwingUtilities.invokeLater(() -> {
                JScrollBar verticalScrollBar = jScrollPane.getVerticalScrollBar();
                verticalScrollBar.setValue(verticalScrollBar.getMaximum());
            });

            StorageContext.menuFrame.sendMessage(message);
        }
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
