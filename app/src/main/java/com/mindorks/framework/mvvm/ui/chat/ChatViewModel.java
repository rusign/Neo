package com.mindorks.framework.mvvm.ui.chat;

import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.data.model.api.ApiContent;
import com.mindorks.framework.mvvm.data.model.api.CirclesRequest;
import com.mindorks.framework.mvvm.data.model.api.ConversationRequest;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;

public class  ChatViewModel extends BaseViewModel<ChatNavigator> {

    public final ObservableField<String> names;

    String _color[]  = {"#3498db", "#1abc9c", "#2ecc71", "#e67e22", "#c0392b", "#f39c12", "#2c3e50", "#2c2c54"};

    public ChatViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        names = new ObservableField<>("lalalalala");
    }

    public String GetToken(){
        return getDataManager().getAccessToken();
    }

    private int getMyId(List<ApiContent.ConversationLinks> links){
        Log.i("getMyId amil ",getDataManager().getCurrentUserEmail());
        for (ApiContent.ConversationLinks item : links){
            if (!item.user_id.email.equals(getDataManager().getCurrentUserEmail())) {
               getNavigator().addIdToCall(item.user_id.id);
            }
        }
        for (ApiContent.ConversationLinks item : links){
            if (item.user_id.email.equals(getDataManager().getCurrentUserEmail())) {
                return item.id;
            }
        }
        Log.i("getMyId amil ","error retun 1");

        return 1;
    }

    private void getNames(List<ApiContent.ConversationLinks> links){
        List<String> names = new ArrayList<String>();

        for (ApiContent.ConversationLinks item : links){
                names.add(item.user_id.first_name);
        }
        getNavigator().setNames(names);
    }

    private String getNameByLinkID(int id, List<ApiContent.ConversationLinks> links){
        for (ApiContent.ConversationLinks val : links)
        {
            getNavigator().addUserToMap(val.user_id.first_name, val.id);
        }
        for (ApiContent.ConversationLinks val : links)
        {
            if (val.id == id)
                return val.user_id.first_name + " - ";
        }
        return "";
    }

    public void getMessageFromServer(int id) {
        setIsLoading(true);

        getCompositeDisposable().add(getDataManager()
                .doConversationInfoApiCall(new ConversationRequest.ConversationInfo(getDataManager().getAccessToken(), id))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    int myId = getMyId(response.getContent().links);
                    getNavigator().serUserId(myId);
                    getNavigator().setConvName(response.getContent().name);
                    getNames(response.getContent().links);
                    getContacts(response.getContent().circle.id,response.getContent().links);
                    for (ApiContent.ConversationMessage item : response.getContent().messages){
                        Date date = new Date(item.sent);

                        ChatActivity.MemberData dt = new ChatActivity.MemberData(getNameByLinkID(item.link_id ,response.getContent().links)  + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(date).toString(), _color[item.link_id % (_color.length - 1)]);
                        if (item.link_id == myId)
                        {
                            if (item.medias != 0){
                                getNavigator().addMediaToView(item.id, dt, true);
                            }
                            else{
                                Message message = new Message(item.content, dt, true, false);
                                getNavigator().displayMessageFromServer(message);
                            }

                        }
                        else
                        {
                            if (item.medias != 0){
                                getNavigator().addMediaToView(item.id, dt, false);
                            }
                            else{
                                Message message = new Message(item.content, dt, false, false);
                                getNavigator().displayMessageFromServer(message);
                            }
                        }
                    }
                   // getNavigator().displayMessageFromServer(message);
                }, throwable -> {
                    setIsLoading(false);
                 //   getNavigator().handleError(throwable);
                   // getNavigator().toastServerError();
                }));
    }



    public void getPicFromServer(int id, ChatActivity.MemberData dt, boolean user) {
        setIsLoading(true);

        getCompositeDisposable().add(getDataManager()
                .doConversationPicApiCall(new ConversationRequest.ConversationPic(getDataManager().getAccessToken(), id))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    Message message = new Message(response.getData(), dt, user, true);
                    getNavigator().displayMessageFromServer(message);
                    // getNavigator().displayMessageFromServer(message);
                }, throwable -> {
                    setIsLoading(false);
                    //   getNavigator().handleError(throwable);
                    // getNavigator().toastServerError();
                }));
    }

    private OkHttpClient getConfigOkHttpClient() {
     //   HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
    }

    //essayer post
    public void UploadMedia(int id, String data, String imagePath) {


        MyTaskParams params = new MyTaskParams(id, getDataManager().getAccessToken(), imagePath);
        UploadFileAsync myTask = new UploadFileAsync();
        myTask.execute(params);
    }

    public void UploadMedia2(int id, String data, String filePath) {
        setIsLoading(true);
        String formatData = "data:image/jpeg;base64,"+data;
        Charset UTF_8 = Charset.forName("UTF-8");

        byte[] bytes = formatData.getBytes(UTF_8);
        Bitmap image;
        AndroidNetworking.upload("https://api.neo.ovh/media/upload/" + id)
                .addHeaders("Authorization", getDataManager().getAccessToken())
                .setContentType("multipart/form-data")
                .addMultipartFile("file", new File(filePath))

                .setPriority(Priority.HIGH)

                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d("onReceived", " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d("onReceived", " bytesSent : " + bytesSent);
                        Log.d("onReceived", " bytesReceived : " + bytesReceived);
                        Log.d("onReceived", " isFromCache : " + isFromCache);
                    }
                })
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
                        Log.i("onProgress", "bytesUploaded : " + bytesUploaded + " totalBytes : " + totalBytes);

                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                    //    Log.i("UploadMedia onResponse", response.body.toString());
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.i("UploadMedia echrc", error.toString());
                    }
                });


        // getCompositeDisposable().add(getDataManager()
