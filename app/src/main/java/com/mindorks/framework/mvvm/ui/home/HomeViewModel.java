package com.mindorks.framework.mvvm.ui.home;

import android.util.Log;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.data.model.api.LoginRequest;
import com.mindorks.framework.mvvm.data.model.api.UserInfoRequest;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {

    public HomeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getUserInfo(){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doUserInfoApiCall(new UserInfoRequest.ApiUserInfoRequest(getDataManager().getAccessToken()))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                        Log.i("email user info", response.getContent().email);
                        getDataManager().setCurrentUserEmail(response.getContent().email);
                        getDataManager().setCurrentUserFName(response.getContent().first_name);
                        getDataManager().setCurrentUserLName(response.getContent().last_name);
                        getDataManager().setCurrentUserBirth(response.getContent().birthday);

                }, throwable -> {
                    setIsLoading(false);
                    Log.i("error user info ", throwable.toString());
                }));
    }

    public void onSettingsClick(){
        getNavigator().openSettingsActivity();
    }

    public void onChatClick(){
        getNavigator().openChatlistActivity();
    }

    public void onContactsClick(){
        getNavigator().openContactsActivity();
    }

    public void onMedicalClick(){
        getNavigator().openMedicalActivity();
    }

    public void onNeoClick(){
        getNavigator().openNeoActivity();
    }

    public String GetToken(){
        return getDataManager().getAccessToken();
    }

}