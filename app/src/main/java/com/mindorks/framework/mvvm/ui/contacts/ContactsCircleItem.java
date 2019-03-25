package com.mindorks.framework.mvvm.ui.contacts;

public class ContactsCircleItem {
    private String Title;
    private String Content ;
    private int Id ;

    public ContactsCircleItem() {
    }

    public ContactsCircleItem(String title, String content, int id) {
        Title = title;
        Content = content;
        Id = id;
    }


    public String getTitle() {
        return Title;
    }

    public String getContent() {
        return Content;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setContent(String c) {
        Content = c;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
