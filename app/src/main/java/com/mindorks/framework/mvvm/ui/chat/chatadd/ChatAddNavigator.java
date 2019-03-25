package com.mindorks.framework.mvvm.ui.chat.chatadd;

public interface ChatAddNavigator {
    void handleError(Throwable throwable);

    void login();

    void openMainActivity();

    void toastServerError();
}
