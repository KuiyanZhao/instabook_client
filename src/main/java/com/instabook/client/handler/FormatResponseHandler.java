package com.instabook.client.handler;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.instabook.client.model.Page;

import javax.swing.*;
import java.util.List;

public class FormatResponseHandler {

    public static <T> T handleResponse(String response, Class<T> clazz) {
        JSONObject jsonObject = JSON.parseObject(response);
        if (jsonObject.getInteger("code") == 200) {
            return JSON.parseObject(jsonObject.getString("data"), clazz);
        } else {
            String msg = jsonObject.getString("msg");
            if (StrUtil.isBlank(msg)) {
                msg = "system error!";
            }
            JOptionPane.showMessageDialog(null, msg, "ERROR_MESSAGE",JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public static <T> List<T> handleListResponse(String response, Class<T> clazz) {
        JSONObject jsonObject = JSON.parseObject(response);
        if (jsonObject.getInteger("code") == 200) {
            return JSON.parseArray(jsonObject.getString("data"), clazz);
        } else {
            String msg = jsonObject.getString("msg");
            if (StrUtil.isBlank(msg)) {
                msg = "system error!";
            }
            JOptionPane.showMessageDialog(null, msg, "ERROR_MESSAGE",JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public static <T> Page<T> handlePageResponse(String response, Class<T> clazz) {
        JSONObject jsonObject = JSON.parseObject(response);
        if (jsonObject.getInteger("code") == 200) {
            JSONObject data = jsonObject.getJSONObject("data");
            List<T> records = JSON.parseArray(data.getString("records"), clazz);
            long total = data.getLongValue("total");
            long current = data.getLongValue("current");
            long size = data.getLongValue("size");

            return new Page<>(total, current, size, records);
        } else {
            String msg = jsonObject.getString("msg");
            if (StrUtil.isBlank(msg)) {
                msg = "system error!";
            }
            JOptionPane.showMessageDialog(null, msg, "ERROR_MESSAGE",JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}
