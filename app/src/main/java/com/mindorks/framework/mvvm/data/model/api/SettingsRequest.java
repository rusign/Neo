package com.mindorks.framework.mvvm.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SettingsRequest {


        private SettingsRequest() {
            // This class is not publicly instantiable
        }

        public static class ApiModifyMdpRequest {

            @Expose
            @SerializedName("token")
            private String token;

            @Expose
            @SerializedName("email")
            private String email;

            @Expose
            @SerializedName("previous_password")
            private String previous_password;

            @Expose
            @SerializedName("new_password")
            private String new_password;

            public ApiModifyMdpRequest(String token, String email, String previous_password, String new_password) {

                this.token = token;
                this.email = email;
                this.previous_password = previous_password;
                this.new_password = new_password;
            }

            public String getToken() {
                return token;
            }

            public String getEmail() {
                return email;
            }

            public String getNew_password() {
                return new_password;
            }

            public String getPrevious_password() {
                return previous_password;
            }
        }

    public static class ApiModifySettings {

        @Expose
        @SerializedName("token")
        private String token;

        @Expose
        @SerializedName("email")
        private String email;

        @Expose
        @SerializedName("first_name")
        private String first_name;

        @Expose
        @SerializedName("last_name")
        private String last_name;

        public ApiModifySettings(String token, String email, String first_name, String last_name) {

            this.token = token;
            this.email = email;
            this.first_name = first_name;
            this.last_name = last_name;
        }

        public String getToken() {
            return token;
        }

        public String getEmail() {
            return email;
        }

        public String getFirst_name() {
            return first_name;
        }

        public String getLast_name() {
            return last_name;
        }
    }


}
