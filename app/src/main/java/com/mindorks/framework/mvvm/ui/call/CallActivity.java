package com.mindorks.framework.mvvm.ui.call;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.databinding.ActivityCallBinding;
import com.mindorks.framework.mvvm.ui.base.BaseActivity;
import com.mindorks.framework.mvvm.ui.chat.ChatActivity;
import com.mindorks.framework.mvvm.ui.chatlist.ChatlistActivity;
import com.mindorks.framework.mvvm.ui.home.HomeActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.AudioSource;
import org.webrtc.AudioTrack;
import org.webrtc.Camera1Enumerator;
import org.webrtc.CameraEnumerator;
import org.webrtc.DefaultVideoDecoderFactory;
import org.webrtc.DefaultVideoEncoderFactory;
import org.webrtc.EglBase;
import org.webrtc.IceCandidate;
import org.webrtc.Logging;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.SessionDescription;
import org.webrtc.SurfaceViewRenderer;
import org.webrtc.VideoCapturer;
import org.webrtc.VideoRenderer;
import org.webrtc.VideoSource;
import org.webrtc.VideoTrack;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CallActivity extends BaseActivity<ActivityCallBinding, CallViewModel> implements CallNavigator , SignallingClient.SignalingInterface, View.OnClickListener {

    @Inject
    CallViewModel mCallViewModel;
    private ActivityCallBinding mActivityCallBinding;

    // ----------------- for call -------------
    PeerConnectionFactory peerConnectionFactory;
    MediaConstraints audioConstraints;
    MediaConstraints videoConstraints;
    MediaConstraints sdpConstraints;
    VideoSource videoSource;
    VideoTrack localVideoTrack;
    AudioSource audioSource;
    AudioTrack localAudioTrack;
    MediaStream stream;
    SurfaceViewRenderer localVideoView;
    SurfaceViewRenderer remoteVideoView;

    Button hangup;
    PeerConnection localPeer;
    EglBase rootEglBase;

    Boolean isInitiator = false;
    int caller = 0;
    int userId;
    int conv_id =0;
    boolean gotUserMedia;
    List<PeerConnection.IceServer> peerIceServers = new ArrayList<>();

    private static final String TAG = "CallActivity";

    public static Intent newIntent(Context context) {
        return new Intent(context, CallActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_call;
    }

    @Override
    public CallViewModel getViewModel() {
        return mCallViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void toastServerError()
    {
        Toast.makeText(this, getString(R.string.invalid_email_password), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openMainActivity() {
        Intent intent = HomeActivity.newIntent(CallActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         peerConnectionFactory = null;
         audioConstraints= null;
         videoConstraints= null;
         sdpConstraints= null;
         videoSource= null;
         localVideoTrack= null;
         audioSource= null;
         localAudioTrack= null;
         stream= null;
         localVideoView= null;
         remoteVideoView= null;
        mActivityCallBinding = getViewDataBinding();
        mCallViewModel.setNavigator(this);
        userId = getIntent().getIntExtra("id", 0);
        isInitiator = getIntent().getBooleanExtra("isIni", true);
        caller = getIntent().getIntExtra("caller", 0);
        conv_id = getIntent().getIntExtra("conv_id", 0);

        Log.d(TAG, "userid: " + String.valueOf(userId));
        initViews();
        initVideos();
        if (isInitiator){
            SignallingClient.getInstance().init(this, mCallViewModel.GetToken(), userId, false, conv_id);
            sendCalling();
        }
        else {
            SignallingClient.getInstance().init(this, mCallViewModel.GetToken(), userId, true, conv_id);
        }
    }

    private void initViews() {
        hangup = findViewById(R.id.end_call);
        localVideoView = findViewById(R.id.local_gl_surface_view);
        remoteVideoView = findViewById(R.id.remote_gl_surface_view);
        hangup.setOnClickListener(this);
    }

    private void initVideos() {
        rootEglBase = EglBase.create();
        localVideoView.init(rootEglBase.getEglBaseContext(), null);
        remoteVideoView.init(rootEglBase.getEglBaseContext(), null);
        localVideoView.setZOrderMediaOverlay(true);
        remoteVideoView.setZOrderMediaOverlay(true);
    }


    private void getIceServers() {
        // STUN Server address
        PeerConnection.IceServer peerIceServerSTUN = PeerConnection.IceServer.builder("stun:webrtc.neo.ovh:3478").createIceServer();
        peerIceServers.add(peerIceServerSTUN);

        // TURN Server address
        Log.i("init ice username ", SignallingClient.getInstance().getUsername());
        Log.i("init ice password ", SignallingClient.getInstance().getPassword());
        PeerConnection.IceServer peerIceServerTURN = PeerConnection.IceServer.builder("turn:webrtc.neo.ovh:3478")
                                .setUsername(SignallingClient.getInstance().getUsername())
                                .setPassword(SignallingClient.getInstance().getPassword())
                                .createIceServer();
        peerIceServers.add(peerIceServerTURN);
    }

    @Override
    public void start() {
        getIceServers();

        PeerConnectionFactory.InitializationOptions initializationOptions =
                PeerConnectionFactory.InitializationOptions.builder(this)
                        .setEnableVideoHwAcceleration(true)
                        .createInitializationOptions();
        PeerConnectionFactory.initialize(initializationOptions);
        PeerConnectionFactory.Options options = new PeerConnectionFactory.Options();
        peerConnectionFactory = new PeerConnectionFactory(options);

        VideoCapturer videoCapturerAndroid;
        videoCapturerAndroid = createCameraCapturer(new Camera1Enumerator(false));

        audioConstraints = new MediaConstraints();
        videoConstraints = new MediaConstraints();

        if (videoCapturerAndroid != null) {
            videoSource = peerConnectionFactory.createVideoSource(videoCapturerAndroid);
        }
        localVideoTrack = peerConnectionFactory.createVideoTrack("100", videoSource);
        if (videoCapturerAndroid != null) {
            videoCapturerAndroid.startCapture(1024, 720, 30);
        }

        audioSource = peerConnectionFactory.createAudioSource(audioConstraints);
        localAudioTrack = peerConnectionFactory.createAudioTrack("101", audioSource);
        localVideoView.setVisibility(View.VISIBLE);

        localVideoTrack.addSink(localVideoView);
       // localRenderer = new VideoRenderer(localVideoView);
        //localVideoTrack.addRenderer(localRenderer);

        localVideoView.setMirror(true);
        remoteVideoView.setMirror(true);

        gotUserMedia = true;
//        if (SignallingClient.getInstance().isInitiator) {
//            onTryToStart();
//        }

        if (isInitiator){
            startCall();
        }
        else {
            joinACall();
        }
    }

    @Override
    public void onTryToStart() {
        runOnUiThread(() -> {
            if (!SignallingClient.getInstance().isStarted && localVideoTrack != null && SignallingClient.getInstance().isChannelReady) {
                createPeerConnection();
                SignallingClient.getInstance().isStarted = true;
                if (SignallingClient.getInstance().isInitiator) {
                    doCall();
                }
            }
        });
    }

    @Override
    public void onRemoteHangUp(String msg) {

    }

    @Override
    public void onOfferReceived(JSONObject data) {
        showToast("Received Offer");
        runOnUiThread(() -> {
            if (!SignallingClient.getInstance().isInitiator && !SignallingClient.getInstance().isStarted) {
                onTryToStart();
            }
            try {
                localPeer.setRemoteDescription(new CustomSdpObserver("localSetRemote"), new SessionDescription(SessionDescription.Type.OFFER, data.getString("sdp")));
                doAnswer();
                updateVideoViews(true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void doAnswer() {
        localPeer.createAnswer(new CustomSdpObserver("localCreateAns") {
            @Override
            public void onCreateSuccess(SessionDescription sessionDescription) {
                super.onCreateSuccess(sessionDescription);
                localPeer.setLocalDescription(new CustomSdpObserver("localSetLocal"), sessionDescription);
                Log.i("doAnswer", sessionDescription.toString());
                SignallingClient.getInstance().emitMessage(sessionDescription);
            }
        }, new MediaConstraints());
    }


    @Override
    public void onAnswerReceived(JSONObject data) {
        showToast("Received Answer");
        try {
            localPeer.setRemoteDescription(new CustomSdpObserver("localSetRemote"), new SessionDescription(SessionDescription.Type.fromCanonicalForm(data.getString("type").toLowerCase()), data.getString("sdp")));
            updateVideoViews(true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onIceCandidateReceived(JSONObject data) {
        Log.i("onIceCandidateReceived", "onIceCandidateReceived");
        try {
            localPeer.addIceCandidate(new IceCandidate(data.getString("sdpMid"), data.getInt("sdpMLineIndex"), data.getString("candidate")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.end_call: {
                hangup();
                break;
            }
        }
    }

    private void hangup() {
        Log.i("hangup ", "hangup");
        leaveCall();
    }

    private void callE() {
        Log.i("callE ", "callE");
        SignallingClient.getInstance().callEmail("");
        SignallingClient.getInstance().isInitiator = true;
        onTryToStart();;
    }

    @Override
    public void sendCalling() {
        Log.i("sendCalling ", "sendCalling");
        SignallingClient.getInstance().callUserId();
        SignallingClient.getInstance().isInitiator = true;
    }

    @Override
    public void startCall() {
        onTryToStart();;
    }

    @Override
    public void joinACall() {
        Log.i("callE ", "callE");
        SignallingClient.getInstance().join();
        onTryToStart();;
    }

    @Override
    public void declineACall() {
        Log.i("callE ", "callE");
        SignallingClient.getInstance().decline();
        onTryToStart();;
    }

    private void createPeerConnection() {
        PeerConnection.RTCConfiguration rtcConfig =
                new PeerConnection.RTCConfiguration(peerIceServers);
        rtcConfig.tcpCandidatePolicy = PeerConnection.TcpCandidatePolicy.DISABLED;
        rtcConfig.bundlePolicy = PeerConnection.BundlePolicy.MAXBUNDLE;
        rtcConfig.rtcpMuxPolicy = PeerConnection.RtcpMuxPolicy.REQUIRE;
        rtcConfig.continualGatheringPolicy = PeerConnection.ContinualGatheringPolicy.GATHER_CONTINUALLY;
        rtcConfig.keyType = PeerConnection.KeyType.ECDSA;
        localPeer = peerConnectionFactory.createPeerConnection(rtcConfig, new CustomPeerConnectionObserver("localPeerCreation") {
            @Override
            public void onIceCandidate(IceCandidate iceCandidate) {
                super.onIceCandidate(iceCandidate);
                onIceCandidateReceived(iceCandidate);
            }

            @Override
            public void onAddStream(MediaStream mediaStream) {
                super.onAddStream(mediaStream);
                gotRemoteStream(mediaStream);
            }
        });

        addStreamToLocalPeer();
    }

    private void addStreamToLocalPeer() {
        stream = peerConnectionFactory.createLocalMediaStream("102");
        stream.addTrack(localAudioTrack);
        stream.addTrack(localVideoTrack);
        localPeer.addStream(stream);
    }

    private void doCall() {
        sdpConstraints = new MediaConstraints();
        sdpConstraints.mandatory.add(
                new MediaConstraints.KeyValuePair("OfferToReceiveAudio", "true"));
        sdpConstraints.mandatory.add(new MediaConstraints.KeyValuePair(
                "OfferToReceiveVideo", "true"));
        localPeer.createOffer(new CustomSdpObserver("localCreateOffer") {
            @Override
            public void onCreateSuccess(SessionDescription sessionDescription) {
                super.onCreateSuccess(sessionDescription);
                localPeer.setLocalDescription(new CustomSdpObserver("localSetLocalDesc"), sessionDescription);
                Log.d("onCreateSuccess", "SignallingClient emit ");
                SignallingClient.getInstance().emitMessage(sessionDescription);
            }
        }, sdpConstraints);
    }


    private VideoCapturer createCameraCapturer(CameraEnumerator enumerator) {
        final String[] deviceNames = enumerator.getDeviceNames();

        Logging.d(TAG, "Looking for front facing cameras.");
        for (String deviceName : deviceNames) {
            if (enumerator.isFrontFacing(deviceName)) {
                Logging.d(TAG, "Creating front facing camera capturer.");
                VideoCapturer videoCapturer = enumerator.createCapturer(deviceName, null);
                if (videoCapturer != null) {
                    return videoCapturer;
                }
            }
        }

        Logging.d(TAG, "Looking for other cameras.");
        for (String deviceName : deviceNames) {
            if (!enumerator.isFrontFacing(deviceName)) {
                Logging.d(TAG, "Creating other camera capturer.");
                VideoCapturer videoCapturer = enumerator.createCapturer(deviceName, null);

                if (videoCapturer != null) {
                    return videoCapturer;
                }
            }
        }

        return null;
    }

    private void gotRemoteStream(MediaStream stream) {
        //we have remote video stream. add to the renderer.
        final VideoTrack videoTrack = stream.videoTracks.get(0);
        Log.i("video track", videoTrack.id());
        Log.i("video track", videoTrack.kind());
        runOnUiThread(() -> {
            try {
                remoteVideoView.setVisibility(View.VISIBLE);
                videoTrack.addSink(remoteVideoView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void updateVideoViews(final boolean remoteVisible) {
        runOnUiThread(() -> {
            ViewGroup.LayoutParams params = localVideoView.getLayoutParams();
            if (remoteVisible) {
                params.height = dpToPx(100);
                params.width = dpToPx(100);
            } else {
                params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }
            localVideoView.setLayoutParams(params);
        });
    }

    public void showToast(final String msg) {
        runOnUiThread(() -> Toast.makeText(CallActivity.this, msg, Toast.LENGTH_SHORT).show());
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public void onIceCandidateReceived(IceCandidate iceCandidate) {
        SignallingClient.getInstance().emitIceCandidate(iceCandidate);
    }

    public void leaveCall(){
        if (localPeer != null){
            localPeer.close();
            localPeer = null;
            SignallingClient.getInstance().close();
            updateVideoViews(false);

          }

        SignallingClient.getInstance().quitting();
        SignallingClient.getInstance().close();
        Intent intent = ChatlistActivity.newIntent(CallActivity.this);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (localPeer != null){
            localPeer.close();
            localPeer = null;
            SignallingClient.getInstance().close();
            updateVideoViews(false);
        }
        SignallingClient.getInstance().close();

    }
}
