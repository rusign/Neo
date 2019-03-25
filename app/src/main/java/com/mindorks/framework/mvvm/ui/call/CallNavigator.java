package com.mindorks.framework.mvvm.ui.call;

public interface CallNavigator {
    void handleError(Throwable throwable);

    void openMainActivity();

    void toastServerError();
}
