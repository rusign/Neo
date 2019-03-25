package com.mindorks.framework.mvvm.data.remote;

import com.mindorks.framework.mvvm.BuildConfig;

public final class ApiEndPoint {

    public static final String ENDPOINT_BLOG = BuildConfig.BASE_URL + "/5926ce9d11000096006ccb30";

    public static final String ENDPOINT_OPEN_SOURCE = BuildConfig.BASE_URL + "/5926c34212000035026871cd";

    public static final  String ENDPOINT_SERVER_NEO = "https://api.neo.ovh/";

    public static final String ENDPOINT_SERVER_LOGIN = ENDPOINT_SERVER_NEO + "account/login";

    public static final String ENDPOINT_SERVER_CREATE = ENDPOINT_SERVER_NEO + "account/create";

    public static final String ENDPOINT_USER_INFO = ENDPOINT_SERVER_NEO + "user/info";

    public static final String ENDPOINT_CIRCLE_LIST = ENDPOINT_SERVER_NEO + "circle/list";

    public static final String ENDPOINT_CIRCLE_CREATE = ENDPOINT_SERVER_NEO + "circle/create";

    public static final String ENDPOINT_CIRCLE_JOIN = ENDPOINT_SERVER_NEO + "circle/join";

    public static final String ENDPOINT_CIRCLE_REJECT = ENDPOINT_SERVER_NEO + "circle/reject";

    public static final String ENDPOINT_CIRCLE_QUIT = ENDPOINT_SERVER_NEO + "circle/quit";

    public static final String ENDPOINT_CIRCLE_INFO = ENDPOINT_SERVER_NEO + "circle/info";

    public static final String ENDPOINT_CIRCLE_INVITE = ENDPOINT_SERVER_NEO + "circle/invite";

    public static final String ENDPOINT_CONVERSATION_INFO = ENDPOINT_SERVER_NEO + "conversation/info";

    public static final String ENDPOINT_CONVERSATION_PIC = ENDPOINT_SERVER_NEO + "media/retrieve";

    public static final String ENDPOINT_CONVERSATION_LIST = ENDPOINT_SERVER_NEO + "conversation/list";

    public static final String ENDPOINT_CONVERSATION_INVITE = ENDPOINT_SERVER_NEO + "conversation/invite";

    public static final String ENDPOINT_CONVERSATION_QUIT = ENDPOINT_SERVER_NEO + "conversation/quit";

    public static final String ENDPOINT_CONVERSATION_CREATE = ENDPOINT_SERVER_NEO + "message/first-message";

    public static final String ENDPOINT_MESSAGE_INFO = ENDPOINT_SERVER_NEO + "message/info";

    public static final String ENDPOINT_UPLOAD_MEDIA = ENDPOINT_SERVER_NEO + "media/upload";

    public static final String ENDPOINT_MESSAGE_SEND = ENDPOINT_SERVER_NEO + "message/send";

    public static final String ENDPOINT_CONVERSATION_UPDATE = ENDPOINT_SERVER_NEO + "/conversation/update";

    public static final String ENDPOINT_ANDROID_TOKEN = ENDPOINT_SERVER_NEO + "/android/token";

    public static final String ENDPOINT_MODIFY_PASS = ENDPOINT_SERVER_NEO + "account/modify/password";

    public static final String ENDPOINT_MODIFY = ENDPOINT_SERVER_NEO + "account/modify  ";





    private ApiEndPoint() {
        // This class is not publicly instantiable
    }
}
