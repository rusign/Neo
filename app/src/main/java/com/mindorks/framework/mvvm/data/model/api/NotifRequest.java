package com.mindorks.framework.mvvm.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public final class NotifRequest {

    private NotifRequest() {
        // This class is not publicly instantiable
    }

    public static class NotifSendToken {

        @Expose
        @SerializedName("token")
        private String token;

        @Expose
        @SerializedName("android_token")
        private String android_token;

        public NotifSendToken(String token, String android_token) {
            this.token = token;
            this.android_token = android_token;
        }


        public String getToken() {
            return token;
        }

        public String getAndroid_token() {
            return android_token;
        }
    }
}

