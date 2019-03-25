
package com.mindorks.framework.mvvm.ui.login;

import android.text.TextUtils;
import android.util.Log;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.data.model.api.LoginRequest;
import com.mindorks.framework.mvvm.data.model.api.NotifRequest;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.CommonUtils;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    public LoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public boolean isEmailAndPasswordValid(String email, String password) {
        // validate email and password
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        if (!CommonUtils.isEmailValid(email)) {
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        return true;
    }

    public void login(String email, String password) {
        setIsLoading(true);

        getCompositeDisposable().add(getDataManager()
                .doServerLoginApiCall(new LoginRequest.ServerLoginRequest(email, password))
                .doOnSuccess(response -> getDataManager()
                        .updateUserInfo(
                                response.getToken()))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().openMainActivity();
                    getDataManager().setCurrentUserPass(password);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                    getNavigator().toastServerError();
                }));
    }

    public void SendFirebaseToken(String token) {
        setIsLoading(true);

        getCompositeDisposable().add(getDataManager()
                .doNotifSendTokenApiCall(new NotifRequest.NotifSendToken(getDataManager().getAccessToken(), token))
                        .doOnSuccess(response -> Log.i("getCircles succes", "success"))
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            setIsLoading(false);
                            Log.i("addUserToConv", "success");
                            //getNavigator().openMainActivity();
                          //  getNavigator().toastSuccess(email + " ajouté");
                        }, throwable -> {
                            setIsLoading(false);
                            Log.i("addUserToConv error", throwable.toString());
                        //    getNavigator().toastSuccess(email + " déjà dans la conversation");
                        }));
    }

    public void onServerLoginClick() {
        getNavigator().login();
    }
}
