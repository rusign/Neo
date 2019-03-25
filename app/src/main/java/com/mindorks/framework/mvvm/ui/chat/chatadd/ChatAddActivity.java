package com.mindorks.framework.mvvm.ui.chat.chatadd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.databinding.ActivityChataddBinding;
import com.mindorks.framework.mvvm.ui.base.BaseActivity;
import com.mindorks.framework.mvvm.ui.home.HomeActivity;
import com.mindorks.framework.mvvm.ui.main.MainActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ChatAddActivity extends BaseActivity<ActivityChataddBinding, ChatAddViewModel> implements ChatAddNavigator {

    @Inject
    ChatAddViewModel mChatAddViewModel;
    private ActivityChataddBinding mActivityChatAddBinding;

    private AppCompatSpinner _usersSpinner;
    List<CompanyModel> companies_list;
    public static Intent newIntent(Context context) {
        return new Intent(context, ChatAddActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_chatadd;
    }

    @Override
    public ChatAddViewModel getViewModel() {
        return mChatAddViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void login() {
    }

    @Override
    public void toastServerError()
    {
        Toast.makeText(this, getString(R.string.invalid_email_password), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openMainActivity() {
        Intent intent = HomeActivity.newIntent(ChatAddActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityChatAddBinding = getViewDataBinding();
        mChatAddViewModel.setNavigator(this);

    }

    public class CompanyModel implements Serializable {
        private String CompanyId;
        private String CompanyName;
        private String Location;

        public CompanyModel(String companyId, String companyName, String location) {
            CompanyId = companyId;
            CompanyName = companyName;
            Location = location;
        }

        public String getCompanyId() {
            return CompanyId;
        }

        public void setCompanyId(String companyId) {
            CompanyId = companyId;
        }

        public String getCompanyName() {
            return CompanyName;
        }

        public void setCompanyName(String companyName) {
            CompanyName = companyName;
        }

        public String getLocation() {
            return Location;
        }

        public void setLocation(String location) {
            Location = location;
        }

        @Override
        public String toString() {

            return CompanyName;
        }
    }

}
