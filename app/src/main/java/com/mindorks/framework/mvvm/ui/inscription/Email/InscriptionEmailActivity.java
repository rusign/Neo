package com.mindorks.framework.mvvm.ui.inscription.Email;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.databinding.ActivityInscriptionemailBinding;
import com.mindorks.framework.mvvm.ui.base.BaseActivity;
import com.mindorks.framework.mvvm.ui.inscription.Birth.InscriptionBirthActivity;
import com.mindorks.framework.mvvm.ui.inscription.Mdp.InscriptionMdpActivity;
import com.mindorks.framework.mvvm.ui.inscription.Name.InscriptionNameActivity;

import javax.inject.Inject;

public class InscriptionEmailActivity extends BaseActivity<ActivityInscriptionemailBinding, InscriptionEmailViewModel> implements InscriptionEmailNavigator {

    @Inject
    InscriptionEmailViewModel _inscriptionVM;
    private ActivityInscriptionemailBinding _mActivityInscriptionBinding;

    String _fname;
    String _lname;
    String _date;
    public static Intent newIntent(Context context) {
        return new Intent(context, InscriptionEmailActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_inscriptionemail;
    }

    @Override
    public InscriptionEmailViewModel getViewModel() {
        return _inscriptionVM;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("aaazaza ", "azazazazazz---------------------------------");

        _mActivityInscriptionBinding = getViewDataBinding();
        _fname= getIntent().getStringExtra("fname");
        _lname= getIntent().getStringExtra("lname");
        _date= getIntent().getStringExtra("date");
        _inscriptionVM.setNavigator(this);
        Log.i("fname ", _fname);
        Log.i("lname ", _lname);
        Log.i("date ", _date);
    }

    public final  boolean isValidEmail(String target) {
        return !target.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void checkEmail(){
        String email = _mActivityInscriptionBinding.inputEmail.getText().toString();

        if (isValidEmail(email)) {
            hideKeyboard();
            Intent intent = InscriptionMdpActivity.newIntent(InscriptionEmailActivity.this);
            intent.putExtra("email", email);
            intent.putExtra( "lname", _lname);
            intent.putExtra( "fname", _fname);
            intent.putExtra( "date", _date);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, getString(R.string.invalid_email), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void next() {
        checkEmail();
    }
}
