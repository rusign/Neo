package com.mindorks.framework.mvvm.ui.settings;

import android.util.Log;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.data.model.api.SettingsRequest;
import com.mindorks.framework.mvvm.data.model.api.UserInfoRequest;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

public class SettingsViewModel extends BaseViewModel<SettingsNavigator> {

    public SettingsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public String getFname(){
        return getDataManager().getCurrentUserFName();
    }


    public void ModifyPass(String pass){
        Log.i("email set",getDataManager().getCurrentUserEmail() );
        setIsLoading(true);
        getDataManager().setCurrentUserPass(pass);

        getCompositeDisposable().add(getDataManager()
                .doModifyPassApiCall(new SettingsRequest.ApiModifyMdpRequest(getDataManager().getAccessToken(),
                        getDataManager().getCurrentUserEmail(),
                        getDataManager().getCurrentUserPass(),
                        pass))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);


                }, throwable -> {
                    setIsLoading(false);
                    Log.i("error user info ", throwable.toString());
                }));
    }

    public void ModifyEmail(String email){
        Log.i("email set",getDataManager().getCurrentUserEmail() );
        Log.i("email set",getDataManager().getCurrentUserFName() );
        Log.i("email set",getDataManager().getCurrentUserLName() );
        getDataManager().setCurrentUserEmail(email);
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doModifyApiCall(new SettingsRequest.ApiModifySettings(getDataManager().getAccessToken(),
                        email,
                        getDataManager().getCurrentUserLName(),
                        getDataManager().getCurrentUserLName()))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);


                }, throwable -> {
                    setIsLoading(false);
                    Log.i("error user info ", throwable.toString());
                }));
    }

    public void ModifFname(String email){
        Log.i("email set",getDataManager().getCurrentUserEmail() );
        Log.i("email set",getDataManager().getCurrentUserFName() );
        Log.i("email set",getDataManager().getCurrentUserLName() );
        getDataManager().setCurrentUserFName(email);

        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doModifyApiCall(new SettingsRequest.ApiModifySettings(getDataManager().getAccessToken(),
                        getDataManager().getCurrentUserEmail(),
                        email,
                        getDataManager().getCurrentUserLName()))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);


                }, throwable -> {
                    setIsLoading(false);
                    Log.i("error user info ", throwable.toString());
                }));
    }

    public void ModifLname(String email){
        Log.i("email set",getDataManager().getCurrentUserEmail() );
        Log.i("email set",getDataManager().getCurrentUserFName() );
        Log.i("email set",getDataManager().getCurrentUserLName() );
        getDataManager().setCurrentUserLName(email);

        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doModifyApiCall(new SettingsRequest.ApiModifySettings(getDataManager().getAccessToken(),
                        getDataManager().getCurrentUserEmail(),
                        getDataManager().getCurrentUserFName(),
                        email))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);


                }, throwable -> {
                    setIsLoading(false);
                    Log.i("error user info ", throwable.toString());
                }));
    }
}
