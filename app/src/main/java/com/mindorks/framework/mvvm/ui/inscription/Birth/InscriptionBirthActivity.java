package com.mindorks.framework.mvvm.ui.inscription.Birth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.databinding.ActivityInscriptionbirthBinding;
import com.mindorks.framework.mvvm.ui.base.BaseActivity;
import com.mindorks.framework.mvvm.ui.inscription.Email.InscriptionEmailActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.inject.Inject;

public class InscriptionBirthActivity extends BaseActivity<ActivityInscriptionbirthBinding, InscriptionBirthViewModel> implements InscriptionBirthNavigator {

    @Inject
    InscriptionBirthViewModel _inscriptionVM;
    private ActivityInscriptionbirthBinding _mActivityInscriptionBinding;
    String _fname;
    String _lname;

    public String testtest;

    public static Intent newIntent(Context context) {
        return new Intent(context, InscriptionBirthActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_inscriptionbirth;
    }

    @Override
    public InscriptionBirthViewModel getViewModel() {
        return _inscriptionVM;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _mActivityInscriptionBinding = getViewDataBinding();
        _inscriptionVM.setNavigator(this);
        _fname= getIntent().getStringExtra("fname");
        _lname= getIntent().getStringExtra("lname");

    }

    private void checkDate(){
        DatePicker dp =  _mActivityInscriptionBinding.datePicker1;
        Calendar calendar = new GregorianCalendar(dp.getYear(),
                dp.getMonth(),
                dp.getDayOfMonth());
        java.text.DateFormat formatter = java.text.DateFormat.getDateInstance(
                java.text.DateFormat.LONG); // one of SHORT, MEDIUM, LONG, FULL, or DEFAULT
        formatter.setTimeZone(calendar.getTimeZone());
        String formatted = formatter.format(calendar.getTime());
        Intent intent = InscriptionEmailActivity.newIntent(InscriptionBirthActivity.this);
        intent.putExtra("fname", _fname);
        intent.putExtra( "lname", _lname);
        intent.putExtra( "date", formatted.toString());
        startActivity(intent);
        finish();
    }

    @Override
    public void next() {
        checkDate();
    }
}