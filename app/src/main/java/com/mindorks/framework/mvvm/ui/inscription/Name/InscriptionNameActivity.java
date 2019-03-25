package com.mindorks.framework.mvvm.ui.inscription.Name;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.databinding.ActivityInscriptionnameBinding;
import com.mindorks.framework.mvvm.ui.base.BaseActivity;
import com.mindorks.framework.mvvm.ui.home.HomeActivity;
import com.mindorks.framework.mvvm.ui.inscription.Birth.InscriptionBirthActivity;
import com.mindorks.framework.mvvm.ui.inscription.InscriptionViewModel;
import com.mindorks.framework.mvvm.ui.login.LoginActivity;

import javax.inject.Inject;


public class InscriptionNameActivity extends BaseActivity<ActivityInscriptionnameBinding, InscriptionNameViewModel> implements InscriptionNameNavigator {

    @Inject
    InscriptionNameViewModel _inscriptionVM;
    private ActivityInscriptionnameBinding _mActivityInscriptionBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, InscriptionNameActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_inscriptionname;
    }

    @Override
    public InscriptionNameViewModel getViewModel() {
        return _inscriptionVM;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _mActivityInscriptionBinding = getViewDataBinding();
        _inscriptionVM.setNavigator(this);
    }

    private void checkName(){
        String fName = _mActivityInscriptionBinding.IDFname.getText().toString();
        String lName = _mActivityInscriptionBinding.IDLname.getText().toString();

        if (!fName.isEmpty() && !lName.isEmpty()) {
            hideKeyboard();
            Intent intent = InscriptionBirthActivity.newIntent(InscriptionNameActivity.this);
            intent.putExtra("fname", fName);
            intent.putExtra( "lname", lName);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, getString(R.string.invalid_name), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void next() {
        checkName();
    }
}