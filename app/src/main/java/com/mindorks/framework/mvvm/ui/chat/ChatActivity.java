package com.mindorks.framework.mvvm.ui.chat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.fangxu.allangleexpandablebutton.ButtonData;
import com.fangxu.allangleexpandablebutton.ButtonEventListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nkzawa.emitter.Emitter;
import com.google.gson.JsonObject;
import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.data.model.api.ApiContent;
import com.mindorks.framework.mvvm.databinding.ActivityChatBinding;
import com.mindorks.framework.mvvm.ui.base.BaseActivity;
import com.mindorks.framework.mvvm.ui.call.CallActivity;
import com.mindorks.framework.mvvm.ui.chat.chatadd.ChatAddActivity;
import com.mindorks.framework.mvvm.ui.home.HomeActivity;
import com.mindorks.framework.mvvm.ui.login.LoginActivity;
import com.scaledrone.lib.Listener;
import com.scaledrone.lib.Member;
import com.scaledrone.lib.Room;
import com.scaledrone.lib.RoomListener;
import com.scaledrone.lib.Scaledrone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;
import javax.net.ssl.SSLContext;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;


public class ChatActivity extends BaseActivity<ActivityChatBinding, ChatViewModel> implements ChatNavigator, RoomListener {

    @Inject
    ChatViewModel _chatVM;
    private ActivityChatBinding _mActivityChatBinding;
    String _color[]  = {"#3498db", "#1abc9c", "#2ecc71", "#e67e22", "#c0392b", "#f39c12", "#2c3e50", "#2c2c54"};

    private Socket socket;
    private EditText editText;
    private TextView namesText;
    private TextView convNameText;
    private MessageAdapter messageAdapter;
    private ListView messagesView;
    private SpinnerDialog spinnerDialog;
    ArrayList<String> items=new ArrayList<>();
    HashMap<Integer, String> userMap = new HashMap<Integer, String>();
    private int id;
    private int userId;
    String token;
    int circle_id;
    List<String> namesList = new ArrayList<>();
    int idToCall;

    public static final int CAMERA_REQUEST_CODE = 228;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 4192;

    public static Intent newIntent(Context context) {
        return new Intent(context, com.mindorks.framework.mvvm.ui.chat.ChatActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    public ChatViewModel getViewModel() {
        return _chatVM;
    }

    private void showToast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    ////-----------------Take pic ------------------///
    public void onTakePhotoClicked() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            invokeCamera();
        } else {
            // let's request permission.
            String[] permissionRequest = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissionRequest, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    //    private File createImageFile() {
    //        // the public picture director
    //        File picturesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    //
    //        // timestamp makes unique name.
    //        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
    //        String timestamp = sdf.format(new Date());
    //
    //        // put together the directory and the timestamp to make a unique image location.
    //        File imageFile = new File(picturesDirectory, "picture" + timestamp + ".jpg");
    //
    //        return imageFile;
    //    }

    String imageFilePath;
    Uri photoURI;
    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    private void invokeCamera() {

        // get a file reference
       // Uri pictureUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", createImageFile());

        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE
        );
        if(pictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {

                Log.i("package anme", getApplicationContext().getPackageName() );
                 photoURI = FileProvider.getUriForFile(this,getApplicationContext().getPackageName() +".provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoURI);
                startActivityForResult(pictureIntent,
                        CAMERA_REQUEST_CODE);
            }
        }
    }

    private void setListener(AllAngleExpandableButton button) {
        button.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {
                showToast("clicked index:" + index);
                if (index == 1){
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

                    // where do we want to find the data?
                    File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    String pictureDirectoryPath = pictureDirectory.getPath();
                    // finally, get a URI representation
                    Uri data = Uri.parse(pictureDirectoryPath);

                    // set the data and type.  Get all image types.
                    photoPickerIntent.setDataAndType(data, "image/*");

                    // we will invoke this activity, and get something back from it.
                    startActivityForResult(photoPickerIntent, 20);
                }
                else  if (index == 2){
                    onTakePhotoClicked();
                }

            }

            @Override
            public void onExpand() {
//                showToast("onExpand");
            }

            @Override
            public void onCollapse() {
//                showToast("onCollapse");
            }
        });
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("onActivityResult", "onActivityResult");
        if (resultCode == RESULT_OK) {
            Log.i("onActivityResult", "ok");

            if (requestCode == CAMERA_REQUEST_CODE) {
                Toast.makeText(this, "Image Saved.", Toast.LENGTH_LONG).show();
                Uri imageUri = photoURI;
                String filePath =  imageFilePath;
                Log.i("img :", imageUri.toString());
                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    String encoded = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);

                    final Message mes = new Message(encoded, null, true, true);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageAdapter.add(mes);
                            messagesView.setSelection(messagesView.getCount() - 1);
                            List<String> files = new ArrayList<>();
                            files.add("test.png");
                            _chatVM.sendMedia(id, encoded, files, filePath);
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    // show a message to the user indictating that the image is unavailable.
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }
            }
            if (requestCode == 20) {
                Uri imageUri = data.getData();
                String filePath =  getRealPathFromURI(imageUri);
                Log.i("img :", imageUri.toString());
                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    String encoded = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);

