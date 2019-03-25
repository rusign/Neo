package com.mindorks.framework.mvvm.ui.chatlist;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.data.model.api.ApiContent;
import com.mindorks.framework.mvvm.data.model.api.ChatlListResponse;
import com.mindorks.framework.mvvm.data.model.api.CirclesRequest;
import com.mindorks.framework.mvvm.data.model.api.ConversationRequest;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.ui.chat.ChatActivity;
import com.mindorks.framework.mvvm.ui.chat.Message;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatlistViewModel extends BaseViewModel<ChatlistNavigator> {

    public final ObservableList<ChatlListResponse.Chatlist> chatlistObservableArrayList = new ObservableArrayList<>();

    private final MutableLiveData<List<ChatlListResponse.Chatlist>> chatlistListLiveData;

    public ChatlistViewModel(DataManager dataManager,
                         SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
     //   chatlistObservableArrayList.add(new ChatlListResponse.Chatlist("", "", 1, 1));

       // Log.i("chatlisteArrayList", chatlistObservableArrayList.get(0).getName());
        chatlistListLiveData = new MutableLiveData<>();
        chatlistListLiveData.setValue(chatlistObservableArrayList);

        //fetchBlogs();
    }

    public void addChatlistItemsToList(List<ChatlListResponse.Chatlist> chatlistList) {
       // chatlistObservableArrayList.clear();
        chatlistObservableArrayList.addAll(chatlistList);
        Log.i("Itemlist", "item add");
        String token = getDataManager().getAccessToken();
        getCompositeDisposable().add(getDataManager()
                .doCirclesApiCall(new CirclesRequest.CircleList(token))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    for ( ApiContent.CircleListContent val: response.getContent()){
                        getNavigator().addCircleToMap(val.name, val.id);
                    }
                    for(Integer item : response.getIds()){
                        getConvByCircleID(token, item);
                    }

                    //getNavigator().openMainActivity();
                }, throwable -> {
                    setIsLoading(false);
                 //   getNavigator().handleError(throwable);
                   // getNavigator().toastServerError();
                }));
    }

    private void getConvByCircleID(String token, int id){
        getCompositeDisposable().add(getDataManager()
                .doConversationListApiCall(new ConversationRequest.ConversationList(token, id))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(resp -> {
                    setIsLoading(false);
                    for(ApiContent.ConversationListContent item : resp.getContent()) {
                        Log.i("item id ", String.valueOf(item.id));
                        Log.i("item  circleid ", String.valueOf(item.circle_id));
                        Date date = new Date(item.updated);
                        chatlistObservableArrayList.add(new ChatlListResponse.Chatlist(item.name, DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(date).toString(), item.id, item.circle_id));
                    }
                  //  chatlistObservableArrayList.addAll(chatlistListLiveData);
                    //getNavigator().openMainActivity();
                }, throwable -> {
                    setIsLoading(false);
                    //   getNavigator().handleError(throwable);
                    // getNavigator().toastServerError();
                }));
    }

    public void createConv(int circleID, String name, String email){
        Log.i("createConv id", String.valueOf(circleID));
        Log.i("createConv name", name);
        getCompositeDisposable().add(getDataManager()
                .doConversationCreateApiCall(new ConversationRequest.ConversationCreate(getDataManager().getAccessToken(), email, circleID))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(resp -> {
                    setIsLoading(false);
                    nameTheConv(name, resp.getConversation_id());
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().toastMessage("Echec de la création");
                    Log.i("createConv echec", throwable.toString());

                    //   getNavigator().handleError(throwable);
                    // getNavigator().toastServerError();
                }));
    }

    public void nameTheConv(String name, int id){
        getCompositeDisposable().add(getDataManager()
                .doConversationUpdateApiCall(new ConversationRequest.ConversationUpdate(getDataManager().getAccessToken(), name, id))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(resp -> {
                    setIsLoading(false);
                    getNavigator().toastMessage(name + " créer");
                    getNavigator().openHome();
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().toastMessage("Echec de la création");
                    Log.i("createConv echec", throwable.toString());
                    //   getNavigator().handleError(throwable);
                    // getNavigator().toastServerError();
                }));
    }

    private void getNames(List<ApiContent.CircleUser> links, String nom, int conv_id){
        List<String> names = new ArrayList<String>();

        for (ApiContent.CircleUser item : links){
            names.add(item.user.email);
        }
        getNavigator().displayEmail(names, nom, conv_id);
    }

    public void getEmailFromServer(int id, String nom) {
        setIsLoading(true);
        Log.i("getEmailFromServer id", String.valueOf(id));
        Log.i("getEmailFromServer nom", nom);
        getCompositeDisposable().add(getDataManager()
                .doCirclesContactsApiCall(new CirclesRequest.CircleContacts(getDataManager().getAccessToken(), id))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNames(response.getContent().users, nom, id);
                    // getNavigator().displayMessageFromServer(message);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().toastMessage("Echec de la création");
                    //   getNavigator().handleError(throwable);
                    // getNavigator().toastServerError();
                }));
    }

    public void quitConv(int conv_id){
        Log.i("getEmailFromServer id", String.valueOf(conv_id));
        getCompositeDisposable().add(getDataManager()
                .doConversationQuitApiCall(new ConversationRequest.ConvrsationQuit(getDataManager().getAccessToken(), conv_id))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().toastMessage("Vous avez quittez la conversation");
                    getNavigator().openHome();
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().toastMessage("Echec, Vous n'avez pas pu quittez la conversation");
                    //   getNavigator().handleError(throwable);
                    // getNavigator().toastServerError();
                }));
    }

    public MutableLiveData<List<ChatlListResponse.Chatlist>>  getChatlistListLiveData() {
        Log.i("getChatlistListLiveData", "getChatlistListLiveData");
        return chatlistListLiveData;
    }

    public ObservableList<ChatlListResponse.Chatlist> getChatlistObservableList() {
        return chatlistObservableArrayList;
    }

    public void onAddConvClick(){
        Log.i("onAddConvClick", "onAddConvClick");
        getNavigator().displayDialogAddConv();
    }

    public String GetToken(){
        return getDataManager().getAccessToken();
    }
}
