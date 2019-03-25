package com.mindorks.framework.mvvm.ui.settings;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;

import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.databinding.ActivitySettingsBinding;
import com.mindorks.framework.mvvm.ui.base.BaseActivity;

import javax.inject.Inject;



public class SettingsActivity extends BaseActivity<ActivitySettingsBinding, SettingsViewModel> implements SettingsNavigator {

    public static class SettingsScreen extends PreferenceFragment{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
          //  getLayoutInflater().inflate(R.layout.toolbar, (ViewGroup)findViewById(android.R.id.appbar_settings_id));

            addPreferencesFromResource(R.xml.pref_main);
            Log.i("onCreate SettingsScreen", "SettingsScreen" );
            SwitchPreference switchPreference =(SwitchPreference) findPreference("");
            Preference decoB = (Preference)findPreference("decoButton");
            decoB.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Log.i("deco", "deco" );
                    return true;
                }
            });

        }
    }
    @Inject
    SettingsViewModel _settingsVM;
    private ActivitySettingsBinding _mActivitySettingsBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, com.mindorks.framework.mvvm.ui.settings.SettingsActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    public SettingsViewModel getViewModel() {
        return _settingsVM;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        _mActivitySettingsBinding = getViewDataBinding();
        _settingsVM.setNavigator(this);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString ("key_mail", _settingsVM.getDataManager().getCurrentUserEmail());
        editor.putString ("key_lname", _settingsVM.getDataManager().getCurrentUserLName());
        editor.putString ("key_fname", _settingsVM.getDataManager().getCurrentUserFName());

        editor.commit();

    }


}
