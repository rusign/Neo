package com.mindorks.framework.mvvm.ui.contacts.circlecontacts;

public class CircleContactsItem {
        private String Title;
        private String Content ;
        private int User_id;

        public CircleContactsItem() {
        }

        public CircleContactsItem(String title, String content, int user_id) {
            Title = title;
            Content = content;
            User_id = user_id;
        }


        public String getEmail() {
            return Title;
        }

        public String getName() {
            return Content;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public void setContent(String c) {
            Content = c;
        }

        public void setUser_id(int user_id) {User_id =user_id; }

        public int getUser_id() { return User_id; }
}
