package com.mindorks.framework.mvvm.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SettingsResponse {

    public final class ApiModifyMdpRespose {
        @Expose
        @SerializedName("success")
        private Boolean success;

        @Expose
        @SerializedName("message")
        private String message;


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

        public String getMessage() {
            return message;
        }

        public Boolean getSuccess() {
            return success;
        }
    }

    public final class ApiModifyResponse {
        @Expose
        @SerializedName("success")
        private Boolean success;

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

        public Boolean getSuccess() {
            return success;
        }
    }
}
