package com.mindorks.framework.mvvm.ui.contacts.contact;

public interface ContactNavigator {

    void handleError(Throwable throwable);

    void openMainActivity();

    void toastServerError();
}
