package com.mindorks.framework.mvvm.ui.contacts.circleinvitation;

public interface CircleInvitationNavigator {
    void setCircles(String Name, int id);

    void displayDialogAddCircle();

    void toastMessage(String message);

    void openHome();

    void openInvitation();
}
