package com.mindorks.framework.mvvm.ui.firstPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.databinding.ActivityFirstpageBinding;
import com.mindorks.framework.mvvm.ui.base.BaseActivity;
import com.mindorks.framework.mvvm.ui.inscription.InscriptionActivity;
import com.mindorks.framework.mvvm.ui.inscription.Name.InscriptionNameActivity;
import com.mindorks.framework.mvvm.ui.login.LoginActivity;
import com.mindorks.framework.mvvm.ui.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.inject.Inject;


public class FirstPageActivity extends BaseActivity<ActivityFirstpageBinding, FirstPageViewModel> implements FirstPageNavigator {

    @Inject
    FirstPageViewModel _firstPageVM;
    private  ActivityFirstpageBinding mActivityFirstPageBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, com.mindorks.framework.mvvm.ui.firstPage.FirstPageActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_firstpage;
    }

    @Override
    public FirstPageViewModel getViewModel() {
        return _firstPageVM;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.newIntent(FirstPageActivity.this);
        startActivity(intent);
        finish();
    }
    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.newIntent(FirstPageActivity.this);
        startActivity(intent);
    }
    @Override
    public void openInscriptionActivity() {
        Intent intent = InscriptionNameActivity.newIntent(FirstPageActivity.this);
        startActivity(intent);
    }
    public class a{
        public String name;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityFirstPageBinding = getViewDataBinding();
        _firstPageVM.setNavigator(this);


        Log.i("parh",getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath());
//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( FirstPageActivity.this,  new OnSuccessListener<InstanceIdResult>() {
//            @Override
//            public void onSuccess(InstanceIdResult instanceIdResult) {
//                String newToken = instanceIdResult.getToken();
//                Log.e("newToken onSuccess",newToken);
//
//            }
//        });
        Log.i("token firebase ",FirebaseInstanceId.getInstance().getInstanceId().toString());

    }


}