                    final Message mes = new Message(encoded, null, true, true);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageAdapter.add(mes);
                            messagesView.setSelection(messagesView.getCount() - 1);
                            List<String> files = new ArrayList<>();
                            files.add("test.png");
                            _chatVM.sendMedia(id, encoded, files, filePath);
                        }
                    });



                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    // show a message to the user indictating that the image is unavailable.
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    private void installButton90to90() {
        final AllAngleExpandableButton button = (AllAngleExpandableButton) findViewById(R.id.button_expandable_90_90);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.drawable.plus, R.drawable.ic_album, R.drawable.ic_camera};
        int[] color = {R.color.blue, R.color.red, R.color.green};
        for (int i = 0; i < 3; i++) {
            ButtonData buttonData;
            if (i == 0) {
                buttonData = ButtonData.buildIconButton(this, drawable[i], 15);
            } else {
                buttonData = ButtonData.buildIconButton(this, drawable[i], 0);
            }
            buttonData.setBackgroundColorId(this, color[i]);
            buttonDatas.add(buttonData);
        }
        button.setButtonDatas(buttonDatas);
        setListener(button);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 225);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.i("permission", "not granted");
        }
        _mActivityChatBinding = getViewDataBinding();
        _chatVM.setNavigator(this);
        installButton90to90();
        editText = (EditText) findViewById(R.id.editText);
        namesText = (TextView) findViewById(R.id.namesId);
        convNameText = (TextView) findViewById(R.id.convNameId);
        messageAdapter = new MessageAdapter(this);
        messagesView = (ListView) findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);
        id = getIntent().getIntExtra("id", 0);
        circle_id = getIntent().getIntExtra("circle_id", 0);
        _chatVM.getMessageFromServer(id);


        spinnerDialog=new SpinnerDialog(ChatActivity.this,items,"Ajoutez une personne", "fermer");// With No Animation
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                _chatVM.addUserToConv(item, id);
               // Toast.makeText(ChatActivity.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
            }
        });



        token = _chatVM.GetToken();
        MemberData data = new MemberData(getRandomName(), getRandomColor());

        Log.i("ID VAlue", String.valueOf(id));
        Log.i("circle ID VAlue", String.valueOf(circle_id));
        try {
            socket = IO.socket("wws://api.neo.ovh");
            socket.on(Socket.EVENT_CONNECT, onConnect);
            socket.on(Socket.EVENT_DISCONNECT, onDisconnect);
            socket.on("message", onMessage);
            socket.on("media", onMedia);
            socket.on("error", onError);
            socket.on("success", onSuccess);
            socket.connect();
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
                socket.emit("authenticate", objc);
                JSONObject objcs = new JSONObject();
                try {
                    objcs.put("circle_id", circle_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                socket.emit("join_circle", objcs);

                JSONObject objcss = new JSONObject();
                try {
                    objcss.put("conversation_id", id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                socket.emit("join_conversation", objcss);
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
            // JSONObject obj = (JSONObject)args[0];
            for (int i = 0; i< args.length; i++)
                Log.i("Socket Error", args[i].toString());
        }
    };

    private Emitter.Listener onSuccess = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            //  JSONObject obj = (JSONObject)args[0];
            for (int i = 0; i< args.length; i++)
                Log.i("Socket Success", args[i].toString());
        }
    };

    private Emitter.Listener onMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject obj = (JSONObject)args[0];
            try {
                Log.i("Socket message", obj.get("message").toString());
                JSONObject objs = (JSONObject)obj.get("message");
                Log.i("Socket message", String.valueOf((int)objs.get("link_id")));

                if ((int)objs.get( "medias") > 0) {
                    if ((int) objs.get("link_id") != userId) {
                        ChatActivity.MemberData dt;
                        if (userMap.containsKey((int) objs.get("link_id")))
                            dt = new ChatActivity.MemberData(userMap.get((int) objs.get("link_id")), _color[(int) objs.get("link_id") % (_color.length - 1)]);
                        else
                            dt = new ChatActivity.MemberData("", _color[(int) objs.get("link_id") % (_color.length - 1)]);
                        _chatVM.getMediaFromServer((int) objs.get("id"), dt, false);
                    }
                }
                else {
                    if ((int) objs.get("link_id") != userId) {
                        ChatActivity.MemberData dt;
                        if (userMap.containsKey((int) objs.get("link_id")))
                            dt = new ChatActivity.MemberData(userMap.get((int) objs.get("link_id")), _color[(int) objs.get("link_id") % (_color.length - 1)]);
                        else
                            dt = new ChatActivity.MemberData("", _color[(int) objs.get("link_id") % (_color.length - 1)]);

                        final Message mes = new Message((String) objs.get("content"), dt, false, false);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                messageAdapter.add(mes);
                                messagesView.setSelection(messagesView.getCount() - 1);
                            }
                        });
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private Emitter.Listener onMedia = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject obj = (JSONObject)args[0];
            try {
                Log.i("Socket onMedia", obj.toString());
                JSONArray objs = (JSONArray)obj.get("media_list");
                JSONObject js = (JSONObject)objs.get(0);
                Log.i("Socket onMedia", String.valueOf((int)js.get("id")));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    public void close() {
        socket.disconnect();
        socket.close();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("onDestroy Socket ", "onDestroy socket");

        socket.disconnect();

        socket.off(Socket.EVENT_CONNECT, onConnect);
        socket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        socket.off("error", onError);
        socket.off("success", onSuccess);
        socket.off("message", onMessage);
    }

    private String getRandomName() {
        String[] adjs = {"autumn", "hidden", "bitter", "misty", "silent", "empty", "dry", "dark", "summer", "icy", "delicate", "quiet", "white", "cool", "spring", "winter", "patient", "twilight", "dawn", "crimson", "wispy", "weathered", "blue", "billowing", "broken", "cold", "damp", "falling", "frosty", "green", "long", "late", "lingering", "bold", "little", "morning", "muddy", "old", "red", "rough", "still", "small", "sparkling", "throbbing", "shy", "wandering", "withered", "wild", "black", "young", "holy", "solitary", "fragrant", "aged", "snowy", "proud", "floral", "restless", "divine", "polished", "ancient", "purple", "lively", "nameless"};
        String[] nouns = {"waterfall", "river", "breeze", "moon", "rain", "wind", "sea", "morning", "snow", "lake", "sunset", "pine", "shadow", "leaf", "dawn", "glitter", "forest", "hill", "cloud", "meadow", "sun", "glade", "bird", "brook", "butterfly", "bush", "dew", "dust", "field", "fire", "flower", "firefly", "feather", "grass", "haze", "mountain", "night", "pond", "darkness", "snowflake", "silence", "sound", "sky", "shape", "surf", "thunder", "violet", "water", "wildflower", "wave", "water", "resonance", "sun", "wood", "dream", "cherry", "tree", "fog", "frost", "voice", "paper", "frog", "smoke", "star"};
        return (
                adjs[(int) Math.floor(Math.random() * adjs.length)] +
                        "_" +
                        nouns[(int) Math.floor(Math.random() * nouns.length)]
        );
    }

    private String getRandomColor() {
        Random r = new Random();
        StringBuffer sb = new StringBuffer("#");
        while(sb.length() < 7){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, 7);
    }

    public void displayMessageFromServer(Message message){
        if (message.getText().length() > 0) {
            final Message mes = new Message(message.getText(), message.getData(), message.isBelongsToCurrentUser(), message.isMedia());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    messageAdapter.add(mes);
                    messagesView.setSelection(messagesView.getCount() - 1);
                }
            });
        }
    }

    public void sendMessage(View view) {
        String message = editText.getText().toString();
        Log.i("message ", message);
        if (message.length() > 0) {
            MemberData dt = new MemberData("", "#aaffff");
            final Message mes = new Message(message, dt, true, false);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    messageAdapter.add(mes);
                    messagesView.setSelection(messagesView.getCount() - 1);
                    JSONObject objcsss = new JSONObject();
                    try {
                        objcsss.put("conversation_id", id);
                        objcsss.put("text_message", message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i("Socket json message", objcsss.toString());
                    socket.emit("message", objcsss);
                }
            });
            editText.getText().clear();
        }
    }

    @Override
    public void onOpen(Room room) {

        Log.i("onOpen", "Scaledrone connection open ext");
    }

    @Override
    public void onOpenFailure(Room room, Exception ex) {
        Log.i("onOpenFailure ext", ex.toString());
    }

    public void onMessage(Room room, final JsonNode json, final Member member) {
        Log.i("onMessage ext", member.toString());
            // final ObjectMapper mapper = new ObjectMapper();
            //   final MemberData data = mapper.treeToValue(member.getClientData(), MemberData.class);
             boolean belongsToCurrentUser = false;
           // if(member.getId() != null)
             //  belongsToCurrentUser = member.getId().equals(scaledrone.getClientID());
            MemberData dt = new MemberData("voila", "#aaffff");

            final Message message = new Message(json.toString(), dt, true, false);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    messageAdapter.add(message);
                    messagesView.setSelection(messagesView.getCount() - 1);
                }
            });
    }

    protected static class MemberData {
        private String name;
        private String color;

        public MemberData(String name, String color) {
            this.name = name;
            this.color = color;
        }

        // Add an empty constructor so we can later parse JSON into MemberData using Jackson
        public MemberData() {
        }

        public String getName() {
            return name;
        }

        public String getColor() {
            return color;
        }

        @Override
        public String toString() {
            return "MemberData{" +
                    "name='" + name + '\'' +
                    ", color='" + color + '\'' +
                    '}';
        }
    }

    public void setNames(List<String> namesList){

        this.namesList = namesList;
        String tmp = "";

        for (String s : namesList)
        {
            tmp += s + " ";
        }
        namesText.setText(tmp);
    }

    public void setConvName(String convName){
        convNameText.setText(convName);
    }

    @Override
    public void openChatAddActivity() {
        //Intent intent = ChatAddActivity.newIntent(ChatActivity.this);
        //startActivity(intent);
        spinnerDialog.showSpinerDialog();
    }

    @Override
    public void openChatCallActivity() {
        if (this.namesList.size() > 2){
            Toast.makeText(this, "Appel de groupe impossible ", Toast.LENGTH_SHORT).show();
            return;
        }

        close();
        Log.i("open call endd ", String.valueOf(idToCall));
        Intent intent = CallActivity.newIntent(ChatActivity.this);
        intent.putExtra("id", idToCall);
        intent.putExtra("conv_id", id);
        startActivity(intent);
    }

    @Override
    public void addToUserList(String user) {
        items.add(user);
    }

    @Override
    public void serUserId(int id) {
        userId = id;
    }

    @Override
    public void toastSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openMainActivity() {
        close();
        Intent intent = HomeActivity.newIntent(ChatActivity.this);
        startActivity(intent);
    }

    @Override
    public void addUserToMap(String name, int id) {
        userMap.put(id, name);
    }

    @Override
    public void addMediaToView(int id,ChatActivity.MemberData dt, boolean user){
        final Message mes = new Message("", dt, user, true);
        mes.setIsFromServer(true);
        mes.setIdServer(id);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageAdapter.add(mes);
                messagesView.setSelection(messagesView.getCount() - 1);
            }
        });
    }

    @Override
    public void addIdToCall(int id) {
        idToCall = id;
    }

}
