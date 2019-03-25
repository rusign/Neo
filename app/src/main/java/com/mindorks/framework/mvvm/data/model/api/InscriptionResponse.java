package com.mindorks.framework.mvvm.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class InscriptionResponse {


    @Expose
    @SerializedName("success")
    private Boolean success;

    @Expose
    @SerializedName("token")
    private String token;


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

    public String getToken() {
        return token;
    }

    public Boolean getSuccess() {
        return success;
    }

}
