package com.mindorks.framework.mvvm.ui.firstPage;

public interface FirstPageNavigator {

    void handleError(Throwable throwable);

    void openMainActivity();

    void openLoginActivity();

    void openInscriptionActivity();
}
