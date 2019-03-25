package com.mindorks.framework.mvvm.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConversationRequest {

    private ConversationRequest() {
        // This class is not publicly instantiable
    }

    public static class ConversationInfo {

        @Expose
        @SerializedName("token")
        private String token;

        @Expose
        @SerializedName("conversation_id")
        private int conversation_id;

        public ConversationInfo(String token, int conversation_id){
            this.token = token;
            this.conversation_id = conversation_id;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || getClass() != object.getClass()) {
                return false;
            }

            ConversationInfo that = (ConversationInfo) object;

            return token != null ? token.equals(that.token) : that.token == null;
        }

        @Override
        public int hashCode() {
            int result = token != null ? token.hashCode() : 0;
            return result;
        }

        public String getToken() {
            return token;
        }

    }

    public static class ConversationUploadMedia{

        @Expose
        @SerializedName("file")
        private String file;

        public ConversationUploadMedia(String file){
            this.file = file;
        }

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

        public String getFile() {
            return file;
        }

    }

    public static class ConversationMessageSend{
        @Expose
        @SerializedName("token")
        private String token;

        @Expose
        @SerializedName("files")
        private List<String> files;

        @Expose
        @SerializedName("conversation_id")
        private int conversation_id;


        public ConversationMessageSend(String token, List<String> files, int conversation_id){
            this.token = token;
            this.files = files;
            this.conversation_id  = conversation_id;
        }

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

        public List<String> getFiles() {
            return files;
        }

        public int getConversation_id() {
            return conversation_id;
        }

    }

    public static class ConversationMessageInfo {

        @Expose
        @SerializedName("token")
        private String token;

        @Expose
        @SerializedName("message_id")
        private int message_id;

        public ConversationMessageInfo(String token, int message_id){
            this.token = token;
            this.message_id = message_id;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || getClass() != object.getClass()) {
                return false;
            }

            ConversationMessageInfo that = (ConversationMessageInfo) object;

            return token != null ? token.equals(that.token) : that.token == null;
        }

        @Override
        public int hashCode() {
            int result = token != null ? token.hashCode() : 0;
            return result;
        }

        public String getToken() {
            return token;
        }

        public int getMessage_id() {
            return message_id;
        }

    }

    public static class ConversationPic {

        @Expose
        @SerializedName("token")
        private String token;

        @Expose
        @SerializedName("media_id")
        private int media_id;

        public ConversationPic(String token, int media_id){
            this.token = token;
            this.media_id = media_id;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || getClass() != object.getClass()) {
                return false;
            }

            ConversationPic that = (ConversationPic) object;

            return token != null ? token.equals(that.token) : that.token == null;
        }

        @Override
        public int hashCode() {
            int result = token != null ? token.hashCode() : 0;
            return result;
        }

        public String getToken() {
            return token;
        }
        public int getMedia_id() {
            return media_id;
        }

    }

    public static class ConversationList {

        @Expose
        @SerializedName("token")
        private String token;

        @Expose
        @SerializedName("circle_id")
        private int circle_id;

        public ConversationList(String token, int circle_id){

            this.token = token;
            this.circle_id = circle_id;
        }


        public String getToken() {
            return token;
        }

        public int getCircle_id() {
            return circle_id;
        }

    }

    public static class ConversationInvite {

        @Expose
        @SerializedName("token")
        private String token;

        @Expose
        @SerializedName("email")
        private String email;

        @Expose
        @SerializedName("conversation_id")
        private int conversation_id;

        public ConversationInvite(String token, String email, int conversation_id){

            this.token = token;
            this.email = email;
            this.conversation_id = conversation_id;
        }


        public String getToken() {
            return token;
        }

        public String getEmail() {
            return email;
        }

        public int getConversation_id() {
            return conversation_id;
        }

    }

    public static class ConversationCreate {

        @Expose
        @SerializedName("token")
        private String token;

        @Expose
        @SerializedName("email")
        private String email;

        @Expose
        @SerializedName("text_message")
        private String text_message;

        @Expose
        @SerializedName("circle_id")
        private int circle_id;



        public ConversationCreate(String token, String email, int circle_id){

            this.token = token;
            this.email = email;
            this.circle_id = circle_id;
            this.text_message = "";
        }


        public String getToken() {
            return token;
        }

        public String getText_message() {
            return text_message;
        }

        public String getEmail() {
            return email;
        }

        public int getCircle_id() {
            return circle_id;
        }

    }

    public static class ConversationUpdate {

        @Expose
        @SerializedName("token")
        private String token;

        @Expose
        @SerializedName("conversation_name")
        private String conversation_name;

        @Expose
        @SerializedName("conversation_id")
        private int conversation_id;



        public ConversationUpdate(String token, String conversation_name, int conversation_id){

            this.token = token;
            this.conversation_name = conversation_name;
            this.conversation_id = conversation_id;
        }


        public String getToken() {
            return token;
        }


        public String getConversation_name() {
            return conversation_name;
        }

        public int getConversation_id() {
            return conversation_id;
        }

    }

    public static class ConvrsationQuit {
        @Expose
        @SerializedName("token")
        private String token;

        @Expose
        @SerializedName("conversation_id")
        private int conversation_id;

        public ConvrsationQuit(String token, int conversation_id){
            this.token = token;
            this.conversation_id = conversation_id;
        }

        public String getToken() {
            return token;
        }

        public int getConversation_id() {
            return conversation_id;
        }

    }
}
