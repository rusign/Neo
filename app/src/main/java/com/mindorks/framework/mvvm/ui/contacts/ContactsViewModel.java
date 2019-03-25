package com.mindorks.framework.mvvm.ui.contacts;

import android.util.Log;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.data.model.api.ApiContent;
import com.mindorks.framework.mvvm.data.model.api.CirclesRequest;
import com.mindorks.framework.mvvm.data.model.api.LoginRequest;
import com.mindorks.framework.mvvm.data.model.api.UserInfoRequest;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

public class ContactsViewModel extends BaseViewModel<ContactsNavigator> {

    public ContactsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
    public String GetToken(){
        return getDataManager().getAccessToken();
    }
    public void getCircles() {
        setIsLoading(true);
       // getNavigator().setCircles();
        String token = getDataManager().getAccessToken();
        Log.i("token authenticate", token);
        getCompositeDisposable().add(getDataManager()
                .doCirclesApiCall(new CirclesRequest.CircleList(token))
                .doOnSuccess(response -> Log.i("getCircles succes", "success"))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    Log.i("getCircles suscribe", "suscribe");
                    for (ApiContent.CircleListContent item : response.getContent())
                        getNavigator().setCircles(item.name, item.id);
                }, throwable -> {
                    setIsLoading(false);
                    Log.i("getCircles error", throwable.toString());

                    //getNavigator().handleError(throwable);
                    //getNavigator().toastServerError();
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

    public void quitCircle (int circle_id){
        String token = getDataManager().getAccessToken();
        getCompositeDisposable().add(getDataManager()
                .doCirclesQuitApiCall(new CirclesRequest.CircleQuit(token, circle_id))
                .doOnSuccess(response -> Log.i("quitCircle succes", "success"))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    Log.i("quitCircle suscribe", "suscribe");
                    getNavigator().toastMessage("Vous avez quittez le cercle");
                    getNavigator().openHome();
                }, throwable -> {
                    setIsLoading(false);
                    Log.i("quitCircle echec", throwable.toString());
                    getNavigator().toastMessage("Echec de la suppression du cercle");

                }));
    }

    public void checkInvite(){
        getCompositeDisposable().add(getDataManager()
                .doUserInfoApiCall(new UserInfoRequest.ApiUserInfoRequest(getDataManager().getAccessToken()))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    Log.i("email user info", response.getContent().email);
                    if (response.getContent().invites.size() > 0){
                        getNavigator().enableInvitationIcon();
                    }
                }, throwable -> {
                    setIsLoading(false);
                    Log.i("error user info ", throwable.toString());
                }));
    }

    public void onAddCircleClick(){
        getNavigator().displayDialogAddCircle();
    }

    public void onInviteCircleClick() { getNavigator().openInvitation();}
}
