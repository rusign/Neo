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

import io.reactivex.Single;

public interface ApiHelper {

    Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request);

    Single<CirclesResponse.CircleListResponse> doCirclesApiCall(CirclesRequest.CircleList request);

    Single<CirclesResponse.CircleContactsResponse> doCirclesContactsApiCall(CirclesRequest.CircleContacts request);

    Single<CirclesResponse.CircleCreateResponse> doCirclesCreateApiCall(CirclesRequest.CircleCreate request);

    Single<CirclesResponse.CircleInviteResponse> doCirclesInviteApiCall(CirclesRequest.CircleInvite request);

    Single<CirclesResponse.CircleJoinRejectResponse> doCirclesJoinApiCall(CirclesRequest.CircleJoinReject request);

    Single<CirclesResponse.CircleJoinRejectResponse> doCirclesRejectApiCall(CirclesRequest.CircleJoinReject request);

    Single<CirclesResponse.CircleQuitResponse> doCirclesQuitApiCall(CirclesRequest.CircleQuit request);

    Single<InscriptionResponse> doInscriptionApiCall(InscriptionRequest.InscriptionCreate request);

    Single<ConversationResponse.ConversationInfo> doConversationInfoApiCall(ConversationRequest.ConversationInfo request);

    Single<ConversationResponse.ConversationPic> doConversationPicApiCall(ConversationRequest.ConversationPic request);

    Single<ConversationResponse.ConversationMessageInfo> doConversationMessageInfoApiCall(ConversationRequest.ConversationMessageInfo request);

    Single<ConversationResponse.ConversationList> doConversationListApiCall(ConversationRequest.ConversationList request);

    Single<ConversationResponse.ConversationUploadMedia> doConversationUploadMediaApiCall(ConversationRequest.ConversationUploadMedia request, int media_id, String token);

    Single<ConversationResponse.ConversationMessageSend> doConversationMessageSendApiCall(ConversationRequest.ConversationMessageSend request);

    Single<ConversationResponse.ConversationUpdate> doConversationUpdateApiCall(ConversationRequest.ConversationUpdate request);

    Single<ConversationResponse.ConversationInvite> doConversationInviteApiCall(ConversationRequest.ConversationInvite request);

    Single<ConversationResponse.ConversationQuit> doConversationQuitApiCall(ConversationRequest.ConvrsationQuit request);

    Single<ConversationResponse.ConversationCreate> doConversationCreateApiCall(ConversationRequest.ConversationCreate request);

    Single<NotifResponse> doNotifSendTokenApiCall(NotifRequest.NotifSendToken request);

    Single<UserInfoResponse> doUserInfoApiCall(UserInfoRequest.ApiUserInfoRequest request);

    Single<SettingsResponse.ApiModifyMdpRespose> doModifyPassApiCall(SettingsRequest.ApiModifyMdpRequest request);

    Single<SettingsResponse.ApiModifyResponse> doModifyApiCall(SettingsRequest.ApiModifySettings request);

    ApiHeader getApiHeader();

    Single<BlogResponse> getBlogApiCall();

    Single<OpenSourceResponse> getOpenSourceApiCall();


}
