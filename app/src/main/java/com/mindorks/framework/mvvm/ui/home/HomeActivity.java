package com.mindorks.framework.mvvm.ui.home;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.databinding.ActivityHomeBinding;
import com.mindorks.framework.mvvm.ui.base.BaseActivity;
import com.mindorks.framework.mvvm.ui.call.CallActivity;
import com.mindorks.framework.mvvm.ui.chat.ChatActivity;
import com.mindorks.framework.mvvm.ui.chatlist.ChatlistActivity;
import com.mindorks.framework.mvvm.ui.contacts.ContactsActivity;
import com.mindorks.framework.mvvm.ui.settings.SettingsActivity;
import com.mindorks.framework.mvvm.ui.settings.SettingsPrefActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import javax.inject.Inject;


public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomeViewModel> implements HomeNavigator {

    @Inject
    HomeViewModel _homeVM;
    Dialog myDialog;
    String token;
    private Socket socket;
    private ActivityHomeBinding _mActivityHomeBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, com.mindorks.framework.mvvm.ui.home.HomeActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public HomeViewModel getViewModel() {
        return _homeVM;
    }


    @Override
    public void openSettingsActivity() {
        close();
        Intent intent = SettingsActivity.newIntent(HomeActivity.this);
        startActivity(intent);
        //startActivity(new Intent(HomeActivity.this, SettingsPrefActivity.class));
    }

    @Override
    public void openChatlistActivity(){
        close();
        Intent intent = ChatlistActivity.newIntent(HomeActivity.this);
        startActivity(intent);
    }

    @Override
    public void openContactsActivity() {
        close();
        Intent intent = ContactsActivity.newIntent(HomeActivity.this);
        startActivity(intent);
    }

    @Override
    public void openMedicalActivity() {
        Toast.makeText(HomeActivity.this, "Disponible prochainement" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openNeoActivity() {
        Toast.makeText(HomeActivity.this, "Disponible prochainement" , Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            Log.i("permission audio", "not granted");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, 225 );

        }
        Log.i("onCreate home ", "onCreate home ");
        _mActivityHomeBinding = getViewDataBinding();
        _homeVM.setNavigator(this);
        _homeVM.getUserInfo();
        myDialog = new Dialog(this);
    }


    public void ShowPopup(int id) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i("showPopup", " showPopup showPopup");
                TextView name;
                LinearLayout btndecline;
                LinearLayout btnaccept;
                myDialog.setContentView(R.layout.call_popup);
                name = (TextView) myDialog.findViewById(R.id.namepopup);
                name.setText(String.valueOf(id));
                btndecline = (LinearLayout) myDialog.findViewById(R.id.declinecall);
                btnaccept = (LinearLayout) myDialog.findViewById(R.id.acceptecall);
                btnaccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("ShowPopup", "accept call");
                        close();
                        myDialog.dismiss();
                        Intent intent = CallActivity.newIntent(HomeActivity.this);
                        intent.putExtra("isIni", false);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                });
                btndecline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("ShowPopup", "btndecline call");
                        decline(id);
                        myDialog.dismiss();
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });
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

    private Emitter.Listener onWebrtcForward = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("onWebrtcForward", args[0].toString());
            JSONObject objs = (JSONObject) args[0];
            JSONObject obj = null;
            Object messageObj =null;
            int id = 0;
            try {
                id = objs.getInt("sender_id");
                Log.i("Homeac id", String.valueOf(id));
                messageObj = (Object)((JSONObject)objs.get("content")).get("message");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if( (Object)messageObj instanceof String ) {
                    String val = ((JSONObject)objs.get("content")).getString("message");
                    Log.i("valmessage", val);
                    switch (val){
                        case "CALLING":
                            ShowPopup(id);
                            break;
                        case "READY":
                            break;
                        case "PING":
                            break;
                        case "PONG":
                            break;
                        case "QUITTING":
                            myDialog.dismiss();
                            break;
                        case "UNAVAILABLE":
                            break;
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            };
        }
    };

    public void decline(int userId)
    {
        Log.i("decline home", "decline home");
        JSONObject objcs = new JSONObject();
        try {
            objcs.put("user_id", userId);
            objcs.put("message", "UNAVAILABLE");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("webrtc_forward", objcs);
    }
    public void close() {
        socket.disconnect();
        socket.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("onResume home", "onResume home");
        token = _homeVM.GetToken();
        try {
            socket = IO.socket("wws://api.neo.ovh");
            socket.on(Socket.EVENT_CONNECT, onConnect);
            socket.on(Socket.EVENT_DISCONNECT, onDisconnect);
            socket.on("error", onError);
            socket.on("success", onSuccess);
            socket.on("webrtc_forward", onWebrtcForward);
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
