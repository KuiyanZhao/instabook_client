package org.example;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import okio.ByteString;
import org.example.constant.ApiConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class ChatApp extends JFrame {
    private final JTextField tokenField;
    private final JTextArea chatArea;
    private final JTextField messageField;
    private final JTextField userIdField;

    private final JTextField myUserIdField;
    private WebSocket webSocket;
    private JButton connectButton;

    public ChatApp() {
        setTitle("Chat App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        tokenField = new JTextField();
        connectButton = new JButton("Connect");
        connectButton.addActionListener(e -> {
            String token = tokenField.getText();
            if (token != null && !token.isEmpty()) {
                connectWebSocket(token);
                connectButton.setEnabled(false);
            }
        });
        myUserIdField = new JTextField(10);
        JPanel myUserIdPanel = new JPanel();
        myUserIdPanel.add(new JLabel("User ID:"));
        myUserIdPanel.add(myUserIdField);

        messageField = new JTextField();
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(e -> sendMessage());

        userIdField = new JTextField(10);
        JPanel userIdPanel = new JPanel();
        userIdPanel.add(new JLabel("User ID:"));
        userIdPanel.add(userIdField);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);


        Box inputBox = Box.createVerticalBox();
        inputBox.add(userIdPanel);
        inputBox.add(inputPanel);

        JPanel tokenPanel = new JPanel(new BorderLayout());
        tokenPanel.add(tokenField, BorderLayout.CENTER);
        tokenPanel.add(connectButton, BorderLayout.EAST);

        Box tokenBox = Box.createVerticalBox();
        tokenBox.add(myUserIdPanel);
        tokenBox.add(tokenPanel);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(inputBox, BorderLayout.SOUTH);

        panel.add(tokenBox, BorderLayout.NORTH);

        // 设置面板与窗口的边距
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(panel);
    }

    private void connectWebSocket(String token) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ApiConstants.webSocketUrl)
                .header("authorization", token)
                .build();

        WebSocketListener listener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                chatArea.append("Connected to WebSocket server\n");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {

                if (text.startsWith("{")) {
                    JSONObject jsonObject = JSONObject.parseObject(text);
                    int type = jsonObject.getIntValue("type");

                    String userId = jsonObject.getString("userId");
                    if (!userId.equals(myUserIdField.getText())) {
                        JSONObject message = jsonObject.getJSONObject("message");
                        String content = message.getString("content");
                        chatArea.append("[" + userId + "]: " + content + "\n");
                    }
                } else {
                    chatArea.append("Received: " + text + "\n");
                }

            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                chatArea.append("Received binary message: " + bytes.hex() + "\n");
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                webSocket.close(1000, null);
                chatArea.append("WebSocket closed\n");
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                chatArea.append("WebSocket connection failed: " + t.getMessage() + "\n");
                connectButton.setEnabled(true); // 启用连接按钮
            }
        };

        webSocket = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }

    private void sendMessage() {
        String message = messageField.getText();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userIdField.getText());
        JSONObject messageJson = new JSONObject();
        messageJson.put("content", message);
        jsonObject.put("message", messageJson);
        jsonObject.put("type", 0);
        jsonObject.put("requestId", IdUtil.getSnowflakeNextIdStr());

        if (!message.isEmpty()) {
            webSocket.send(jsonObject.toJSONString());
            chatArea.append("Sent: " + message + "\n");
            messageField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChatApp().setVisible(true));
    }
}


