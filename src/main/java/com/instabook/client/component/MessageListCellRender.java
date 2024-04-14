package com.instabook.client.component;

import com.instabook.client.context.StorageContext;
import com.instabook.client.model.dos.Message;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class MessageListCellRender extends JLabel implements ListCellRenderer<Message> {

    private int width;

    public MessageListCellRender(int width) {
        this.width = width;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Message> list,
                                                  Message message, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        boolean left = true;
        if (StorageContext.user.getUserId().equals(message.getUserId())) {
            setHorizontalAlignment(SwingConstants.LEFT);
            setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        } else {
            left = false;
            setHorizontalAlignment(SwingConstants.RIGHT);
            setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }
        Integer type = message.getType();
        StringBuilder content = new StringBuilder(message.getContent());

        if (type == -1) {//warning
            content.insert(0, "⚠️ ");
            setHorizontalAlignment(SwingConstants.CENTER);
            setText(content.toString());
        } else {
            if (type == 1) {
                setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
                String usernameHtml = "<div style='margin-bottom: -15;text-align: " + (left ? "left" : "right")
                        + ";'>" + message.getUserName() + "</div>";
                String text = "<html>" + usernameHtml + "<br/><img src=\"" + content +
                        "?x-oss-process=image/resize,m_mfit,s_100\">"
                        + ((message.getDone() == null) ? "⌛" : "") + " <html/>";
                setText(text);
                try {
                    URL headImgUrl = new URL(message.getUserHeadImg(50, 50));
                    setIcon(new ImageIcon(headImgUrl));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                setIconTextGap(30);
            } else if (type == 0) {
                setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
                String usernameHtml = "<div style='margin-bottom: 15;text-align: " + (left ? "left" : "right")
                        + ";'>" + message.getUserName() + "</div>";
                String[] split = content.toString().split("\n");
                content = new StringBuilder();
                int lineLength = 0;
                content.append("<div style='font-family: Courier New, monospace;'>" +
                        "<span style='background-color: lightgray;'>");
                for (String str : split) {
                    content.append("<p style='margin-top: -15;margin-bottom: -15;'>");
                    int lastCharAt = 0;
                    for (int i = 0; i < str.length(); i++) {
                        int length = getCharLength(str.charAt(i));
                        if (lineLength + length > width - 250) {
                            content.append(str, lastCharAt, i);
                            content.append("</p><br/>");
                            lineLength = 0;
                            lastCharAt = i;
                        } else {
                            lineLength += length;
                        }
                    }
                    content.append(str, lastCharAt, str.length());
                    content.append("</p><br/><br/>");
                }
                content.append("</span></div>");
                String contentHtml = content + ((message.getDone() == null) ? "⌛" : "");
                String text = "<html>" + usernameHtml + "<br/>" + contentHtml + "</html>";
                setText(text);
                try {
                    URL headImgUrl = new URL(message.getUserHeadImg(50, 50));
                    setIcon(new ImageIcon(headImgUrl));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                setIconTextGap(30);
            }
        }


        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        setEnabled(list.isEnabled());
        setOpaque(true);

        return this;
    }

    private int getCharLength(char c) {
        if (c >= 'A' && c <= 'Z') { //upper
            return 8;
        } else if (c >= 'a' && c <= 'z') { //under
            return 8;
        } else if (c >= '0' && c <= '9') { //num
            return 7;
        } else if (c >= 32 && c <= 126) { //half
            return 3;
        } else if (c >= 65377 && c <= 65439) { //full
            return 12;
        } else { //other
            return 12;
        }
    }

    private int getTextLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 'A' && c <= 'Z') { //upper
                length += 8;
            } else if (c >= 'a' && c <= 'z') { //under
                length += 7;
            } else if (c >= 32 && c <= 126) { //half
                length += 3;
            } else if (c >= 65377 && c <= 65439) { //full
                length += 12;
            } else { //other
                length += 12;
            }
        }
        return length;
    }
}
