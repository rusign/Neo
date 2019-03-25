package com.mindorks.framework.mvvm.ui.contacts.circleinvitation;

import android.util.Log;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.data.model.api.ApiContent;
import com.mindorks.framework.mvvm.data.model.api.CirclesRequest;
import com.mindorks.framework.mvvm.data.model.api.UserInfoRequest;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.ui.contacts.ContactsNavigator;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

public class CircleInvitationViewModel extends BaseViewModel<CircleInvitationNavigator> {

    public CircleInvitationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getCircles() {
        setIsLoading(true);
        // getNavigator().setCircles();
        String token = getDataManager().getAccessToken();
        Log.i("token authenticate", token);
        getCompositeDisposable().add(getDataManager()
                .doUserInfoApiCall(new UserInfoRequest.ApiUserInfoRequest(getDataManager().getAccessToken()))
                .doOnSuccess(response -> Log.i("getCircles succes", "success"))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    Log.i("getCircles suscribe", "suscribe");
                    for (ApiContent.UserInvites item : response.getContent().invites)
                        getNavigator().setCircles(item.circle.name, item.id);
                }, throwable -> {
                    setIsLoading(false);
                    Log.i("getCircles error", throwable.toString());

                    //getNavigator().handleError(throwable);
                    //getNavigator().toastServerError();
                }));
    }

    public void joinCircle(int circle_id){
        Log.i("joinCircle circle_id", String.valueOf(circle_id));
        String token = getDataManager().getAccessToken();
        getCompositeDisposable().add(getDataManager()
                .doCirclesJoinApiCall(new CirclesRequest.CircleJoinReject(token, circle_id))
                .doOnSuccess(response -> Log.i("getCircles succes", "success"))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    Log.i("getCircles suscribe", "suscribe");
                    getNavigator().toastMessage("Vous êtes dans le cercle");
                    getNavigator().openHome();
                }, throwable -> {
                    setIsLoading(false);
                    Log.i("createCircle echec", throwable.toString());
                    getNavigator().toastMessage("Echec");

                }));
    }

    public void refectCircle(int circle_id){
        String token = getDataManager().getAccessToken();
        getCompositeDisposable().add(getDataManager()
                .doCirclesRejectApiCall(new CirclesRequest.CircleJoinReject(token, circle_id))
                .doOnSuccess(response -> Log.i("getCircles succes", "success"))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    Log.i("getCircles suscribe", "suscribe");
                    getNavigator().toastMessage("Vous avez rejeté l'invitation");
                    getNavigator().openHome();
                }, throwable -> {
                    setIsLoading(false);
                    Log.i("createCircle echec", throwable.toString());
                    getNavigator().toastMessage("Echec");

                }));
    }

    public void createCircle(String name){
        String token = getDataManager().getAccessToken();
        Log.i("token authenticate", token);
        getCompositeDisposable().add(getDataManager()
                .doCirclesCreateApiCall(new CirclesRequest.CircleCreate(token, name))
                .doOnSuccess(response -> Log.i("getCircles succes", "success"))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    Log.i("getCircles suscribe", "suscribe");
                    getNavigator().toastMessage(name + " créer");
                    getNavigator().openHome();
                }, throwable -> {
                    setIsLoading(false);
                    Log.i("createCircle echec", throwable.toString());
                    getNavigator().toastMessage("Echec de la création du cercle");

                }));
    }

    public void onAddCircleClick(){
        getNavigator().displayDialogAddCircle();
    }

    public void onInviteCircleClick() { getNavigator().openInvitation();}
}