//                .doConversationUploadMediaApiCall(new ConversationRequest.ConversationUploadMedia(formatData), id, getDataManager().getAccessToken())
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(response -> {
//                    setIsLoading(false);
//                    Log.i("UploadMedia", "success");
//                    // getNavigator().displayMessageFromServer(message);
//                }, throwable -> {
//                    setIsLoading(false);
//                    Log.i("UploadMedia echrc", throwable.toString());
//
//                    //   getNavigator().handleError(throwable);
//                    // getNavigator().toastServerError();
//                }));
    }

    public void uploadMediaInfo(int id, String data) {
        setIsLoading(true);

        getCompositeDisposable().add(getDataManager()
                .doConversationMessageInfoApiCall(new ConversationRequest.ConversationMessageInfo(getDataManager().getAccessToken(), id))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    for (ApiContent.MessageMedias item : response.getContent().medias) {
                        UploadMedia(item.media.id, data, null);
                    }
                    // getNavigator().displayMessageFromServer(message);
                }, throwable -> {
                    setIsLoading(false);
                    //   getNavigator().handleError(throwable);
                    // getNavigator().toastServerError();
                }));
    }

    public void sendMedia(int id, String data, List<String> files, String filePath) {
        setIsLoading(true);

        getCompositeDisposable().add(getDataManager()
                .doConversationMessageSendApiCall(new ConversationRequest.ConversationMessageSend(getDataManager().getAccessToken(), files, id))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    for (ApiContent.MessageMedia item : response.getMedia_list()) {
                        UploadMedia(item.id, data, filePath);
                    }
                    // getNavigator().displayMessageFromServer(message);
                }, throwable -> {
                    setIsLoading(false);
                    //   getNavigator().handleError(throwable);
                    // getNavigator().toastServerError();
                }));
    }

    public void getMediaFromServer(int id, ChatActivity.MemberData dt, boolean user) {
        setIsLoading(true);

        getCompositeDisposable().add(getDataManager()
                .doConversationMessageInfoApiCall(new ConversationRequest.ConversationMessageInfo(getDataManager().getAccessToken(), id))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    Log.i("getMediaFromServer", "sucess");
                    for (ApiContent.MessageMedias item : response.getContent().medias) {
                        getPicFromServer(item.media.id, dt, user);
                    }
                    // getNavigator().displayMessageFromServer(message);
                }, throwable -> {
                    Log.i("getMediaFromServer", "echec");

                    setIsLoading(false);
                    //   getNavigator().handleError(throwable);
                    // getNavigator().toastServerError();
                }));
    }

    public void getMediaFromServerImg(int id, ImageView img) {
        setIsLoading(true);

        getCompositeDisposable().add(getDataManager()
                .doConversationMessageInfoApiCall(new ConversationRequest.ConversationMessageInfo(getDataManager().getAccessToken(), id))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    Log.i("getMediaFromServer", "sucess");
                    for (ApiContent.MessageMedias item : response.getContent().medias) {
                        getPicFromServerImg(item.media.id, img);
                    }
                    // getNavigator().displayMessageFromServer(message);
                }, throwable -> {
                    Log.i("getMediaFromServer", "echec");

                    setIsLoading(false);
                    //   getNavigator().handleError(throwable);
                    // getNavigator().toastServerError();
                }));
    }

    public void getPicFromServerImg(int id, ImageView img) {
        setIsLoading(true);

        getCompositeDisposable().add(getDataManager()
                .doConversationPicApiCall(new ConversationRequest.ConversationPic(getDataManager().getAccessToken(), id))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);

                    String imageDataBytes = response.getData().substring(response.getData().indexOf(",")+1);
                    InputStream stream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT));
                    Bitmap image = BitmapFactory.decodeStream(stream);

                    img.setImageBitmap(image);
                    // getNavigator().displayMessageFromServer(message);
                }, throwable -> {
                    setIsLoading(false);
                    //   getNavigator().handleError(throwable);
                    // getNavigator().toastServerError();
                }));
    }

    private void getContacts(int id, List<ApiContent.ConversationLinks> links) {
        setIsLoading(true);
        // getNavigator().setCircles();
        String token = getDataManager().getAccessToken();
        List<String> userList = new ArrayList<String>();
        for (ApiContent.ConversationLinks val: links){
            userList.add(val.user_id.email);
        }
        getCompositeDisposable().add(getDataManager()
                .doCirclesContactsApiCall(new CirclesRequest.CircleContacts(token, id))
                .doOnSuccess(response -> Log.i("getCircles succes", "success"))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    Log.i("getCircles suscribe", "suscribe");
                    for (ApiContent.CircleUser item : response.getContent().users)
                    {
                        if (!userList.contains(item.user.email))
                            getNavigator().addToUserList(item.user.email);
                    }
                }, throwable -> {
                    setIsLoading(false);
                    Log.i("getCircles error", throwable.toString());

                    //getNavigator().handleError(throwable);
                    //getNavigator().toastServerError();
                }));
    }

    public void addUserToConv(String email, int conv_id){
        String token = getDataManager().getAccessToken();
        getCompositeDisposable().add(getDataManager()
                .doConversationInviteApiCall(new ConversationRequest.ConversationInvite(token, email, conv_id))
                .doOnSuccess(response -> Log.i("getCircles succes", "success"))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    Log.i("addUserToConv", "success");
                    getNavigator().openMainActivity();
                    getNavigator().toastSuccess(email + " ajouté");
                }, throwable -> {
                    setIsLoading(false);
                    Log.i("addUserToConv error", throwable.toString());
                    getNavigator().toastSuccess(email + " déjà dans la conversation");
                }));
    }

    public void onNavAddClick(){
        getNavigator().openChatAddActivity();
    }

   public void  onCallClick(){
       getNavigator().openChatCallActivity();

   }
}


 class UploadFileAsync extends AsyncTask<MyTaskParams, String, Void> {
    @Override
    protected Void doInBackground(MyTaskParams... params) {

        try {
            int id = params[0].id;
            String imagePath = params[0].imagePath;
            String token = params[0].token;
            String fileName = params[0].imagePath;
            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(imagePath);

            if (!sourceFile.isFile()) {
                Log.e("uploadFile", "Source File not exist :" + imagePath);

            } else {
                try {

                    // open a URL connection to the Servlet
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    URL url = new URL("https://api.neo.ovh/media/upload/" + id);

                    // Open a HTTP  connection to  the URL
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("file", fileName);
                    conn.setRequestProperty("Authorization", token);

                    dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\""
                            + fileName + "\"" + lineEnd);

                    dos.writeBytes(lineEnd);

                    // create a buffer of  maximum size
                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {

                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    }

                    // send multipart form data necesssary after file data...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    // Responses from the server (code and message)
                    int serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn.getResponseMessage();

                    Log.i("uploadFile", "HTTP Response is : "
                            + serverResponseMessage + ": " + serverResponseCode);

                    //close the streams //
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                    Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
                } catch (Exception e) {

                    e.printStackTrace();

                }
            } // End else block

        } catch (Exception e) {

            // dialog.dismiss();
            e.printStackTrace();

        }
        // dialog.dismiss();
        return null;
    } // End else block
}

 class MyTaskParams {
    int id;
    String token;
    String imagePath;

    MyTaskParams(int id, String token, String imagePath) {
        this.id = id;
        this.token = token;
        this.imagePath = imagePath;
    }
}