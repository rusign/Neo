package com.mindorks.framework.mvvm.ui.inscription.Mdp;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.data.model.api.InscriptionRequest;
import com.mindorks.framework.mvvm.data.model.api.LoginRequest;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

public class InscriptionMdpViewModel extends BaseViewModel<InscriptionMdpNavigator> {

    public InscriptionMdpViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void createAccount(String email,String pass,String fname,String lname,String birth){
        getCompositeDisposable().add(getDataManager()
                .doInscriptionApiCall(new InscriptionRequest.InscriptionCreate(email, pass, fname, lname, birth))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().openMainActivity();
                }, throwable -> {
                    setIsLoading(false);
                   // getNavigator().handleError(throwable);
                    getNavigator().toastServerError();
                }));

    }

    public void onMdpInscriptionClick() {
        getNavigator().next();
    }
}
