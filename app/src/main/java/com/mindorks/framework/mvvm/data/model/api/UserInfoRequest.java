package com.mindorks.framework.mvvm.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class UserInfoRequest {

    private UserInfoRequest() {
        // This class is not publicly instantiable
    }

    public static class ApiUserInfoRequest {

        @Expose
        @SerializedName("token")
        private String token;


        public ApiUserInfoRequest(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }
}
