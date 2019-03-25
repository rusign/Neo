package com.mindorks.framework.mvvm.ui.login;

import org.json.JSONException;

public interface LoginNavigator {

    void handleError(Throwable throwable);

    void login();

    void openMainActivity();

    void toastServerError();
}
