package com.mindorks.framework.mvvm.ui.contacts.circlecontacts;

public interface CircleContactsNavigator {

    void setCircles(String email, String Name, int user_id);

    void displayDialogInvite();

    void toastMessage(String message);

    void openHome();
}
