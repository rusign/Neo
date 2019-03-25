package com.mindorks.framework.mvvm.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatlListResponse {

    @Expose
    @SerializedName("data")
    private List<Chatlist> data;

    @Expose
    @SerializedName("message")
    private String message;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlogResponse)) {
            return false;
        }

        return true;
    }

    public List<Chatlist> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class Chatlist {

        @Expose
        @SerializedName("name")
        private String name;

        @Expose
        @SerializedName("id")
        private Integer id;

        @Expose
        @SerializedName("circle_id")
        private Integer circle_id;

        @Expose
        @SerializedName("content")
        private String content;

        public Chatlist(String name, String content,Integer id, Integer circle_id ){
            this.name = name;
            this.content = content;
            this.id = id;
            this.circle_id = circle_id;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Chatlist)) {
                return false;
            }

             return true;
        }

        public String getName() {
            return name;
        }

        public Integer getId() {
            return id;
        }

        public Integer getCircle_id() { return circle_id; }

        public String getContent() {
            return content;
        }

    }
}
