package com.mindorks.framework.mvvm.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public final class CirclesResponse {

    public final class CircleListResponse {

        @Expose
        @SerializedName("success")
        private Boolean success;

        @Expose
        @SerializedName("content")
        private List<ApiContent.CircleListContent> content;


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

        public List<ApiContent.CircleListContent> getContent() {
            return content;
        }

        public List<Integer> getIds() {

            List<Integer> list = new ArrayList<Integer>();


            for (ApiContent.CircleListContent item : content) {
                list.add(item.id);
            }
            return list;
        }

        public Boolean getSuccess() {
            return success;
        }
    }

    public final class CircleContactsResponse{

        @Expose
        @SerializedName("success")
        private Boolean success;

        @Expose
        @SerializedName("content")
        private ApiContent.ClircleContactsInfo content;


        public ApiContent.ClircleContactsInfo getContent() {
            return content;
        }

        public Boolean getSuccess() {
            return success;
        }
    }

    public final class CircleCreateResponse{

        @Expose
        @SerializedName("success")
        private Boolean success;

        @Expose
        @SerializedName("content")
        private ApiContent.CircleListContent content;


        public ApiContent.CircleListContent getContent() {
            return content;
        }

        public Boolean getSuccess() {
            return success;
        }
    }

    public final class CircleInviteResponse{

        @Expose
        @SerializedName("success")
        private Boolean success;

        public Boolean getSuccess() {
            return success;
        }
    }

    public final class CircleJoinRejectResponse {
        @Expose
        @SerializedName("success")
        private Boolean success;

        public Boolean getSuccess() {
            return success;
        }
    }

    public final class CircleQuitResponse {
        @Expose
        @SerializedName("success")
        private Boolean success;

        public Boolean getSuccess() {
            return success;
        }
    }
}

