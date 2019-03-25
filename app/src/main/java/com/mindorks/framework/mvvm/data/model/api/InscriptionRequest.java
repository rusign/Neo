package com.mindorks.framework.mvvm.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class InscriptionRequest {

    private InscriptionRequest() {

    }

    public  static class InscriptionCreate {
        @Expose
        @SerializedName("email")
        private String email;

        @Expose
        @SerializedName("password")
        private String password;

        @Expose
        @SerializedName("first_name")
        private String first_name;

        @Expose
        @SerializedName("last_name")
        private String last_name;

        @Expose
        @SerializedName("birthday")
        private String birthday;


        public InscriptionCreate(String email, String password, String first_name, String last_name,String birthday) {
            this.email = email;
            this.password = password;
            this.first_name = first_name;
            this.last_name = last_name;
            this.birthday = birthday;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || getClass() != object.getClass()) {
                return false;
            }

            InscriptionCreate that = (InscriptionCreate) object;

            if (email != null ? !email.equals(that.email) : that.email != null) {
                return false;
            }
            if (password != null ? !password.equals(that.password) : that.password != null) {
                return false;
            }
            if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null) {
                return false;
            }
            if (last_name != null ? !last_name.equals(that.last_name) : that.last_name != null) {
                return false;
            }
            return first_name != null ? first_name.equals(that.first_name) : that.first_name == null;
        }

        @Override
        public int hashCode() {
            int result = email.hashCode();
            result = 31 * result + password.hashCode();
            result = 31 * result + birthday.hashCode();
            result = 31 * result + last_name.hashCode();
            result = 31 * result + first_name.hashCode();
            return result;
        }

        public String getEmail() {
            return email;
        }
        public String getPassword() {
            return password;
        }
        public String getBirthday() {
            return birthday;
        }
        public String getLast_name() {
            return last_name;
        }
        public String getFirst_name() {
            return first_name;
        }

    }

}