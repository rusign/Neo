package com.mindorks.framework.mvvm.ui.contacts;

import java.util.List;

public interface ContactsNavigator {

     void setCircles(String Name, int id);

     void displayDialogAddCircle();

     void toastMessage(String message);

     void openHome();

     void openInvitation();

     void enableInvitationIcon();

}
