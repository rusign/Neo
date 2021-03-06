package com.mindorks.framework.mvvm.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.data.local.prefs.AppPreferencesHelper;

public class SettingsPrefActivity extends AppCompatPreferenceActivity {
    private static final String TAG = SettingsPrefActivity.class.getSimpleName();
    private SettingsViewModel _vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
    //    Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.settings_toolbar, root, false);
      //  root.addView(bar, 0); // insert at top
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // load settings fragment
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        Log.i("mail settings" , preferences.getString ("PREF_KEY_CURRENT_USER_EMAIL", "NA" )); //This is just an example, you could also put boolean, long, int or floats
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        Log.i("postceate", "postcreate call");
        super.onPostCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    public static class MainPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_main);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("onOptionsItemSelected", item.toString());
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private static void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();
            Log.i("preferenc change pref ", preference.getKey());
            Log.i("preferenc change ", stringValue);

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(R.string.pref_ringtone_silent);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(R.string.summary_choose_ringtone);
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            } else if (preference instanceof EditTextPreference) {
                if (preference.getKey().equals("key_gallery_name")) {
                    // update the changed gallery name to summary filed
                    preference.setSummary(stringValue);
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

}