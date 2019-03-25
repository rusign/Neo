package com.mindorks.framework.mvvm.ui.chatlist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.data.model.api.BlogResponse;
import com.mindorks.framework.mvvm.data.model.api.ChatlListResponse;
import com.mindorks.framework.mvvm.databinding.ActivityChatlistBinding;
import com.mindorks.framework.mvvm.ui.base.BaseActivity;
import com.mindorks.framework.mvvm.ui.call.CallActivity;
import com.mindorks.framework.mvvm.ui.chat.ChatActivity;
import com.mindorks.framework.mvvm.ui.contacts.ContactsActivity;
import com.mindorks.framework.mvvm.ui.home.HomeActivity;
import com.mindorks.framework.mvvm.ui.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.inject.Inject;

public class ChatlistActivity extends BaseActivity<ActivityChatlistBinding, ChatlistViewModel> implements ChatlistNavigator, ChatlistAdapter.ChatlistAdapterListener {

    @Inject
    ChatlistAdapter mChatlistAdapter;
    ActivityChatlistBinding _mActivityChatlistBinding;
    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    ChatlistViewModel _chatlistVM;


    Dialog myDialog;
    String token;
    private Socket socket;
    HashMap<String, Integer> circlesMap = new HashMap<String, Integer>();
    List<String> emailByCircle = new ArrayList<String>();

    public static Intent newIntent(Context context) {
        return new Intent(context, ChatlistActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_chatlist;
    }

    @Override
    public ChatlistViewModel getViewModel() {
        return _chatlistVM;
    }

    @Override
    public void updateBlog(List<ChatlListResponse.Chatlist> blogList) {
        mChatlistAdapter.addItems(blogList);
    }

    @Override
    public void displayDialogAddConv() {
        Log.i("displayDialogAddConv", "displayDialogAddConv");
        if (circlesMap.size() < 1){
            Toast.makeText(ChatlistActivity.this, "Rejoigner un cercle avant de créer une conversation" , Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Integer> entry : circlesMap.entrySet()){
            list.add(entry.getKey());
        }
        final CharSequence[] tmplist = list.toArray(new CharSequence[list.size()]) ;
        final CharSequence[] res = new CharSequence[1];
        if (tmplist.length > 0)
         res[0] = tmplist[0];
        int checkItem = 0;
        final EditText input = new EditText(ChatlistActivity.this);
        input.setHint("Nom de la conversation");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        AlertDialog.Builder builder = new AlertDialog.Builder(ChatlistActivity.this);
        builder.setTitle("Nouvelle conversation")
                .setView(input)
                .setSingleChoiceItems(tmplist,checkItem, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        res[0] = tmplist[which];
                    }
                })
                .setPositiveButton("Continuer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (input.getText().toString().equals("") )
                            Toast.makeText(ChatlistActivity.this, "Echec : le nom de vide" , Toast.LENGTH_SHORT).show();
                        else
                        {
                            _chatlistVM.getEmailFromServer(circlesMap.get(res[0].toString()), input.getText().toString());
                        }
                    }
                });
        builder.show();
    }

    public void displayEmail(List<String> listEmail, String nom, int conv_id){
        Log.i("displayDialogAddConv", "displayDialogAddConv");
        if (listEmail.size() < 1){
            Toast.makeText(ChatlistActivity.this, "Personne dans le cercle" , Toast.LENGTH_SHORT).show();
            return;
        }
        final CharSequence[] tmplist = listEmail.toArray(new CharSequence[listEmail.size()]) ;
        final CharSequence[] res = new CharSequence[1];
        if (tmplist.length > 0)
            res[0] = tmplist[0];
        int checkItem = 0;

        AlertDialog.Builder builder = new AlertDialog.Builder(ChatlistActivity.this);
        builder.setTitle("Choisir le partenaire")
                .setSingleChoiceItems(tmplist,checkItem, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        res[0] = tmplist[which];
                    }
                })
                .setPositiveButton("Créer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("dialogNameConv", nom);
                        _chatlistVM.createConv(conv_id, nom, res[0].toString());
                    }
                });
        builder.show();
    }

    @Override
    public void toastMessage(String message) {
        Toast.makeText(ChatlistActivity.this,  message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addCircleToMap(String name, int id) {
        circlesMap.put(name, id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //toolbar.setTitle("Conversation");
        //recycler stuff
        _chatlistVM.setNavigator(this);
        mChatlistAdapter.setListener(this);
        mChatlistAdapter.activity = ChatlistActivity.this;
        _mActivityChatlistBinding = getViewDataBinding();
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        _mActivityChatlistBinding.chatlistRecyclerviewId.setLayoutManager(new LinearLayoutManager(this));
        _mActivityChatlistBinding.chatlistRecyclerviewId.setItemAnimator((new DefaultItemAnimator()));
        _mActivityChatlistBinding.chatlistRecyclerviewId.setAdapter(mChatlistAdapter);
        _mActivityChatlistBinding.chatlistRecyclerviewId.setHasFixedSize(true);
        subscribeToLiveData();
        myDialog = new Dialog(this);
    }

    private void subscribeToLiveData() {
        Log.i("subscribeToLiveData","subscribeToLiveData activity" );

        _chatlistVM.getChatlistListLiveData().observe(this, chatlists -> _chatlistVM.addChatlistItemsToList(chatlists));
    }

    @Override
    public void onRetryClick() {
        Log.i("TestClick","onRetryClick activity" );
    }

    @Override
    public void onChatActivity(Integer id, Integer circle_id) {
        close();

        Intent intent = ChatActivity.newIntent(ChatlistActivity.this);
        Log.i("onChatActivity id", String.valueOf(id));
        Log.i("onChatActivity circleid", String.valueOf(circle_id));
        intent.putExtra("id", id);
        intent.putExtra("circle_id", circle_id);
        startActivity(intent);
    }

    @Override
    public void openHome() {
        close();

        Intent intent = HomeActivity.newIntent(ChatlistActivity.this);
        startActivity(intent);
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
                        Intent intent = CallActivity.newIntent(ChatlistActivity.this);
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
                if(!isFinishing())
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
        token = _chatlistVM.GetToken();
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