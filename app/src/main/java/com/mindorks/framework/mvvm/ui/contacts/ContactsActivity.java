package com.mindorks.framework.mvvm.ui.contacts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.databinding.ActivityContactsBinding;
import com.mindorks.framework.mvvm.ui.base.BaseActivity;
import com.mindorks.framework.mvvm.ui.call.CallActivity;
import com.mindorks.framework.mvvm.ui.chatlist.ChatlistActivity;
import com.mindorks.framework.mvvm.ui.contacts.circleinvitation.CircleInvitationActivity;
import com.mindorks.framework.mvvm.ui.home.HomeActivity;
import com.mindorks.framework.mvvm.ui.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


public class ContactsActivity extends BaseActivity<ActivityContactsBinding, ContactsViewModel> implements ContactsNavigator {

    @Inject
    ContactsViewModel _ContactsVM;
    private ActivityContactsBinding _mActivityContactsBinding;
    List<ContactsCircleItem> lstCircle ;
    RecyclerView myrv;
    ImageButton imgBut;


    Dialog myDialog;
    String token;
    private Socket socket;

    public static Intent newIntent(Context context) {
        return new Intent(context, ContactsActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_contacts;
    }

    @Override
    public ContactsViewModel getViewModel() {
        return _ContactsVM;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _mActivityContactsBinding = getViewDataBinding();
        _ContactsVM.setNavigator(this);

        lstCircle = new ArrayList<>();
        imgBut = (ImageButton)findViewById(R.id.imgbut);
        imgBut.setBackgroundResource(R.drawable.ic_invitation_grey);
        imgBut.setClickable(false);
        myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        ContactsAdapter myAdapter = new ContactsAdapter(this,lstCircle);
        myrv.setLayoutManager(new GridLayoutManager(this,2));
        myrv.setAdapter(myAdapter);
        myAdapter.activity = ContactsActivity.this;
        _ContactsVM.getCircles();
        _ContactsVM.checkInvite();
        myDialog = new Dialog(this);
    }

        


    @Override
    public void setCircles(String name, int id) {
        Log.i("setCircles ", "setCircles");
        lstCircle.add(new ContactsCircleItem(name,"cercle neo", id));

        myrv.getAdapter().notifyDataSetChanged();

    }

    @Override
    public void displayDialogAddCircle() {
        Log.i("displayDialogAddCircle", "displayDialogAddCircle");
        final EditText input = new EditText(ContactsActivity.this);
        input.setHint("Nom du cercle");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        AlertDialog.Builder builder = new AlertDialog.Builder(ContactsActivity.this);
        builder.setTitle("Nouveaux Cercle")
                .setView(input)
                .setPositiveButton("Créer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (input.getText().toString().equals(""))
                            Toast.makeText(ContactsActivity.this, "Echec : le nom de vide" , Toast.LENGTH_SHORT).show();
                        else
                        {
                            _ContactsVM.createCircle(input.getText().toString());
                        }
                    }
                });
        builder.show();
    }

    @Override
    public void toastMessage(String message) {
        Toast.makeText(ContactsActivity.this, message , Toast.LENGTH_SHORT).show();

    }

    @Override
    public void openHome() {
        close();
        Intent intent = HomeActivity.newIntent(ContactsActivity.this);
        startActivity(intent);
    }

    @Override
    public void openInvitation() {
        close();
        Intent intent = CircleInvitationActivity.newIntent(ContactsActivity.this);
        startActivity(intent);
    }

    @Override
    public void enableInvitationIcon() {
        imgBut.setBackgroundResource(R.drawable.ic_invitation);
        imgBut.setClickable(true);
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

                        Intent intent = CallActivity.newIntent(ContactsActivity.this);
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
        token = _ContactsVM.GetToken();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("on bback pree", "back");
        openHome();
    }
}