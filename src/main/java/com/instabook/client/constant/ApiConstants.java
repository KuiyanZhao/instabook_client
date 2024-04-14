package com.instabook.client.constant;

public class ApiConstants {

    //    public static String hostAddress = "47.103.205.194:8082";
    public static String hostAddress = "127.0.0.1:8082";
    public static String http = "http://";

    public static String ws = "ws://";

    public static String httpHost = http + hostAddress;

    public static String wsHost = ws + hostAddress;

    public static String loginUrl = httpHost + "/user";

    public static String registerUrl = httpHost + "/user";

    public static String userListUrl = httpHost + "/user/search";

    public static String profileUrl = httpHost + "/user/profile";

    public static String applyFriendUrl = httpHost + "/user-application/apply";

    public static String replyFriendUrl = httpHost + "/user-application/reply";

    public static String applicationPage = httpHost + "/user-application/page";

    public static String userRelationshipListUrl = httpHost + "/user-relationship/list";

    public static String userRelationshipUrl = httpHost + "/user-relationship";

    public static String userRelationshipOperationUrl = httpHost + "/user-relationship/operate";

    public static String messageSendingUrl = httpHost + "/message";

    public static String chatPageUrl = httpHost + "/message/chat/page";

    public static String messagePageUrl = httpHost + "/message/page";

    public static String delMessageUrl = httpHost + "/message";

    public static String webSocketUrl = wsHost + "/websocket";

}
