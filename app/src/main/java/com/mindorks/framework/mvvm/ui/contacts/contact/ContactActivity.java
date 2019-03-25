package com.mindorks.framework.mvvm.ui.contacts.contact;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.databinding.ActivityContactBinding;
import com.mindorks.framework.mvvm.ui.base.BaseActivity;
import com.mindorks.framework.mvvm.ui.home.HomeActivity;

import javax.inject.Inject;

public class ContactActivity extends BaseActivity<ActivityContactBinding, ContactViewModel> implements ContactNavigator {

    @Inject
    ContactViewModel mContactViewModel;
    private ActivityContactBinding mActivityContactBinding;

    private String _name;
    private String _email;
    private int _user_id;

    public static Intent newIntent(Context context) {
        return new Intent(context, ContactActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_contact;
    }

    @Override
    public ContactViewModel getViewModel() {
        return mContactViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    private void Contact() {
//            Toast.makeText(this, getString(R.string.invalid_email_password), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toastServerError() {
        Toast.makeText(this, getString(R.string.invalid_email_password), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openMainActivity() {
        Intent intent = HomeActivity.newIntent(ContactActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityContactBinding = getViewDataBinding();
        mContactViewModel.setNavigator(this);
        _name = getIntent().getStringExtra("name");
        _email = getIntent().getStringExtra("email");
        _user_id = getIntent().getIntExtra("id", 0);
        TextView nameText = (TextView)findViewById(R.id.name_contact_id);
        TextView emailText = (TextView)findViewById(R.id.email_contact_id);
        nameText.setText(_name);
        emailText.setText(_email);
    }
}
