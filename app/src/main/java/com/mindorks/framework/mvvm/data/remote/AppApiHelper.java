package com.mindorks.framework.mvvm.data.remote;

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
import com.rx2androidnetworking.Rx2AndroidNetworking;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class AppApiHelper implements ApiHelper {

    private ApiHeader mApiHeader;

    @Inject
    public AppApiHelper(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_LOGIN)
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(LoginResponse.class);
    }
    @Override
    public Single<CirclesResponse.CircleListResponse> doCirclesApiCall(CirclesRequest.CircleList request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CIRCLE_LIST)
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(CirclesResponse.CircleListResponse.class);
    }

    @Override
    public Single<CirclesResponse.CircleCreateResponse> doCirclesCreateApiCall(CirclesRequest.CircleCreate request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CIRCLE_CREATE)
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(CirclesResponse.CircleCreateResponse.class);
    }

    @Override
    public Single<CirclesResponse.CircleInviteResponse> doCirclesInviteApiCall(CirclesRequest.CircleInvite request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CIRCLE_INVITE)
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(CirclesResponse.CircleInviteResponse.class);
    }

    @Override
    public Single<CirclesResponse.CircleContactsResponse> doCirclesContactsApiCall(CirclesRequest.CircleContacts request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CIRCLE_INFO)
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(CirclesResponse.CircleContactsResponse.class);
    }

    @Override
    public Single<CirclesResponse.CircleJoinRejectResponse> doCirclesJoinApiCall(CirclesRequest.CircleJoinReject request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CIRCLE_JOIN)
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(CirclesResponse.CircleJoinRejectResponse.class);
    }

    @Override
    public Single<CirclesResponse.CircleJoinRejectResponse> doCirclesRejectApiCall(CirclesRequest.CircleJoinReject request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CIRCLE_REJECT)
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(CirclesResponse.CircleJoinRejectResponse.class);
    }

    @Override
    public Single<CirclesResponse.CircleQuitResponse> doCirclesQuitApiCall(CirclesRequest.CircleQuit request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CIRCLE_QUIT)
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(CirclesResponse.CircleQuitResponse.class);
    }



    @Override
    public Single<InscriptionResponse> doInscriptionApiCall(InscriptionRequest.InscriptionCreate request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_CREATE)
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(InscriptionResponse.class);
    }

    @Override
    public Single<ConversationResponse.ConversationInfo> doConversationInfoApiCall(ConversationRequest.ConversationInfo request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CONVERSATION_INFO)
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(ConversationResponse.ConversationInfo.class);
    }

    @Override
    public Single<ConversationResponse.ConversationPic> doConversationPicApiCall(ConversationRequest.ConversationPic request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CONVERSATION_PIC)
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(ConversationResponse.ConversationPic.class);
    }

    @Override
    public Single<ConversationResponse.ConversationMessageInfo> doConversationMessageInfoApiCall(ConversationRequest.ConversationMessageInfo request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_MESSAGE_INFO)
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(ConversationResponse.ConversationMessageInfo.class);
    }

    @Override
    public Single<ConversationResponse.ConversationUploadMedia> doConversationUploadMediaApiCall(ConversationRequest.ConversationUploadMedia request, int media_id, String token) {
        return Rx2AndroidNetworking.upload(ApiEndPoint.ENDPOINT_UPLOAD_MEDIA+ "/{media_id}")
                .setContentType("multipart/form-data")
                .addHeaders("Authorization", token)
                .addPathParameter("media_id", String.valueOf(media_id))
                .addMultipartParameter(request)
                .build()
                .getObjectSingle(ConversationResponse.ConversationUploadMedia.class);
    }

    @Override
    public Single<ConversationResponse.ConversationMessageSend> doConversationMessageSendApiCall(ConversationRequest.ConversationMessageSend request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_MESSAGE_SEND)
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(ConversationResponse.ConversationMessageSend.class);
    }

    @Override
    public Single<ConversationResponse.ConversationQuit> doConversationQuitApiCall(ConversationRequest.ConvrsationQuit request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CONVERSATION_QUIT)
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(ConversationResponse.ConversationQuit.class);
    }

    @Override
    public Single<ConversationResponse.ConversationInvite> doConversationInviteApiCall(ConversationRequest.ConversationInvite request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CONVERSATION_INVITE)
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(ConversationResponse.ConversationInvite.class);
    }

    @Override
    public Single<ConversationResponse.ConversationCreate> doConversationCreateApiCall(ConversationRequest.ConversationCreate request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CONVERSATION_CREATE)
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(ConversationResponse.ConversationCreate.class);
    }

    @Override
    public Single<ConversationResponse.ConversationUpdate> doConversationUpdateApiCall(ConversationRequest.ConversationUpdate request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CONVERSATION_UPDATE)
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(ConversationResponse.ConversationUpdate.class);
    }


    @Override
    public Single<ConversationResponse.ConversationList> doConversationListApiCall(ConversationRequest.ConversationList request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CONVERSATION_LIST)
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(ConversationResponse.ConversationList.class);
    }

    @Override
    public Single<UserInfoResponse> doUserInfoApiCall(UserInfoRequest.ApiUserInfoRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_USER_INFO)
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(UserInfoResponse.class);
    }

    @Override
    public Single<NotifResponse> doNotifSendTokenApiCall(NotifRequest.NotifSendToken request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_ANDROID_TOKEN)
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(NotifResponse.class);
    }

    @Override
    public Single<SettingsResponse.ApiModifyMdpRespose> doModifyPassApiCall(SettingsRequest.ApiModifyMdpRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_MODIFY_PASS)
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(SettingsResponse.ApiModifyMdpRespose.class);
    }

    @Override
    public Single<SettingsResponse.ApiModifyResponse> doModifyApiCall(SettingsRequest.ApiModifySettings request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_MODIFY)
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(SettingsResponse.ApiModifyResponse.class);
    }



    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    @Override
    public Single<BlogResponse> getBlogApiCall() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_BLOG)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(BlogResponse.class);
    }

    @Override
    public Single<OpenSourceResponse> getOpenSourceApiCall() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_OPEN_SOURCE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(OpenSourceResponse.class);
    }
}
