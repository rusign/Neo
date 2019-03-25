package com.mindorks.framework.mvvm.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public final class ConversationResponse {

    public final class ConversationInfo{
        @Expose
        @SerializedName("success")
        private Boolean success;

        @Expose
        @SerializedName("content")
        private ApiContent.ConversationInfoContent content;


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

        public ApiContent.ConversationInfoContent getContent() {
            return content;
        }

        public Boolean getSuccess() {
            return success;
        }
    }

    public final class ConversationMessageInfo{
        @Expose
        @SerializedName("success")
        private Boolean success;

        @Expose
        @SerializedName("content")
        private ApiContent.MessageContent content;


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

        public ApiContent.MessageContent getContent() {
            return content;
        }

        public Boolean getSuccess() {
            return success;
        }
    }


    public final class ConversationPic{
        @Expose
        @SerializedName("success")
        private Boolean success;

        @Expose
        @SerializedName("data")
        private String data;


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

        public String getData() {
            return data;
        }

        public Boolean getSuccess() {
            return success;
        }
    }

    public final class ConversationUploadMedia{
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

    public final class ConversationMessageSend{
        @Expose
        @SerializedName("success")
        private Boolean success;

        @Expose
        @SerializedName("message_id")
        private int message_id;

        @Expose
        @SerializedName("media_list")
        private List<ApiContent.MessageMedia> media_list;

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

        public  int getMessage_id(){
            return message_id;
        }

        public List<ApiContent.MessageMedia> getMedia_list() {
            return media_list;
        }
    }


    public final class ConversationList{
        @Expose
        @SerializedName("success")
        private Boolean success;

        @Expose
        @SerializedName("content")
        private List<ApiContent.ConversationListContent> content;


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

        public List<ApiContent.ConversationListContent> getContent() {
            return content;
        }

        public List<Integer> getIds() {

            List<Integer> list = new ArrayList<Integer>();

            for(ApiContent.ConversationListContent item : content){
                list.add(item.id);
            }
            return list;
        }


        public Boolean getSuccess() {
            return success;
        }
    }

    public final class ConversationInvite{
        @Expose
        @SerializedName("success")
        private Boolean success;

        public Boolean getSuccess() {
            return success;
        }
    }

    public final class ConversationCreate{
        @Expose
        @SerializedName("success")
        private Boolean success;

        @Expose
        @SerializedName("conversation_id")
        private int conversation_id;

        public int getConversation_id() {return conversation_id;}

        public Boolean getSuccess() {
            return success;
        }
    }

    public final class ConversationUpdate{
        @Expose
        @SerializedName("success")
        private Boolean success;

        public Boolean getSuccess() {
            return success;
        }
    }

    public final class ConversationQuit{
        @Expose
        @SerializedName("success")
        private Boolean success;

        public Boolean getSuccess() {
            return success;
        }
    }

}
