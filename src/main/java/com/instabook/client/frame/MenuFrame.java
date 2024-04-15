package com.instabook.client.frame;

import com.alibaba.fastjson.JSON;
import com.instabook.client.component.*;
import com.instabook.client.constant.ApiConstants;
import com.instabook.client.context.StorageContext;
import com.instabook.client.handler.WebSocketHandler;
import com.instabook.client.model.dos.*;
import com.instabook.client.service.FriendService;
import com.instabook.client.service.MessageService;
import com.instabook.client.service.UserApplicationService;
import com.instabook.client.service.impl.FriendServiceImpl;
import com.instabook.client.service.impl.MessageServiceImpl;
import com.instabook.client.service.impl.UserApplicationServiceImpl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MenuFrame extends JFrame {

    private JLabel profilePicLabel;
    private JLabel nameLabel;
    private JButton momentsButton;
    private JButton searchUserButton;
    private JButton friendListButton;
    private JButton chatListButton;
    private JButton userApplicationListButton;

    private String atPage = "chat";

    private JScrollPane atPane;

    private JScrollPane friendScrollPane;
    private JList<UserRelationship> friendList;

    private JScrollPane userApplicationScrollPane;
    private JList<UserApplication> userApplicationList;

    private JScrollPane chatScrollPane;
    private JList<Chat> chatList;
    private MessageService messageService;
    private FriendService friendService;

    private UserApplicationService userApplicationService;
    private WebSocket webSocket;

    public MenuFrame(User user) {
        //screen size
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        StorageContext.screenWidth = (int) dimension.getWidth();
        StorageContext.screenHeight = (int) dimension.getHeight();

        setTitle("Instabook");
        setSize(300, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //socket connect
        connectWebSocket(0);

        //profile
        try {
            URL headImgUrl = new URL(user.getHeadImg(50, 50));
            profilePicLabel = new JLabel(new ImageIcon(headImgUrl));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        nameLabel = new JLabel(user.getUserName());

        // Set layout
        setLayout(new BorderLayout());

        // Top panel for profile pic and name
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(profilePicLabel, BorderLayout.WEST);
        JPanel placeholderPanel = new JPanel();
        topPanel.add(placeholderPanel, BorderLayout.CENTER);
        Font nameLabelFont = nameLabel.getFont();
        Font newFont = new Font(nameLabelFont.getName(), Font.BOLD, 20);
        nameLabel.setFont(newFont);
        placeholderPanel.add(nameLabel);
        add(topPanel, BorderLayout.NORTH);


        // Center panel for recent messages list
        freshChats();


        chatListButton = new JButton("💬");
        friendListButton = new JButton("\uD83D\uDC65");
        userApplicationListButton = new JButton("📝");
        searchUserButton = new JButton("🔍");
        momentsButton = new JButton("🌏");

        // Bottom panel for buttons
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(chatListButton);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(friendListButton);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(userApplicationListButton);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(searchUserButton);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(momentsButton);
        add(leftPanel, BorderLayout.WEST);

        // Add action listeners
        profilePicLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new AvatarFrame(StorageContext.user);
            }
        });

        chatListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //chat list update sync. so it do not need to refresh
                if (!Objects.equals(atPage, "chat")) {
                    atPage = "chat";
                    remove(atPane);
                    atPane = chatScrollPane;
                    add(chatScrollPane, BorderLayout.CENTER);
                    revalidate();
                    repaint();
                }
            }
        });
        momentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle moments button action
                JOptionPane.showMessageDialog(MenuFrame.this,
                        "Redirect to Moments page.");
            }
        });

        searchUserButton.addActionListener(e -> {
            if (StorageContext.userSearchFrame == null) {
                StorageContext.userSearchFrame = new UserSearchFrame();
            }
            StorageContext.userSearchFrame.setVisible(true);
        });

        friendListButton.addActionListener(e -> {
            //create pane
            if (friendScrollPane == null) {
                remove(atPane);
                atPage = "friend";
                if (friendService == null) {
                    friendService = new FriendServiceImpl();
                }
                friendService.refreshFriendList();
                FriendListModel friendListModel = new FriendListModel(StorageContext.friends);
                friendList = new JListZ<>();
                friendList.setCellRenderer(new FriendListCellRender());
                friendList.setModel(friendListModel);
                friendList.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 1) {
                            int index = friendList.locationToIndex(e.getPoint());
                            if (index != -1) {
                                UserRelationship userPicked = friendList.getModel().getElementAt(index);
                                if (SwingUtilities.isLeftMouseButton(e)) {
                                    if (StorageContext.chatFrameMap == null) {
                                        StorageContext.chatFrameMap = new HashMap<String, ChatFrame>();
                                    }
                                    ChatFrame chatFrame = StorageContext.chatFrameMap.get(userPicked.getChatId());
                                    if (chatFrame == null) {
                                        Chat chat = new Chat(userPicked);
                                        chatFrame = new ChatFrame(chat);
                                        StorageContext.chatFrameMap.put(chat.getChatId(), chatFrame);
                                        chatFrame.setVisible(true);
                                    } else {
                                        if (!chatFrame.isVisible()) {
                                            chatFrame.setVisible(true);
                                        }
                                        chatFrame.setExtendedState(JFrame.NORMAL);
                                        chatFrame.toFront();
                                        chatFrame.requestFocus();
                                    }
                                } else {
                                    new UserOperationFrame(userPicked);
                                }
                            } else {
                                friendList.clearSelection();
                            }
                        }
                    }
                });
                friendScrollPane = new JScrollPane(friendList);
                atPane = friendScrollPane;
                add(friendScrollPane, BorderLayout.CENTER);
                friendList.repaint();
                revalidate();
                repaint();
                return;
            }

            if (!Objects.equals(atPage, "friend")) {
                atPage = "friend";
                remove(atPane);
                //refresh friend list
                if (friendService == null) {
                    friendService = new FriendServiceImpl();
                }
                friendService.refreshFriendList();
                friendList.setModel(new FriendListModel(StorageContext.friends));
                friendScrollPane = new JScrollPane(friendList);
                atPane = friendScrollPane;
                add(friendScrollPane, BorderLayout.CENTER);
                revalidate();
                repaint();
                friendList.repaint();
            } else {
                //refresh friend list
                if (friendService == null) {
                    friendService = new FriendServiceImpl();
                }
                friendService.refreshFriendList();
                friendList.setModel(new FriendListModel(StorageContext.friends));
                revalidate();
                repaint();
                friendList.repaint();
            }
        });
        userApplicationListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userApplicationScrollPane == null) {
                    remove(atPane);
                    atPage = "userApplication";
                    if (userApplicationService == null) {
                        userApplicationService = new UserApplicationServiceImpl();
                    }
                    userApplicationService.refreshUserApplicationList();
                    UserApplicationListModel userApplicationListModel = new UserApplicationListModel(StorageContext
                            .userApplications);
                    userApplicationList = new JListZ<>();
                    userApplicationList.setCellRenderer(new UserApplicationListCellRender());
                    userApplicationList.setModel(userApplicationListModel);
                    userApplicationList.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (e.getClickCount() == 1) {
                                int index = userApplicationList.locationToIndex(e.getPoint());
                                if (index != -1) {
                                    UserApplication userApplicationPicked = userApplicationList.getModel()
                                            .getElementAt(index);
                                    new UserApplicationDetailFrame(userApplicationPicked);
                                } else {
                                    userApplicationList.clearSelection();
                                }
                            }
                        }
                    });
                    userApplicationScrollPane = new JScrollPane(userApplicationList);
                    atPane = userApplicationScrollPane;
                    add(userApplicationScrollPane, BorderLayout.CENTER);
                    userApplicationList.repaint();
                    revalidate();
                    repaint();
                    return;
                }

                if (!Objects.equals(atPage, "userApplication")) {
                    atPage = "userApplication";
                    remove(atPane);
                    //refresh userApplication list
                    if (userApplicationService == null) {
                        userApplicationService = new UserApplicationServiceImpl();
                    }
                    userApplicationService.refreshUserApplicationList();
                    userApplicationList.setModel(new UserApplicationListModel(StorageContext.userApplications));
                    userApplicationScrollPane = new JScrollPane(userApplicationList);
                    atPane = userApplicationScrollPane;
                    add(userApplicationScrollPane, BorderLayout.CENTER);
                    revalidate();
                    repaint();
                    userApplicationList.repaint();
                } else {
                    //refresh userApplication list
                    if (userApplicationService == null) {
                        userApplicationService = new UserApplicationServiceImpl();
                    }
                    userApplicationService.refreshUserApplicationList();
                    userApplicationList.setModel(new UserApplicationListModel(StorageContext.userApplications));
                    revalidate();
                    repaint();
                    userApplicationList.repaint();
                }
            }
        });



    }

    public void connectWebSocket(long waitTime) {
        StorageContext.connectTimes++;
        if (waitTime > 0) {
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ApiConstants.webSocketUrl)
                .header("authorization", StorageContext.user.getToken())
                .build();
        webSocket = client.newWebSocket(request, new WebSocketHandler());
        client.dispatcher().executorService().shutdown();
    }

    public void freshChats() {
        //chats
        if (messageService == null) {
            messageService = new MessageServiceImpl();
        }

        List<Chat> chats = messageService.getChats();
        ChatListModel chatListModel = new ChatListModel(chats);
        if (chatList == null) {
            chatList = new JListZ<>();
            chatList.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
            chatList.setCellRenderer(new ChatListCellRender());
            chatList.setModel(chatListModel);
            chatList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 1) {
                        int index = chatList.locationToIndex(e.getPoint());
                        if (index != -1) {
                            Chat clickedChat = chatList.getModel().getElementAt(index);
                            if (StorageContext.chatFrameMap == null) {
                                StorageContext.chatFrameMap = new HashMap<String, ChatFrame>();
                            }
                            ChatFrame chatFrame = StorageContext.chatFrameMap.get(clickedChat.getChatId());
                            if (chatFrame == null) {
                                chatFrame = new ChatFrame(clickedChat);
                                StorageContext.chatFrameMap.put(clickedChat.getChatId(), chatFrame);
                                chatFrame.setVisible(true);
                            } else {
                                if (!chatFrame.isVisible()) {
                                    chatFrame.setVisible(true);
                                }
                                chatFrame.setExtendedState(JFrame.NORMAL);
                                chatFrame.toFront();
                                chatFrame.requestFocus();
                            }
                        } else {
                            chatList.clearSelection();
                        }
                    }
                }
            });
            atPage = "chat";
            chatScrollPane = new JScrollPane(chatList);
            atPane = chatScrollPane;
            add(chatScrollPane, BorderLayout.CENTER);
        } else {
            chatList.setModel(chatListModel);
            if (Objects.equals(atPage, "chat")) {
                revalidate();
                repaint();
                chatList.repaint();
            }
        }
    }

    public void freshChatFrame(Message message) {
        ChatFrame chatFrame = StorageContext.chatFrameMap.get(message.getChatId());
        if (chatFrame == null) {
            return;
        }
        chatFrame.refreshChatLabel(message);
    }

    public void sendMessage(Message message) {
        webSocket.send(JSON.toJSONString(message));
    }

    public void refreshHeadImg() {
        URL url = null;
        try {
            url = new URL(StorageContext.user.getHeadImg(50, 50));
            profilePicLabel.setIcon(new ImageIcon(url));
            profilePicLabel.repaint();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshFriendList() {
        if (Objects.equals(atPage, "friend")) {
            atPage = "friend";
            remove(atPane);
            //refresh friend list
            if (friendService == null) {
                friendService = new FriendServiceImpl();
            }
            friendService.refreshFriendList();
            friendList.setModel(new FriendListModel(StorageContext.friends));
            friendScrollPane = new JScrollPane(friendList);
            atPane = friendScrollPane;
            add(friendScrollPane, BorderLayout.CENTER);
            revalidate();
            repaint();
            friendList.repaint();
        }
    }
}
