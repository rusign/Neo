package com.mindorks.framework.mvvm.ui.inscription.Mdp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.databinding.ActivityInscriptionmdpBinding;
import com.mindorks.framework.mvvm.ui.base.BaseActivity;
import com.mindorks.framework.mvvm.ui.firstPage.FirstPageActivity;
import com.mindorks.framework.mvvm.ui.home.HomeActivity;
import com.mindorks.framework.mvvm.ui.inscription.Birth.InscriptionBirthActivity;
import com.mindorks.framework.mvvm.ui.login.LoginActivity;

import javax.inject.Inject;

public class InscriptionMdpActivity extends BaseActivity<ActivityInscriptionmdpBinding, InscriptionMdpViewModel> implements InscriptionMdpNavigator {

    @Inject
    InscriptionMdpViewModel _inscriptionVM;
    private ActivityInscriptionmdpBinding _mActivityInscriptionBinding;

    String _fname;
    String _lname;
    String _date;
    String _email;

    public static Intent newIntent(Context context) {
        return new Intent(context, InscriptionMdpActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_inscriptionmdp;
    }

    @Override
    public InscriptionMdpViewModel getViewModel() {
        return _inscriptionVM;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("aaazaza ", "azazazazazz---------------------------------");

        _mActivityInscriptionBinding = getViewDataBinding();
        _fname = getIntent().getStringExtra("fname");
        _lname = getIntent().getStringExtra("lname");
        _date = getIntent().getStringExtra("date");
        _email = getIntent().getStringExtra("email");
        _inscriptionVM.setNavigator(this);
        Log.i("fname ", _fname);
        Log.i("lname ", _lname);
        Log.i("date ", _date);
    }

    public final boolean isValidEmail(String target) {
        return !target.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void checkEmail() {
        String mdp = _mActivityInscriptionBinding.IDmdp.getText().toString();
        String mdpconf = _mActivityInscriptionBinding.IDmdpConf.getText().toString();

        if ((!mdp.isEmpty() && !mdpconf.isEmpty()) && mdp.equals(mdpconf)) {
            hideKeyboard();
            _inscriptionVM.createAccount(_email, mdp, _fname, _lname, _date);
            //Intent intent = InscriptionBirthActivity.newIntent(InscriptionMdpActivity.this);
            //intent.putExtra("email", _email);
//            intent.putExtra( "lname", _lname);
//            intent.putExtra( "fname", _fname);
//            intent.putExtra( "date", _date);
            // startActivity(intent);
            //  finish();
        } else {
            Toast.makeText(this, getString(R.string.invalid_mdp), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void next() {
        checkEmail();
    }

    @Override
    public void toastServerError() {
        Toast.makeText(this, getString(R.string.invalid_create), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openMainActivity() {
        Toast.makeText(this, getString(R.string.valid_create), Toast.LENGTH_SHORT).show();
        Intent intent = FirstPageActivity.newIntent(InscriptionMdpActivity.this);
        startActivity(intent);
        finish();
    }
}