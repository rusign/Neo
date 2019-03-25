package com.mindorks.framework.mvvm.ui.settings;


import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.ui.firstPage.FirstPageActivity;
import com.mindorks.framework.mvvm.ui.home.HomeActivity;
import com.mindorks.framework.mvvm.ui.login.LoginActivity;

public  class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.pref_main);
        Log.i("onCr ettingsFragment", "SettingsFragment" );
        Preference myPref = findPreference(getString(R.string.key_send_feedback));

        Preference button = (Preference)getPreferenceManager().findPreference("decoButton");
        if (button != null) {
            button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference arg0) {
                    Intent intent = FirstPageActivity.newIntent(getActivity());
                    startActivity(intent);
                    return true;
                }
            });
        }

        Preference lnam = (Preference)getPreferenceManager().findPreference("key_lname");
        if (lnam != null) {
            lnam.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    ((SettingsActivity)getActivity())._settingsVM.ModifLname(newValue.toString());

                    return true;
                }
            });
        }

        Preference fname = (Preference)getPreferenceManager().findPreference("key_fname");
        if (fname != null) {
            fname.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    ((SettingsActivity)getActivity())._settingsVM.ModifFname(newValue.toString());
                    return true;
                }
            });
        }

        Preference email = (Preference)getPreferenceManager().findPreference("key_mail");
        if (email != null) {
            email.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    ((SettingsActivity)getActivity())._settingsVM.ModifyEmail(newValue.toString());
                    return true;
                }
            });
        }


        Preference pass = (Preference)getPreferenceManager().findPreference("password_key");
        if (pass != null) {
            pass.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    ((SettingsActivity)getActivity())._settingsVM.ModifyPass(newValue.toString());
                    return true;
                }
            });
        }


    }



}