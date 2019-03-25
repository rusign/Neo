package com.mindorks.framework.mvvm.ui.chat;

import java.util.List;

public interface ChatNavigator {

    void displayMessageFromServer(Message m);

    void setNames(List<String> namesList);

    void setConvName(String convName);

    void openChatAddActivity();

    void openChatCallActivity();

    void addToUserList(String user);

    void serUserId(int id);

    void toastSuccess(String message);

    void openMainActivity();

    void addUserToMap(String name, int id);

    void addMediaToView(int id,ChatActivity.MemberData dt, boolean user);

    void addIdToCall(int id);
}
