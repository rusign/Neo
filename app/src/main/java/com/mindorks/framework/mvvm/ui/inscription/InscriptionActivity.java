package com.mindorks.framework.mvvm.ui.inscription;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.databinding.ActivityInscriptionBinding;
import com.mindorks.framework.mvvm.ui.base.BaseActivity;

import javax.inject.Inject;


public class InscriptionActivity extends BaseActivity<ActivityInscriptionBinding, InscriptionViewModel> implements InscriptionNavigator {

    @Inject
    InscriptionViewModel _inscriptionVM;
    private ActivityInscriptionBinding _mActivityInscriptionBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, com.mindorks.framework.mvvm.ui.inscription.InscriptionActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_inscription;
    }

    @Override
    public InscriptionViewModel getViewModel() {
        return _inscriptionVM;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _mActivityInscriptionBinding = getViewDataBinding();
        _inscriptionVM.setNavigator(this);
    }

}
