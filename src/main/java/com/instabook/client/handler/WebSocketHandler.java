package com.instabook.client.handler;

import com.alibaba.fastjson.JSON;
import com.instabook.client.context.StorageContext;
import com.instabook.client.model.dos.Message;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class WebSocketHandler extends WebSocketListener {

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        StorageContext.connectTimes = 0;
        System.out.println("login success!");
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, String text) {

        if (text.startsWith("{")) {
            Message message = JSON.parseObject(text, Message.class);
            StorageContext.menuFrame.freshChats();
            StorageContext.menuFrame.freshChatFrame(message);
        }
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, ByteString bytes) {
        System.out.println("Received binary message: " + bytes.hex() + "\n");
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, @NotNull String reason) {
        webSocket.close(1000, null);
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, Response response) {
        JOptionPane.showMessageDialog(null, "internet error, retry after " +
                        StorageContext.connectTimes * 5 +
                        " seconds...", "error",
                JOptionPane.ERROR_MESSAGE);
        StorageContext.menuFrame.connectWebSocket(StorageContext.connectTimes * 5000L);
    }

}
