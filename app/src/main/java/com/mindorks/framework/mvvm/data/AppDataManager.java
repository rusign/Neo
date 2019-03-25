package com.mindorks.framework.mvvm.data;

import android.content.Context;
import android.provider.ContactsContract;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;
import com.mindorks.framework.mvvm.data.local.db.DbHelper;
import com.mindorks.framework.mvvm.data.local.prefs.PreferencesHelper;
import com.mindorks.framework.mvvm.data.model.api.BlogResponse;
import com.mindorks.framework.mvvm.data.model.api.CirclesRequest;
import com.mindorks.framework.mvvm.data.model.api.CirclesResponse;
import com.mindorks.framework.mvvm.data.model.api.ConversationRequest;
import com.mindorks.framework.mvvm.data.model.api.ConversationResponse;
import com.mindorks.framework.mvvm.data.model.api.InscriptionRequest;
import com.mindorks.framework.mvvm.data.model.api.InscriptionResponse;
import com.mindorks.framework.mvvm.data.model.api.LoginRequest;
import com.mindorks.framework.mvvm.data.model.api.LoginResponse;
import com.mindorks.framework.mvvm.data.model.api.NotifRequest;
import com.mindorks.framework.mvvm.data.model.api.NotifResponse;
import com.mindorks.framework.mvvm.data.model.api.OpenSourceResponse;
import com.mindorks.framework.mvvm.data.model.api.SettingsRequest;
import com.mindorks.framework.mvvm.data.model.api.SettingsResponse;
import com.mindorks.framework.mvvm.data.model.api.UserInfoRequest;
import com.mindorks.framework.mvvm.data.model.api.UserInfoResponse;
import com.mindorks.framework.mvvm.data.model.db.Option;
import com.mindorks.framework.mvvm.data.model.db.Question;
import com.mindorks.framework.mvvm.data.model.db.User;
import com.mindorks.framework.mvvm.data.model.others.QuestionCardData;
import com.mindorks.framework.mvvm.data.remote.ApiHeader;
import com.mindorks.framework.mvvm.data.remote.ApiHelper;
import com.mindorks.framework.mvvm.utils.AppConstants;
import com.mindorks.framework.mvvm.utils.CommonUtils;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class AppDataManager implements DataManager {

    private final ApiHelper mApiHelper;

    private final Context mContext;

    private final DbHelper mDbHelper;

    private final Gson mGson;

    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public AppDataManager(Context context, DbHelper dbHelper, PreferencesHelper preferencesHelper, ApiHelper apiHelper, Gson gson) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
        mGson = gson;
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        return mApiHelper.doServerLoginApiCall(request);
    }


    @Override
    public Single<CirclesResponse.CircleListResponse> doCirclesApiCall(CirclesRequest.CircleList request) {
        return mApiHelper.doCirclesApiCall(request);
    }

    @Override
    public Single<CirclesResponse.CircleContactsResponse> doCirclesContactsApiCall(CirclesRequest.CircleContacts request) {
        return mApiHelper.doCirclesContactsApiCall(request);
    }

    @Override
    public Single<CirclesResponse.CircleCreateResponse> doCirclesCreateApiCall(CirclesRequest.CircleCreate request) {
        return mApiHelper.doCirclesCreateApiCall(request);
    }

    @Override
    public Single<CirclesResponse.CircleInviteResponse> doCirclesInviteApiCall(CirclesRequest.CircleInvite request) {
        return mApiHelper.doCirclesInviteApiCall(request);
    }

    @Override
    public Single<CirclesResponse.CircleJoinRejectResponse> doCirclesJoinApiCall(CirclesRequest.CircleJoinReject request) {
        return mApiHelper.doCirclesJoinApiCall(request);
    }

    @Override
    public Single<CirclesResponse.CircleQuitResponse> doCirclesQuitApiCall(CirclesRequest.CircleQuit request) {
        return mApiHelper.doCirclesQuitApiCall(request);
    }

    @Override
    public Single<CirclesResponse.CircleJoinRejectResponse> doCirclesRejectApiCall(CirclesRequest.CircleJoinReject request) {
        return mApiHelper.doCirclesRejectApiCall(request);
    }

    @Override
    public Single<InscriptionResponse> doInscriptionApiCall(InscriptionRequest.InscriptionCreate request) {
        return mApiHelper.doInscriptionApiCall(request);
    }

    @Override
    public Single<ConversationResponse.ConversationInfo> doConversationInfoApiCall(ConversationRequest.ConversationInfo request) {
        return mApiHelper.doConversationInfoApiCall(request);
    }
    @Override
    public Single<ConversationResponse.ConversationPic> doConversationPicApiCall(ConversationRequest.ConversationPic request) {
        return mApiHelper.doConversationPicApiCall(request);
    }

    @Override
    public Single<ConversationResponse.ConversationMessageInfo> doConversationMessageInfoApiCall(ConversationRequest.ConversationMessageInfo request) {
        return mApiHelper.doConversationMessageInfoApiCall(request);
    }

    @Override
    public Single<ConversationResponse.ConversationMessageSend> doConversationMessageSendApiCall(ConversationRequest.ConversationMessageSend request) {
        return mApiHelper.doConversationMessageSendApiCall(request);
    }

    @Override
    public Single<ConversationResponse.ConversationUploadMedia> doConversationUploadMediaApiCall(ConversationRequest.ConversationUploadMedia request, int media_id,  String token) {
        return mApiHelper.doConversationUploadMediaApiCall(request, media_id, token);
    }

    @Override
    public Single<ConversationResponse.ConversationInvite> doConversationInviteApiCall(ConversationRequest.ConversationInvite request) {
        return mApiHelper.doConversationInviteApiCall(request);
    }

    @Override
    public Single<SettingsResponse.ApiModifyMdpRespose> doModifyPassApiCall(SettingsRequest.ApiModifyMdpRequest  request) {
        return mApiHelper.doModifyPassApiCall(request);
    }

    @Override
    public Single<SettingsResponse.ApiModifyResponse> doModifyApiCall(SettingsRequest.ApiModifySettings  request) {
        return mApiHelper.doModifyApiCall(request);
    }

    @Override
    public Single<ConversationResponse.ConversationQuit> doConversationQuitApiCall(ConversationRequest.ConvrsationQuit request) {
        return mApiHelper.doConversationQuitApiCall(request);
    }
    @Override
    public Single<ConversationResponse.ConversationCreate> doConversationCreateApiCall(ConversationRequest.ConversationCreate request) {
        return mApiHelper.doConversationCreateApiCall(request);
    }
    @Override
    public Single<ConversationResponse.ConversationUpdate> doConversationUpdateApiCall(ConversationRequest.ConversationUpdate request) {
        return mApiHelper.doConversationUpdateApiCall(request);
    }
    @Override
    public Single<ConversationResponse.ConversationList> doConversationListApiCall(ConversationRequest.ConversationList request) {
        return mApiHelper.doConversationListApiCall(request);
    }

    @Override
    public  Single<UserInfoResponse> doUserInfoApiCall(UserInfoRequest.ApiUserInfoRequest request){
        return mApiHelper.doUserInfoApiCall(request);
    }

    @Override
    public  Single<NotifResponse> doNotifSendTokenApiCall(NotifRequest.NotifSendToken request){
        return mApiHelper.doNotifSendTokenApiCall(request);
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

    @Override
    public Observable<List<Question>> getAllQuestions() {
        return mDbHelper.getAllQuestions();
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return mDbHelper.getAllUsers();
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override
    public Single<BlogResponse> getBlogApiCall() {
        return mApiHelper.getBlogApiCall();
    }

    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public Long getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(Long userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public String getCurrentUserFName() {
        return mPreferencesHelper.getCurrentUserFName();
    }

    @Override
    public void setCurrentUserFName(String userName) {
        mPreferencesHelper.setCurrentUserFName(userName);
    }

    @Override
    public String getCurrentUserLName() {
        return mPreferencesHelper.getCurrentUserLName();
    }

    @Override
    public void setCurrentUserLName(String userName) {
        mPreferencesHelper.setCurrentUserLName(userName);
    }

    @Override
    public String getCurrentUserBirth() {
        return mPreferencesHelper.getCurrentUserBirth();
    }

    @Override
    public void setCurrentUserBirth(String userName) {
        mPreferencesHelper.setCurrentUserBirth(userName);
    }

    @Override
    public String getCurrentUserPass() {
        return mPreferencesHelper.getCurrentUserPass();
    }

    @Override
    public void setCurrentUserPass(String Pass) {
        mPreferencesHelper.setCurrentUserPass(Pass);
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPreferencesHelper.getCurrentUserProfilePicUrl();
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl);
    }

    @Override
    public Single<OpenSourceResponse> getOpenSourceApiCall() {
        return mApiHelper.getOpenSourceApiCall();
    }

    @Override
    public Observable<List<Option>> getOptionsForQuestionId(Long questionId) {
        return mDbHelper.getOptionsForQuestionId(questionId);
    }

    @Override
    public Observable<List<QuestionCardData>> getQuestionCardData() {
        return mDbHelper.getAllQuestions()
                .flatMap(questions -> Observable.fromIterable(questions))
                .flatMap(question -> Observable.zip(
                        mDbHelper.getOptionsForQuestionId(question.id),
                        Observable.just(question),
                        (options, question1) -> new QuestionCardData(question1, options)))
                .toList()
                .toObservable();
    }

    @Override
    public Observable<Boolean> insertUser(User user) {
        return mDbHelper.insertUser(user);
    }

    @Override
    public Observable<Boolean> isOptionEmpty() {
        return mDbHelper.isOptionEmpty();
    }

    @Override
    public Observable<Boolean> isQuestionEmpty() {
        return mDbHelper.isQuestionEmpty();
    }

    @Override
    public Observable<Boolean> saveOption(Option option) {
        return mDbHelper.saveOption(option);
    }

    @Override
    public Observable<Boolean> saveOptionList(List<Option> optionList) {
        return mDbHelper.saveOptionList(optionList);
    }

    @Override
    public Observable<Boolean> saveQuestion(Question question) {
        return mDbHelper.saveQuestion(question);
    }

    @Override
    public Observable<Boolean> saveQuestionList(List<Question> questionList) {
        return mDbHelper.saveQuestionList(questionList);
    }



    @Override
    public Observable<Boolean> seedDatabaseOptions() {
        return mDbHelper.isOptionEmpty()
                .concatMap(isEmpty -> {
                    if (isEmpty) {
                        Type type = new TypeToken<List<Option>>() {
                        }.getType();
                        List<Option> optionList = mGson.fromJson(CommonUtils.loadJSONFromAsset(mContext, AppConstants.SEED_DATABASE_OPTIONS), type);
                        return saveOptionList(optionList);
                    }
                    return Observable.just(false);
                });
    }

    @Override
    public Observable<Boolean> seedDatabaseQuestions() {
        return mDbHelper.isQuestionEmpty()
                .concatMap(isEmpty -> {
                    if (isEmpty) {
                        Type type = $Gson$Types.newParameterizedTypeWithOwner(null, List.class, Question.class);
                        List<Question> questionList = mGson
                                .fromJson(CommonUtils.loadJSONFromAsset(mContext, AppConstants.SEED_DATABASE_QUESTIONS), type);
                        return saveQuestionList(questionList);
                    }
                    return Observable.just(false);
                });
    }


    @Override
    public void updateUserInfo(String accessToken) {

        setAccessToken(accessToken);
    }
}
