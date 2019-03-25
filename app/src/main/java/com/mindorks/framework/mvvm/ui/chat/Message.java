package com.mindorks.framework.mvvm.ui.chat;

public class Message {
    private String text; // message body
    private ChatActivity.MemberData data; // data of the user that sent this message
    private boolean belongsToCurrentUser; // is this message sent by us?
    private boolean isMedia;
    private boolean isFromServer;
    private int idServer;

    public Message(String text, ChatActivity.MemberData data, boolean belongsToCurrentUser, boolean isMedia) {
        this.text = text;
        this.data = data;
        this.belongsToCurrentUser = belongsToCurrentUser;
        this.isMedia = isMedia;
        this.isFromServer = false;
    }

    public String getText() {
        return text;
    }

    public ChatActivity.MemberData getData() {
        return data;
    }

    public boolean isBelongsToCurrentUser() {
        return belongsToCurrentUser;
    }

    public boolean isMedia() {
        return isMedia;
    }

    public boolean isFromServer() {
        return isFromServer;
    }

    public void setIsFromServer(boolean val) {
        isFromServer = val;
    }

    public void setIdServer(int idServer) {
        this.idServer = idServer;
    }

    public int getIdServer() {
        return idServer;
    }
}