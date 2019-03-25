package com.mindorks.framework.mvvm.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfoResponse {
    @Expose
    @SerializedName("success")
    private Boolean success;

    @Expose
    @SerializedName("content")
    private ApiContent.UserInfo content;


    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        return true;
    }

    public ApiContent.UserInfo getContent() {
        return content;
    }

    public Boolean getSuccess() {
        return success;
    }
}
