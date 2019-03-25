package com.mindorks.framework.mvvm.data.model.api;

public class ChatlistC {

        private String name;

        private String content;

        public ChatlistC(String name, String content){
            this.name = name;
            this.content = content;
        }

        public String getName() {
            return name;
        }

        public String getContent() {
            return content;
        }

    }

