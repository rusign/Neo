package com.mindorks.framework.mvvm.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class CirclesRequest {

    private CirclesRequest() {

    }

    public static class CircleList {
        @Expose
        @SerializedName("token")
        private String token;

        public CircleList(String token){
            this.token = token;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || getClass() != object.getClass()) {
                return false;
            }

            CircleList that = (CircleList) object;

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

    public static class CircleContacts {
        @Expose
        @SerializedName("token")
        private String token;

        @Expose
        @SerializedName("circle_id")
        private int circle_id;

        public CircleContacts(String token, int circle_id){
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

    public static class CircleCreate {
        @Expose
        @SerializedName("token")
        private String token;

        @Expose
        @SerializedName("name")
        private String name;

        public CircleCreate(String token, String name){
            this.token = token;
            this.name = name;
        }

        public String getToken() {
            return token;
        }

        public String getName() {
            return name;
        }

    }

    public static class CircleInvite {
        @Expose
        @SerializedName("token")
        private String token;

        @Expose
        @SerializedName("email")
        private String email;

        @Expose
        @SerializedName("circle_id")
        private int circle_id;

        public CircleInvite(String token, String email, int circle_id){
            this.token = token;
            this.email = email;
            this.circle_id = circle_id;
        }

        public String getToken() {
            return token;
        }

        public String getEmail() {
            return email;
        }

        public int getCircle_id() {
            return circle_id;
        }

    }

    public static class CircleJoinReject {
        @Expose
        @SerializedName("token")
        private String token;

        @Expose
        @SerializedName("invite_id")
        private int invite_id;

        public CircleJoinReject(String token, int invite_id){
            this.token = token;
            this.invite_id = invite_id;
        }

        public String getToken() {
            return token;
        }

        public int getInvite_id() {
            return invite_id;
        }

    }

    public static class CircleQuit {
        @Expose
        @SerializedName("token")
        private String token;

        @Expose
        @SerializedName("circle_id")
        private int circle_id;

        public CircleQuit(String token, int circle_id){
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


}
