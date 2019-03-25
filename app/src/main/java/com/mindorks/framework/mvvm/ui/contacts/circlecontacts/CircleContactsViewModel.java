package com.mindorks.framework.mvvm.ui.contacts.circlecontacts;

import android.util.Log;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.data.model.api.ApiContent;
import com.mindorks.framework.mvvm.data.model.api.CirclesRequest;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

public class CircleContactsViewModel extends BaseViewModel<CircleContactsNavigator> {

    public CircleContactsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getContacts(int id) {
        setIsLoading(true);
        // getNavigator().setCircles();
        String token = getDataManager().getAccessToken();
        getCompositeDisposable().add(getDataManager()
                .doCirclesContactsApiCall(new CirclesRequest.CircleContacts(token, id))
                .doOnSuccess(response -> Log.i("getCircles succes", "success"))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    Log.i("getCircles suscribe", "suscribe");
                    for (ApiContent.CircleUser item : response.getContent().users)
                    {
                        String tmp = item.user.first_name + " " + item.user.last_name;
                        getNavigator().setCircles(item.user.email, tmp, item.user.id);
                    }
                }, throwable -> {
                    setIsLoading(false);
                    Log.i("getCircles error", throwable.toString());

                    //getNavigator().handleError(throwable);
                    //getNavigator().toastServerError();
                }));
    }

    public void inviteToCircle(String email, int id){
        setIsLoading(true);
        // getNavigator().setCircles();
        Log.i("inviteToCircle email", email);
        Log.i("inviteToCircle email", String.valueOf(id));

        String token = getDataManager().getAccessToken();
        getCompositeDisposable().add(getDataManager()
                .doCirclesInviteApiCall(new CirclesRequest.CircleInvite(token, email, id))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    Log.i("inviteToCircle suscribe", "suscribe");
                    getNavigator().toastMessage(email + " invité");
                    getNavigator().openHome();
                }, throwable -> {
                    setIsLoading(false);
                    Log.i("inviteToCircle error", throwable.toString());
                    getNavigator().toastMessage("Echec de la création du cercle");

                    //getNavigator().handleError(throwable);
                    //getNavigator().toastServerError();
                }));
    }

    public void onAddCircleInviteClick()
    {
        getNavigator().displayDialogInvite();
    }

}
