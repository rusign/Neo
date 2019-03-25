package com.mindorks.framework.mvvm.ui.call;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.IceCandidate;
import org.webrtc.SessionDescription;

import java.net.URISyntaxException;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

class SignallingClient {
    private static SignallingClient instance;
    private Socket socket;
    boolean isChannelReady = false;
    boolean isInitiator = false;
    boolean isStarted = false;
    private SignalingInterface callback;
    public String token;
    private String username;
    private String password;
    private String  email = "nico.r@gmail.com";
    private int userId;
    Boolean isAlive = false;
    Boolean doStart = false;
    Boolean isPingPong = false;
    int conv_id = 0;
    public static SignallingClient getInstance() {
        if (instance == null) {
            instance = new SignallingClient();
        }
        return instance;
    }

    public void init(SignalingInterface signalingInterface, String token, int userId, Boolean doStart, int conv_id) {
        this.callback = signalingInterface;
        try {
            this.token = token;
            this.conv_id = conv_id;
            this.userId = userId;
            this.doStart = doStart;
            isChannelReady = false;
            isInitiator = false;
            isStarted = false;
            socket = IO.socket("https://api.neo.ovh");
            socket.on(Socket.EVENT_CONNECT, onConnect);
            socket.on(Socket.EVENT_DISCONNECT, onDisconnect);
            socket.on("webrtc_config", onWebrtcConfig);
            socket.on("webrtc_forward", onWebrtcForward);
            socket.on("error", onError);
            socket.on("success", onSuccess);
            socket.connect();
            Log.d("SignallingClient", "init() called");
            Log.d("SignallingClient", String.valueOf(userId));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject objc = new JSONObject();
            try {
                objc.put("token", token);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            socket.emit("authenticate" , objc);
            socket.emit("webrtc_credentials");

        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("Socket disconect", "disco");
        }
    };

    private Emitter.Listener onError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            for (int i = 0; i< args.length; i++)
                Log.i("Socket Error", args[i].toString());
        }
    };

    private Emitter.Listener onSuccess = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            for (int i = 0; i< args.length; i++)
                Log.i("Socket Success", args[i].toString());
        }
    };

    private Emitter.Listener onWebrtcConfig = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("onWebrtcConfig" ,args[0].toString());
            JSONObject obj = (JSONObject)args[0];
            try {
                username = (String)obj.get("username");
                password = (String)obj.get("password");
                isChannelReady = true;
                if (doStart){
                    callback.start();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private Emitter.Listener onWebrtcForward = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("onWebrtcForward", args[0].toString());
            JSONObject objs = (JSONObject) args[0];
            JSONObject obj = null;
            Object messageObj =null;
            try {
                messageObj = (Object)((JSONObject)objs.get("content")).get("message");
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            try {
//                String type = obj.getString("type");
//                if (type.equalsIgnoreCase("offer")) {
//                    callback.onOfferReceived(obj);
//                } else if (type.equalsIgnoreCase("answer") && isStarted) {
//                    callback.onAnswerReceived(obj);
//                } else if (type.equalsIgnoreCase("candidate") && isStarted) {
//                    callback.onIceCandidateReceived(obj);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }



            try {
                if( (Object)messageObj instanceof String ) {
                    String val = ((JSONObject)objs.get("content")).getString("message");
                    Log.i("valmessage", val);
                    switch (val){
                        case "CALLING":
                            break;
                        case "READY":
                            isAlive = true;
                            callback.start();
                           // sendPing();
                            startPingPong();
                          //  callback.startCall();
                            break;
                        case "PING":
                            isPingPong = true;
                            sendPong();
                            break;
                        case "PONG":
                            isPingPong = true;
                            sendPing();
                            break;
                        case "QUITTING":
                            callback.leaveCall();
                            break;
                        case "UNAVAILABLE":
                            callback.leaveCall();
                            break;
                    }
                }
                else {
                    Log.i("isobject", "isobject");
                    obj = (JSONObject)((JSONObject)objs.get("content")).get("message");
                    String type = obj.getString("type");
                    Log.i("objecttype", type);

                    if (type.equalsIgnoreCase("sdp") && !isInitiator) {
                        callback.onOfferReceived((JSONObject)obj.get("data"));
                    } else if (type.equalsIgnoreCase("sdp") && isStarted && isInitiator) {
                        callback.onAnswerReceived((JSONObject)obj.get("data"));
                    } else if (type.equalsIgnoreCase("candidate") && isStarted) {
                        callback.onIceCandidateReceived((JSONObject)obj.get("data"));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    public void emitMessage(SessionDescription message) {
        try {
            Log.d("SignallingClient", "emitMessage() called with: message = [" + message + "]");
            JSONObject obj = new JSONObject();
            JSONObject mes = new JSONObject();
            JSONObject data = new JSONObject();
            Log.d("SessionDescription mes", "emitMessage() called with: message = [" + message + "]");
            obj.put("user_id", userId);
            mes.put("type", "sdp");
            data.put("type",message.type.canonicalForm());
            data.put("sdp", message.description);
            mes.put("data", (Object)data);
            obj.put ("message", (Object)mes);
            socket.emit("webrtc_forward", obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void emitIceCandidate(IceCandidate iceCandidate) {
        try {
            Log.d("emitIceCandidate", "emitIceCandidate() called with: message = [" + iceCandidate.toString() + "]");
            JSONObject obj = new JSONObject();
            JSONObject mes = new JSONObject();
            JSONObject data = new JSONObject();
            obj.put("user_id", userId);
            mes.put("type", "candidate");
            data.put("type", "candidate");
            data.put("sdpMLineIndex", iceCandidate.sdpMLineIndex);
            data.put("sdpMid", iceCandidate.sdpMid);
            data.put("candidate", iceCandidate.sdp);
            mes.put("data", (Object)data);
            obj.put("message", (Object)mes);
            socket.emit("webrtc_forward", obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void close() {
        socket.disconnect();
        socket.close();
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void callEmail(String eml)
    {
        Log.i("callEmail ", email);
        JSONObject objcs = new JSONObject();
        try {
            objcs.put("user_id", userId);
            objcs.put("message", "CALLING");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("webrtc_forward", objcs);
    }

    public void callUserId()
    {
        Log.i("callUserId ", email);
        JSONObject objcs = new JSONObject();
        try {
            objcs.put("user_id", userId);
            objcs.put("message", "CALLING");
            setTimeout(new Runnable() {
                @Override
                public void run() {

                    checkQuit();
                }
            }, 25000);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("webrtc_forward", objcs);
    }

    public void join()
    {
        Log.i("join ", "join");
        JSONObject objcs = new JSONObject();
        try {
            objcs.put("user_id", userId);
            objcs.put("message", "READY");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        startPingPong();
        socket.emit("webrtc_forward", objcs);
    }

    public void decline()
    {
        Log.i("decline signa ", "decline");
        JSONObject objcs = new JSONObject();
        try {
            objcs.put("user_id", userId);
            objcs.put("message", "UNAVAILABLE");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("webrtc_forward", objcs);
    }

    public void quitting()
    {
        Log.i("quitting ", "quitting");
        JSONObject objcs = new JSONObject();
        try {
            objcs.put("user_id", userId);
            objcs.put("message", "QUITTING");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("webrtc_forward", objcs);
    }

    public void sendPing()
    {
        Log.i("sendPing ", "sendPing");
        JSONObject objcs = new JSONObject();
        try {
            objcs.put("user_id", userId);
            objcs.put("message", "PING");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("webrtc_forward", objcs);
    }

    public void sendPong()
    {
        Log.i("sendPong ", "sendPong");
        JSONObject objcs = new JSONObject();
        try {
            objcs.put("user_id", userId);
            objcs.put("message", "PONG");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("webrtc_forward", objcs);
    }

    private void endPingpong(){
        setTimeout(() -> {
            if (isPingPong){
                isPingPong = false;
                startPingPong();
            }
            else{
                Log.i("no ping ", "no ping pong");
                callback.leaveCall();
            }
        }, 5000);
    }

    private void startPingPong(){
        setTimeout(() -> {
            if (isPingPong){
                isPingPong = false;
                startPingPong();
            }
            else{
                sendPing();
                endPingpong();
            }
        }, 5000);
    }

    public void checkQuit(){
        if (isAlive){
            return ;
        }
        JSONObject objcsss = new JSONObject();
        try {
            objcsss.put("conversation_id", conv_id);
            objcsss.put("text_message", "Appel manqué !");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("Socket checkQuit ", objcsss.toString());
        socket.emit("message", objcsss);
        callback.leaveCall();
    }

    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }

    interface SignalingInterface {
        void onRemoteHangUp(String msg);

        void onOfferReceived(JSONObject data);

        void onAnswerReceived(JSONObject data);

        void onIceCandidateReceived(JSONObject data);

        void onTryToStart();

        void start();

        void startCall();

        void joinACall();

        void declineACall();

        void sendCalling();

        void leaveCall();

    }
}
